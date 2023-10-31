package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ResponseEndpointHitDto;
import ru.practicum.model.ViewStats;
import ru.practicum.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static ru.practicum.constants.Constant.DATA_FORMAT_PATTERN;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class StatsController {
    private final StatService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEndpointHitDto createHit(@RequestBody @Valid EndpointHitDto hit) {
        log.info("Create hit {}", hit);
        return service.createStatHit(hit);
    }

    @GetMapping("/stats")
    public Collection<ViewStats> getUri(@RequestParam @DateTimeFormat(pattern = DATA_FORMAT_PATTERN) LocalDateTime start,
                                        @RequestParam @DateTimeFormat(pattern = DATA_FORMAT_PATTERN) LocalDateTime end,
                                        @RequestParam(required = false) ArrayList<String> uris,
                                        @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Get uri  from={}, to={}", start, end);
        return service.getStatHit(start, end, uris, unique);
    }
}
