package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.enums.EventSort;
import ru.practicum.ewm.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Collection<EventShortDto>> findEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) EventSort sort,
            @RequestParam(required = false) Long[] categories,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            HttpServletRequest request) {
        return new ResponseEntity<>(eventService.findEvents(text, paid, onlyAvailable, sort, categories, rangeStart, rangeEnd, from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> findEvent(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(eventService.findEvent(id), HttpStatus.OK);
    }
}
