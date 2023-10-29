package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.controller.client.StatClient;
import ru.practicum.explorewithme.dto.EndpointDto;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(path = "")
public class StatController {
    private final StatClient statClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> createStatHit(@RequestBody @Validated EndpointDto endpointDto) {
        return statClient.createStat(endpointDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStatHit(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                             @RequestParam(required = false) Collection<String> uris,
                                             @RequestParam(defaultValue = "false") Boolean unique) {
        return statClient.getStat(start, end, uris, unique);
    }
}