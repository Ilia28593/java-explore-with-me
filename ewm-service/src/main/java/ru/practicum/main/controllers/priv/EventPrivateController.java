package ru.practicum.main.controllers.priv;

import lombok.AllArgsConstructor;
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
        return ResponseEntity.ok(eventService.addEventPrivate(userId, newEventDto));
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEvents(@NotNull @Positive @PathVariable(required = false) Long userId,
                                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(eventService.getEventsPrivate(userId, from, size));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEvent(@Positive @PathVariable(required = false) Long userId,
                                                 @Positive @PathVariable(required = false) Long eventId) {
        return ResponseEntity.ok(eventService.getEventPrivate(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsEventsUser(@Positive @PathVariable Long userId,
                                                                               @Positive @PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getRequestsEventsUserPrivate(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventUserRequest(@Positive @PathVariable(required = false) Long userId,
                                                               @Positive @PathVariable(required = false) Long eventId,
                                                               @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return ResponseEntity.ok(eventService.updateEventPrivate(userId, eventId, updateEventUserRequest));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateEventRequestStatus(@Positive @PathVariable Long userId,
                                                                                   @Positive @PathVariable Long eventId,
                                                                                   @Valid @RequestBody EventRequestStatusUpdateRequest
                                                                                           eventRequestStatusUpdateRequest) {
        return ResponseEntity.ok(eventService.updateEventRequestStatusPrivate(userId, eventId, eventRequestStatusUpdateRequest));
    }
}