package ru.practicum.main.controllers.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getEventsAdmin(@RequestParam(required = false) List<Long> users,
                                                             @RequestParam(required = false) List<String> states,
                                                             @RequestParam(required = false) List<Long> categories,
                                                             @RequestParam(required = false) String rangeStart,
                                                             @RequestParam(required = false) String rangeEnd,
                                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get request events by admin.");
        return new ResponseEntity<>(eventService.getEventsAdmin(users, states, categories, rangeStart, rangeEnd,
                from, size), HttpStatus.OK);
    }


    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventAdmin(@NotNull @PathVariable(required = false) Long eventId,
                                                         @NotNull @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Updating request event by admin.");
        return new ResponseEntity<>(eventService.updateEventAdmin(eventId, updateEventAdminRequest), HttpStatus.OK);
    }
}