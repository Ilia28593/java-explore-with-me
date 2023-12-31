package ru.practicum.main.event.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.State;
import ru.practicum.main.event.model.Status;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.*;
import ru.practicum.main.location.model.Location;
import ru.practicum.main.location.repository.LocationRepository;
import ru.practicum.main.participation.dto.ParticipationRequestDto;
import ru.practicum.main.participation.mapper.ParticipationMapper;
import ru.practicum.main.participation.model.ParticipationRequest;
import ru.practicum.main.participation.repository.ParticipationRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.service.UserServiceImpl;
import ru.practicum.stats.StatsClient;
import ru.practicum.statsDto.EndpointHitDto;
import ru.practicum.statsDto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main.constant.Constants.DATE_FORMAT;

@Slf4j
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserServiceImpl userService;
    private final ParticipationRepository participationRepository;
    private final LocationRepository locationRepository;
    private final StatsClient statsClient;

    @Transactional
    @Override
    public List<EventShortDto> getEventsPrivate(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return eventRepository.getEventsByInitiatorId(userId, pageable)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto addEventPrivate(Long userId, NewEventDto newEventDto) {
        LocalDateTime start = LocalDateTime.parse(newEventDto.getEventDate(),
                DateTimeFormatter.ofPattern(DATE_FORMAT));

        if (start.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalArgumentException("The start time is specified incorrectly");
        }
        Location location = locationRepository.save(newEventDto.getLocation());
        newEventDto.setLocation(location);
        Category category = categoryRepository.getById(newEventDto.getCategory());
        User user = userService.getUserById(userId);

        Event event = EventMapper.toEvent(newEventDto, user, category);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventFullDto getEventPrivate(Long userId, Long eventId) {
        return EventMapper.toEventFullDto(getEventById(userId, eventId));
    }

    public Event getEventById(Long userId, Long eventId) {
        return eventRepository.getEventsByIdAndInitiatorId(eventId, userId).orElseThrow(() -> {
            throw new NotFoundException("Event not found.");
        });
    }


    @Transactional
    @Override
    public EventFullDto updateEventPrivate(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event oldEvent = getEventById(userId, eventId);
        validateUpdateEventPrivate(oldEvent, updateEventUserRequest);
        Category newCategory = getCategory(updateEventUserRequest, oldEvent);
        Event upEvent = oldEvent;
        if (updateEventUserRequest.getStateAction() != null) {
            if (updateEventUserRequest.getStateAction().equals("SEND_TO_REVIEW")) {
                upEvent = EventMapper.toEvent(updateEventUserRequest, oldEvent, newCategory);
                upEvent.setState(State.PENDING);
            }
            if (updateEventUserRequest.getStateAction().equals("CANCEL_REVIEW")) {
                upEvent.setState(State.CANCELED);
            }
        }
        upEvent.setId(eventId);
        return EventMapper.toEventFullDto(eventRepository.save(upEvent));
    }

    private Category getCategory(UpdateEventUserRequest updateEventUserRequest, Event oldEvent) {
        if (updateEventUserRequest.getLocation() != null) {
            Location location = locationRepository.save(updateEventUserRequest.getLocation());
            updateEventUserRequest.setLocation(location);
        }
        return updateEventUserRequest.getCategory() == null ?
                oldEvent.getCategory() : categoryRepository.getById(updateEventUserRequest.getCategory());
    }

    private void validateUpdateEventPrivate(Event oldEvent, UpdateEventUserRequest updateEventUserRequest) {
        if (oldEvent.getState() != null && oldEvent.getState().equals(State.PUBLISHED)) {
            throw new StateArgumentException("Cannot cancel events that are not pending or not canceled");
        }
        LocalDateTime start = oldEvent.getEventDate();
        if (updateEventUserRequest.getEventDate() != null) {
            if (LocalDateTime.parse(updateEventUserRequest.getEventDate(), DateTimeFormatter.ofPattern(DATE_FORMAT))
                    .isBefore(start.plusHours(2))) {
                throw new IllegalArgumentException("The start time is before or equals eventDate");
            }
        }
    }

    @Transactional
    @Override
    public List<ParticipationRequestDto> getRequestsEventsUserPrivate(Long userId, Long eventId) {
        return participationRepository.getParticipationRequestsByEvent(eventId)
                .stream()
                .map(ParticipationMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateEventRequestStatusPrivate(Long userId,
                                                                          Long eventId,
                                                                          EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        Event event = getEventById(userId, eventId);
        if (Long.valueOf(event.getParticipantLimit()).equals(event.getConfirmedRequests())) {
            throw new OverflowLimitException("Cannot exceed the number of participants.");
        }
        Status status = Status.valueOf(eventRequestStatusUpdateRequest.getStatus());

        List<ParticipationRequest> list = participationRepository.getParticipationRequestByIdIn(eventRequestStatusUpdateRequest.getRequestIds());
        List<ParticipationRequest> listPending = new ArrayList<>();
        List<ParticipationRequest> listRejected = new ArrayList<>();
        List<ParticipationRequest> listOld = new ArrayList<>();
        List<ParticipationRequestDto> listDto = new ArrayList<>();
        List<ParticipationRequestDto> listDtoReject = new ArrayList<>();

        if (event.getParticipantLimit() == 0 && Boolean.TRUE.equals(!event.getRequestModeration())) {
            return new EventRequestStatusUpdateResult(listDto, listDtoReject);
        } else if (event.getParticipantLimit() > 0 && Boolean.TRUE.equals(!event.getRequestModeration())) {
            for (ParticipationRequest participationRequest : list) {
                if (!participationRequest.getStatus().equals(Status.PENDING)) {
                    throw new StatusPerticipationRequestException("Wrong status request");
                }
                EventRequestStatusUpdateResult listDto1 = getEventRequestStatusUpdateResult(status, listOld, participationRequest, listPending, event, list, listDto, listDtoReject, listRejected);
                if (listDto1 != null) return listDto1;
            }
            listDto = listPending.stream().map(ParticipationMapper::toParticipationRequestDto).collect(Collectors.toList());
            return new EventRequestStatusUpdateResult(listDto, new ArrayList<>());
        } else if (event.getParticipantLimit() > 0 && Boolean.TRUE.equals(event.getRequestModeration())) {
            for (ParticipationRequest participationRequest : list) {
                if (!participationRequest.getStatus().equals(Status.PENDING)) {
                    throw new StatusPerticipationRequestException("Wrong status request.");
                }
                EventRequestStatusUpdateResult listDto1 = getEventRequestStatusUpdateResult(status, listOld, participationRequest, listPending, event, list, listDto, listDtoReject, listRejected);
                if (listDto1 != null) return listDto1;
            }
        }
        listDto = listPending.stream().map(ParticipationMapper::toParticipationRequestDto).collect(Collectors.toList());
        return new EventRequestStatusUpdateResult(listDto, new ArrayList<>());

    }

    private EventRequestStatusUpdateResult getEventRequestStatusUpdateResult(Status status, List<ParticipationRequest> listOld,
                                                                             ParticipationRequest participationRequest,
                                                                             List<ParticipationRequest> listPending, Event event,
                                                                             List<ParticipationRequest> list,
                                                                             List<ParticipationRequestDto> listDto,
                                                                             List<ParticipationRequestDto> listDtoReject,
                                                                             List<ParticipationRequest> listRejected) {

        if (status.equals(Status.CONFIRMED)) {
            listOld.add(participationRequest);
            participationRequest.setStatus(Status.CONFIRMED);
            listPending.add(participationRequest);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            participationRepository.saveAndFlush(participationRequest);

            if (Long.valueOf(event.getParticipantLimit()).equals(event.getConfirmedRequests())) {
                list.removeAll(listOld);
                if (list.size() != 0) {
                    listDto = listPending.stream().map(ParticipationMapper::toParticipationRequestDto).collect(Collectors.toList());
                    listDtoReject = list.stream().map(ParticipationMapper::toParticipationRequestDto).collect(Collectors.toList());
                    return new EventRequestStatusUpdateResult(listDto, listDtoReject);
                } else {
                    listDto = listPending.stream().map(ParticipationMapper::toParticipationRequestDto).collect(Collectors.toList());
                    return new EventRequestStatusUpdateResult(listDto, new ArrayList<>());
                }
            }
        } else {
            participationRequest.setStatus(Status.REJECTED);
            listRejected.add(participationRequest);
            participationRepository.saveAndFlush(participationRequest);
            listDtoReject = list.stream().map(ParticipationMapper::toParticipationRequestDto).collect(Collectors.toList());
            return new EventRequestStatusUpdateResult(new ArrayList<>(), listDtoReject);
        }
        return null;
    }


    @Transactional
    @Override
    public List<EventFullDto> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories,
                                             String rangeStart, String rangeEnd, Integer from, Integer size) {

        List<EventFullDto> list;
        List<State> stateEnum = null;
        if (states != null) {
            stateEnum = states.stream().map(State::valueOf).collect(Collectors.toList());
        }
        Pageable pageable = PageRequest.of(from / size, size);
        if (rangeStart == null && rangeEnd == null) {
            if (users == null && states == null && categories == null) {
                list = eventRepository.findAll(pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());
            } else if (users == null && states == null) {
                list = eventRepository.getEventsByCategoryIdIn(categories, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (users == null && categories == null) {
                list = eventRepository.getEventsByStateIn(stateEnum, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (users != null && states == null && categories == null) {
                list = eventRepository.getEventsByInitiatorIdIn(users, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (users == null) {
                list = eventRepository.getEventsByCategoryIdInAndStateIn(categories, stateEnum, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (states != null && categories == null) {
                list = eventRepository.getEventsByInitiatorIdInAndStateIn(users, stateEnum, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (states == null) {
                list = eventRepository.getEventsByInitiatorIdInAndCategoryIdIn(users, categories, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else {
                list = eventRepository.getEventsByInitiatorIdInAndStateInAndCategoryIdIn(users, stateEnum, categories, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());
            }
        } else {
            LocalDateTime start = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDateTime end = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern(DATE_FORMAT));
            if (start.isAfter(end)) {
                throw new IllegalArgumentException();
            }
            if (users == null && states == null && categories == null) {
                list = eventRepository.getEventsByEventDateAfterAndEventDateBefore(start, end, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (users == null && states == null) {
                list = eventRepository.getEventsByCategoryIdInAndEventDateAfterAndEventDateBefore(
                                categories, start, end, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (users == null && categories == null) {
                list = eventRepository.getEventsByStateInAndEventDateAfterAndEventDateBefore(
                                stateEnum, start, end, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (users != null && states == null && categories == null) {
                list = eventRepository.getEventsByInitiatorIdInAndEventDateAfterAndEventDateBefore(
                                users, start, end, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (users == null) {
                list = eventRepository.getEventsByStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                                stateEnum, categories, start, end, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (states != null && categories == null) {
                list = eventRepository.getEventsByInitiatorIdInAndStateInAndEventDateAfterAndEventDateBefore(
                                users, stateEnum, start, end, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else if (states == null) {
                list = eventRepository.getEventsByInitiatorIdInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                                users, categories, start, end, pageable).stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());

            } else
                list = eventRepository.getEventsByInitiatorIdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                                users, stateEnum, categories, start, end, pageable)
                        .stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList());
        }

        return list;
    }

    @Transactional
    @Override
    public EventFullDto updateEventAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event oldEvent = eventRepository.getEventsById(eventId);

        validateUpdateEventAdmin(oldEvent, updateEventAdminRequest);

        if (updateEventAdminRequest.getLocation() != null) {
            Location location = locationRepository.save(updateEventAdminRequest.getLocation());
            updateEventAdminRequest.setLocation(location);
        }

        Category newCategory = updateEventAdminRequest.getCategory() == null ?
                oldEvent.getCategory() : categoryRepository.getById(updateEventAdminRequest.getCategory());

        Event upEvent = oldEvent;
        if (updateEventAdminRequest.getStateAction() != null) {
            if (updateEventAdminRequest.getStateAction().equals("PUBLISH_EVENT")) {
                upEvent = EventMapper.toEvent(updateEventAdminRequest, oldEvent, newCategory);
                upEvent.setPublishedOn(LocalDateTime.now());
                upEvent.setState(State.PUBLISHED);
            }
            if (updateEventAdminRequest.getStateAction().equals("REJECT_EVENT")) {
                upEvent.setState(State.CANCELED);

            }
        }
        upEvent.setId(eventId);

        return EventMapper.toEventFullDto(eventRepository.save(upEvent));
    }


    private void validateUpdateEventAdmin(Event oldEvent, UpdateEventAdminRequest updateEventAdminRequest) {
        if (oldEvent == null) {
            throw new NotFoundException("Event not found.");
        }

        LocalDateTime start = oldEvent.getEventDate();
        if (oldEvent.getPublishedOn() != null && start.isAfter(oldEvent.getPublishedOn().plusHours(1))) {
            throw new EventDateException("Start time is before event date");
        }
        if (updateEventAdminRequest.getEventDate() != null) {
            LocalDateTime newEventDate = LocalDateTime.parse(updateEventAdminRequest.getEventDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDateTime currentTime = LocalDateTime.now();
            if (newEventDate.isBefore(currentTime) || newEventDate.isEqual(currentTime)) {
                throw new IllegalArgumentException("Start time before or equals event date");
            }
        }

        if (oldEvent.getState() != null && !oldEvent.getState().equals(State.PENDING) && updateEventAdminRequest.getStateAction().equals("PUBLISH_EVENT")) {
            throw new StateArgumentException("Wrong state: PUBLISHED OR CANCELED");
        }
        if (oldEvent.getState() != null && oldEvent.getState().equals(State.PUBLISHED) && updateEventAdminRequest.getStateAction().equals("REJECT_EVENT")) {
            throw new StateArgumentException("Wrong state: PUBLISHED");
        }
    }

    @Transactional
    @Override
    public List<EventShortDto> getEventsAndStatsPublic(HttpServletRequest request, String text, List<Long> categories,
                                                       Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                       Boolean onlyAvailable, String sort, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        LocalDateTime timeNow = LocalDateTime.now();

        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new IllegalArgumentException("Range start time is after range end time");
            }
        }

        List<Event> list = new ArrayList<>();
        if (text != null) {
            text = "%" + text + "%";
        }
        if (paid != null) {
            if (categories != null && text != null) {
                list = eventRepository.getEvents(
                        State.PUBLISHED.toString(), categories, paid, timeNow, text, pageable);

            } else if (text == null && categories != null) {
                list = eventRepository.getEvents(
                        State.PUBLISHED.toString(), categories, paid, timeNow, pageable);

            } else if (text != null) {
                list = eventRepository.getEvents(
                        State.PUBLISHED.toString(), paid, timeNow, text, pageable);
            } else {
                list = eventRepository.getEvents(
                        State.PUBLISHED.toString(), paid, timeNow, pageable);
            }

        } else {
            if (rangeStart == null && rangeEnd == null) {
                if (categories != null && text != null) {
                    list = eventRepository.getEvents(
                            State.PUBLISHED.toString(), categories, timeNow, text, pageable);
                } else if (text == null && categories != null) {
                    list = eventRepository.getEvents(State.PUBLISHED.toString(),
                            categories, timeNow, pageable);
                } else if (text != null) {
                    list = eventRepository.getEvents(State.PUBLISHED.toString(),
                            timeNow, text, pageable);
                } else {
                    list = eventRepository.getEvents(State.PUBLISHED.toString(),
                            timeNow, pageable);
                }
            } else {
                if (sort != null && sort.equals("EVENT_DATE")) {
                    list = eventRepository.getEvents(State.PUBLISHED.toString(),
                            categories, rangeStart, rangeEnd, text, pageable);
                } else if (text == null && categories != null) {
                    list = eventRepository.getEvents(State.PUBLISHED.toString(),
                            categories, rangeStart, rangeEnd, pageable);

                } else if (text != null) {
                    list = eventRepository.getEvents(State.PUBLISHED.toString(), rangeStart,
                            rangeEnd, text, pageable);
                } else {
                    list = eventRepository.getEvents(State.PUBLISHED.toString(),
                            rangeStart, rangeEnd, pageable);
                }
            }
        }

        EndpointHitDto endpointHitDto = new EndpointHitDto(null, "main-service", request.getRequestURI(),
                request.getRemoteAddr(), timeNow.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));

        try {
            statsClient.addRequest(request.getRemoteAddr(), endpointHitDto);
        } catch (
                RuntimeException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }

        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        return list.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto getEventByIdAndStatsPublic(HttpServletRequest request, Long eventId) {
        Event event = eventRepository.getEventByIdAndState(eventId, State.PUBLISHED);
        if (event == null) {
            throw new NotFoundException("Event not found.");
        }
        String timeStart = event.getCreatedOn().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        String[] uris = {request.getRequestURI()};

        ResponseEntity<Object> response = statsClient.getStats(request.getRequestURI(), timeStart, timeNow, uris, true);
        List<ViewStats> resp = (List<ViewStats>) response.getBody();
        if (resp.size() == 0) {
            event.setViews(event.getViews() + 1);
            eventRepository.save(event);
        }

        EndpointHitDto endpointHitDto = new EndpointHitDto(null,
                "main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                timeNow);

        statsClient.addRequest(request.getRemoteAddr(), endpointHitDto);

        return EventMapper.toEventFullDto(event);
    }
}