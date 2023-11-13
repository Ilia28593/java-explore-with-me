package ru.practicum.main.controllers.priv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/events")
public class EventPrivateController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventFullDto> addEvent(@Positive @PathVariable Long userId,
                                                 @NotNull @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Post request event.");
        return new ResponseEntity<>(eventService.addEventPrivate(userId, newEventDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEvents(@NotNull @Positive @PathVariable(required = false) Long userId,
                                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get request events.");
        return ResponseEntity.ok(eventService.getEventsPrivate(userId, from, size));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEvent(@Positive @PathVariable(required = false) Long userId,
                                                 @Positive @PathVariable(required = false) Long eventId) {
        log.info("Get request event.");
        return ResponseEntity.ok(eventService.getEventPrivate(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsEventsUser(@Positive @PathVariable Long userId,
                                                                               @Positive @PathVariable Long eventId) {
        log.info("Get request events of user.");
        return ResponseEntity.ok(eventService.getRequestsEventsUserPrivate(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventUserRequest(@Positive @PathVariable(required = false) Long userId,
                                                               @Positive @PathVariable(required = false) Long eventId,
                                                               @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Patch request update event.");
        return ResponseEntity.ok(eventService.updateEventPrivate(userId, eventId, updateEventUserRequest));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateEventRequestStatus(@Positive @PathVariable Long userId,
                                                                                   @Positive @PathVariable Long eventId,
                                                                                   @Valid @RequestBody EventRequestStatusUpdateRequest
                                                                                               eventRequestStatusUpdateRequest) {
        log.info("Patch request received: update status event.");
        return ResponseEntity.ok(eventService.updateEventRequestStatusPrivate(userId, eventId, eventRequestStatusUpdateRequest));
    }
}