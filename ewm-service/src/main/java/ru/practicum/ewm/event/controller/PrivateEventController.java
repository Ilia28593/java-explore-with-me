package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Collection<EventFullDto>> findEvents(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(eventService.findEvents(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> findEvent(@PathVariable Long id) {
        return new ResponseEntity<>(eventService.findEvent(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EventFullDto> createEvent(@PathVariable Long userId,
                                                    @RequestBody @Valid NewEventDto eventDto) throws ValidationException {
        return new ResponseEntity<>(eventService.addEvent(userId, eventDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> patchEvent(@PathVariable Long userId,
                                                   @PathVariable Long eventId,
                                                   @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return new ResponseEntity<>(eventService.patchEventByInitiator(userId, eventId, updateEventUserRequest), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> changeRequestStatus(@PathVariable Long userId,
                                                                              @PathVariable Long eventId,
                                                                              @RequestBody EventRequestStatusUpdateRequest statusUpdateRequest) {
        return new ResponseEntity<>(eventService.changeRequestStatus(userId, eventId, statusUpdateRequest), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Collection<ParticipationRequestDto>> findEventRequests(@PathVariable Long userId,
                                                                                 @PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.findEventRequests(userId, eventId), HttpStatus.OK);
    }
}
