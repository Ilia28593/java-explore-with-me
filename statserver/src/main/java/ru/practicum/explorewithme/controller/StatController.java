package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.service.StatService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static ru.practicum.explorewithme.constants.Constant.DATA_FORMAT_PATTERN;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = "")
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public EndpointHit createStatHit(@RequestBody EndpointHitDto endpointHitDto) {
        return statService.createStatHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getStatHit(@RequestParam @DateTimeFormat(pattern = DATA_FORMAT_PATTERN) LocalDateTime start,
                                               @RequestParam @DateTimeFormat(pattern = DATA_FORMAT_PATTERN) LocalDateTime end,
                                               @RequestParam(required = false) ArrayList<String> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {
        return statService.getStatHit(start, end, uris, unique);
    }

}