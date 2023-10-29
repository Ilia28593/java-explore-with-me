package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.EndpointDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.service.StatService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = "")
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public EndpointDto createStatHit(@RequestBody EndpointDto endpointHitDto) {
        return statService.createStatHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getStatHit(@RequestParam String start,
                                               @RequestParam String end,
                                               @RequestParam(required = false) Collection<String> uris,
                                               @RequestParam(defaultValue = "false") Boolean unique) {
        return statService.getStatHit(start, end, uris, unique);
    }
}