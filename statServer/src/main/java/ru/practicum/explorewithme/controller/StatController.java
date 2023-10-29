package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EndpointDto;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.service.StatService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static ru.practicum.explorewithme.Constants.DATE_TIME_PATTERN;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = "")
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public EndpointHitDto createStatHit(@RequestBody EndpointHitDto endpointHitDto) {
        return statService.createStatHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getStatHit(@RequestParam String start,
                                               @RequestParam String end,
                                               @RequestParam(required = false) Collection<String> uris,
                                               @RequestParam(defaultValue = "false") Boolean unique) {
        return statService.getStatHit(getDateFromEncodedString(start), getDateFromEncodedString(end), uris, unique);
    }

    private LocalDateTime getDateFromEncodedString(String date) {
        return LocalDateTime.parse(URLDecoder.decode(date, StandardCharsets.UTF_8),
                DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}