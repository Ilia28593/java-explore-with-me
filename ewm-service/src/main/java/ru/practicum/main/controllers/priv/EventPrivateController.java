package ru.practicum.main.controllers.priv;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.participation.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/events")
public class EventPrivateController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventFullDto> addEvent(@Positive @PathVariable Long userId,
                                                 @NotNull @Valid @RequestBody NewEventDto newEventDto) {
        return new ResponseEntity<>(eventService.addEventPrivate(userId, newEventDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEvents(@NotNull @Positive @PathVariable(required = false) Long userId,
                                                         @PositiveOrZero
                                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                         @Positive
                                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return new ResponseEntity<>(eventService.getEventsPrivate(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEvent(@Positive @PathVariable(required = false) Long userId,
                                                 @Positive @PathVariable(required = false) Long eventId) {
        return new ResponseEntity<>(eventService.getEventPrivate(userId, eventId), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsEventsUser(@Positive @PathVariable Long userId,
                                                                               @Positive @PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.getRequestsEventsUserPrivate(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventUserRequest(@Positive @PathVariable(required = false) Long userId,
                                                               @Positive @PathVariable(required = false) Long eventId,
                                                               @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return new ResponseEntity<>(eventService.updateEventPrivate(userId, eventId, updateEventUserRequest),
                HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateEventRequestStatus(@Positive @PathVariable Long userId,
                                                                                   @Positive @PathVariable Long eventId,
                                                                                   @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return new ResponseEntity<>(eventService.updateEventRequestStatusPrivate(
                userId, eventId, eventRequestStatusUpdateRequest), HttpStatus.OK);
    }
}