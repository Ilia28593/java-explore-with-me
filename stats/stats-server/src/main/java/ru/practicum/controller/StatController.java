package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statsDto.EndpointHitDto;
import ru.practicum.statsDto.ViewStats;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.constant.Constants.DATE_FORMAT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatController {

    private final StatsService statsService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public EndpointHitDto saveHit(@RequestBody EndpointHitDto endpointHitDto) {
        return statsService.createStatHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
                                    @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
                                    @RequestParam(required = false) Collection<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique){
        return statsService.getStatHit(start, end, uris, unique);
    }
}
