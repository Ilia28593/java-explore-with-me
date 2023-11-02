package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.constant.Constants.DATA_TIME_PATTERN;

@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping(path = "/hit")
    public ResponseEntity<String> saveHit(@Validated @RequestBody EndpointDto endpointDto) {
        return ResponseEntity.ok(statService.createStatHit(endpointDto));
    }

    @GetMapping(path = "/stats")
    public ResponseEntity<Collection<StatsDto>> getStats(
            @RequestParam @DateTimeFormat(pattern = DATA_TIME_PATTERN) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = DATA_TIME_PATTERN) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique
    ) {
        return ResponseEntity.ok(statService.getStatHit(start, end, uris, unique));
    }
}