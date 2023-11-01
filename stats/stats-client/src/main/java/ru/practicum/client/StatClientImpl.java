package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatClientImpl implements StatClient {
    @Value("${stats-server.url}")
    private final String serverURL;
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    private void prepareHeader() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Override
    public ResponseEntity<String> saveHit(String app, String uri, String ip, LocalDateTime timestamp) {
        prepareHeader();
        EndpointDto endpointDto = EndpointDto.builder().app(app).uri(uri).ip(ip).timestamp(timestamp).build();
        return this.restTemplate.postForEntity(serverURL + "/hit", new HttpEntity<>(endpointDto, headers), String.class);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Collection<StatsDto>> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean unique) {
        prepareHeader();
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);
        params.put("uris", uris);
        params.put("unique", unique);

        return restTemplate.exchange(serverURL + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                HttpMethod.GET, new HttpEntity(headers), new ParameterizedTypeReference<>() {
                }, params);
    }
}