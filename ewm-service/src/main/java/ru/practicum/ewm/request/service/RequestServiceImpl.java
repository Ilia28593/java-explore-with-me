package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.enums.EventState;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.ParticipationRequest;
import ru.practicum.ewm.request.RequestMapper;
import ru.practicum.ewm.request.RequestRepository;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.enums.ParticipationRequestStatus;
import ru.practicum.ewm.user.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(RuntimeException::new);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Cannot request participation in event that has not been published");
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new ConflictException("Initiator cannot request participation in its own event");
        }
        if (event.getRequestModeration()) {
            if (event.getConfirmedRequests().size() >= event.getParticipantLimit()) {
                throw new ConflictException("Participant limit exceeded");
            }
        } else {
            if (event.getRequests().size() >= event.getParticipantLimit()) {
                throw new ConflictException("Participant limit exceeded");
            }
        }

        ParticipationRequestDto request;
        try {
            request = RequestMapper.toRequestDto(requestRepository.save(ParticipationRequest.of(userId, eventId)));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Duplicate request");
        }

        return request;
    }

    @Override
    public Collection<ParticipationRequestDto> findRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return RequestMapper.toRequestDto(requestRepository.findByRequesterId(userId));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Participation request not found"));
        if (!request.getRequesterId().equals(userId)) {
            throw new NotFoundException("Participation request not found");
        }
        if (request.getStatus() == ParticipationRequestStatus.REJECTED ||
                request.getStatus() == ParticipationRequestStatus.CANCELED) {
            throw new ConflictException("Incorrect cancellation");
        }
        request.setStatus(ParticipationRequestStatus.CANCELED);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }
}
