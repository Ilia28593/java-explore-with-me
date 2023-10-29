package ru.practicum.explorewithme.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.dto.EndpointHitDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.practicum.explorewithme.Constants.API_PREFIX;
import static ru.practicum.explorewithme.Constants.DATE_TIME_PATTERN;

@Service
public class StatClient extends BaseClient {


    @Autowired
    public StatClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> createStat(EndpointHitDto endpointDto) {
        return post("hit", endpointDto);
    }

    public ResponseEntity<Object> getStat(LocalDateTime start, LocalDateTime end,
                                          Collection<String> uris, Boolean unique) {
        Map<String, Object> entity = new HashMap<>();
        entity.put("start", encodeDateToString(start));
        entity.put("end", encodeDateToString(end));
        entity.put("unique", unique);
        if (uris != null) {
            entity.put("uris", String.join(",", uris));
            return get("stats?start={start}&end={end}&uris={uris}&unique={unique}", entity);
        }
        return get("stats?start={start}&end={end}&unique={unique}", entity);
    }

    private String encodeDateToString(LocalDateTime date) {
        return URLEncoder.encode(date.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)), StandardCharsets.UTF_8);
    }
}
