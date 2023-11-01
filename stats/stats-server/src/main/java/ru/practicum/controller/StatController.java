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

@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService statServiceImpl;

    @PostMapping(path = "/hit")
    public ResponseEntity<String> saveHit(@Validated @RequestBody EndpointDto endpointDto) {
        return ResponseEntity.ok(statServiceImpl.createStatHit(endpointDto));
    }

    @GetMapping(path = "/stats")
    public ResponseEntity<Collection<StatsDto>> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                         @RequestParam(required = false) Collection<String> uris,
                                                         @RequestParam(defaultValue = "false") boolean unique) {
        return ResponseEntity.ok(statServiceImpl.getStatHit(start, end, uris, unique));
    }
}