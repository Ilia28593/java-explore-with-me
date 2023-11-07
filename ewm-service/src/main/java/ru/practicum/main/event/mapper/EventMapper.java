package ru.practicum.main.event.mapper;


import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.State;
import ru.practicum.main.location.mapper.LocationMapper;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.main.constant.Constants.DATE_FORMAT;

public class EventMapper {
    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto()
                .setId(event.getId())
                .setAnnotation(event.getAnnotation())
                .setCategory(CategoryMapper.toCategoryDto(event.getCategory()))
                .setConfirmedRequests(event.getConfirmedRequests())
                .setCreatedOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setDescription(event.getDescription())
                .setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setInitiator(UserMapper.toUserShortDto(event.getInitiator()))
                .setLocation(LocationMapper.toLocationDto(event.getLocation()))
                .setPaid(event.getPaid())
                .setParticipantLimit(event.getParticipantLimit())
                .setPublishedOn(event.getPublishedOn() == null ? null : event.getPublishedOn().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setRequestModeration(event.getRequestModeration())
                .setState(event.getState() == null ? null : event.getState().toString())
                .setTitle(event.getTitle())
                .setViews(event.getViews());
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto()
                .setId(event.getId())
                .setAnnotation(event.getAnnotation())
                .setCategory(CategoryMapper.toCategoryDto(event.getCategory()))
                .setConfirmedRequests(event.getConfirmedRequests())
                .setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setInitiator(UserMapper.toUserShortDto(event.getInitiator()))
                .setPaid(event.getPaid())
                .setTitle(event.getTitle())
                .setViews(event.getViews());
    }

    public static Event toEvent(NewEventDto newEventDto, User initiator, Category category) {
        return new Event()
                .setAnnotation(newEventDto.getAnnotation())
                .setCategory(category)
                .setCreatedOn(LocalDateTime.now())
                .setDescription(newEventDto.getDescription())
                .setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setInitiator(initiator)
                .setLocation(newEventDto.getLocation())
                .setConfirmedRequests(0L)
                .setPaid(newEventDto.getPaid() == null ? false : newEventDto.getPaid())
                .setParticipantLimit(newEventDto.getParticipantLimit() == null ? 0 : newEventDto.getParticipantLimit())
                .setRequestModeration(newEventDto.getRequestModeration() == null ? true : newEventDto.getRequestModeration())
                .setState(State.PENDING)
                .setTitle(newEventDto.getTitle())
                .setViews(0L);
    }

    public static Event toEvent(UpdateEventAdminRequest updateEventAdminRequest, Event oldEvent, Category category) {
        return new Event()
                .setId(oldEvent.getId())
                .setAnnotation(updateEventAdminRequest.getAnnotation() == null ? oldEvent.getAnnotation() : updateEventAdminRequest.getAnnotation())
                .setCategory(updateEventAdminRequest.getCategory() == null ? oldEvent.getCategory() : category)
                .setConfirmedRequests(oldEvent.getConfirmedRequests())
                .setCreatedOn(oldEvent.getCreatedOn())
                .setDescription(updateEventAdminRequest.getDescription() == null ? oldEvent.getDescription() : updateEventAdminRequest.getDescription())
                .setEventDate(updateEventAdminRequest.getEventDate() == null ? oldEvent.getEventDate() : LocalDateTime.parse(updateEventAdminRequest.getEventDate(), DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setInitiator(oldEvent.getInitiator())
                .setLocation(updateEventAdminRequest.getLocation() == null ? oldEvent.getLocation() : updateEventAdminRequest.getLocation())
                .setPaid(updateEventAdminRequest.getPaid() == null ? oldEvent.getPaid() : updateEventAdminRequest.getPaid())
                .setParticipantLimit(updateEventAdminRequest.getParticipantLimit() == null ? oldEvent.getParticipantLimit() : updateEventAdminRequest.getParticipantLimit())
                .setRequestModeration(updateEventAdminRequest.getRequestModeration() == null ? oldEvent.getRequestModeration() : updateEventAdminRequest.getRequestModeration())
                .setState(oldEvent.getState())
                .setTitle(updateEventAdminRequest.getTitle() == null ? oldEvent.getTitle() : updateEventAdminRequest.getTitle())
                .setViews(oldEvent.getViews());
    }

    public static Event toEvent(UpdateEventUserRequest updateEventUserRequest, Event oldEvent, Category category) {
        return new Event()
                .setId(oldEvent.getId())
                .setAnnotation(updateEventUserRequest.getAnnotation() == null ? oldEvent.getAnnotation() : updateEventUserRequest.getAnnotation())
                .setCategory(updateEventUserRequest.getCategory() == null ? oldEvent.getCategory() : category)
                .setConfirmedRequests(oldEvent.getConfirmedRequests())
                .setCreatedOn(oldEvent.getCreatedOn())
                .setDescription(updateEventUserRequest.getDescription() == null ? oldEvent.getDescription() : updateEventUserRequest.getDescription())
                .setEventDate(updateEventUserRequest.getEventDate() == null ? oldEvent.getEventDate() : LocalDateTime.parse(updateEventUserRequest.getEventDate(), DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setInitiator(oldEvent.getInitiator())
                .setLocation(updateEventUserRequest.getLocation() == null ? oldEvent.getLocation() : updateEventUserRequest.getLocation())
                .setPaid(updateEventUserRequest.getPaid() == null ? oldEvent.getPaid() : updateEventUserRequest.getPaid())
                .setParticipantLimit(updateEventUserRequest.getParticipantLimit() == null ? oldEvent.getParticipantLimit() : updateEventUserRequest.getParticipantLimit())
                .setRequestModeration(updateEventUserRequest.getRequestModeration() == null ? oldEvent.getRequestModeration() : updateEventUserRequest.getRequestModeration())
                .setTitle(updateEventUserRequest.getTitle() == null ? oldEvent.getTitle() : updateEventUserRequest.getTitle())
                .setViews(oldEvent.getViews());
    }
}