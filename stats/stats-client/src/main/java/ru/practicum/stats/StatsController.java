package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.statsDto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsClient statsClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> addUser(HttpServletRequest request, @RequestBody EndpointHitDto endpointHitDto) {
        return statsClient.addRequest(request.getRemoteAddr(), endpointHitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getAll(HttpServletRequest request,
                                         @RequestParam(name = "start") String start,
                                         @RequestParam(name = "end") String end,
                                         @RequestParam(required = false, name = "uris") String[] uris,
                                         @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return statsClient.getStats(request.getRemoteAddr(), start, end, uris, unique);
    }
}
