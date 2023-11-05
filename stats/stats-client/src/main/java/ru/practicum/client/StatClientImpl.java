package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatClientImpl implements StatClient {
    @Value("${spring.application.name}")
    private final String application;
    @Value("${stats-server.url}")
    private final String serverURL;
    private final RestTemplate restTemplate;

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Override
    public ResponseEntity<String> saveHit(String app, String uri, String ip, LocalDateTime timestamp) {
        return this.restTemplate.postForEntity(
                serverURL + "/hit",
                new HttpEntity<>(
                        new EndpointDto()
                                .setUri(uri)
                                .setIp(ip)
                                .setApp(app)
                                .setTimestamp(timestamp),
                        getHeader()),
                String.class);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<List<StatsDto>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);
        params.put("uris", uris);
        params.put("unique", unique);

        return restTemplate.exchange(
                serverURL + "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                HttpMethod.GET,
                new HttpEntity<>(getHeader()),
                new ParameterizedTypeReference<>() {
                },
                params
        );
    }

    public List<StatsDto> getStats(List<String> uris) {
        if (uris == null || uris.size() == 0) return new ArrayList<>();
        log.info("URIS:");
        log.info(uris.toString());

        String baseUrl = serverURL + "/stats";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("uris", StringUtils.join(uris, ','))
                .queryParam("start","2000-01-01 00:00:00")
                .queryParam("end",LocalDateTime.now().format(formatter)
                ).build();


        StatsDto[] stats = restTemplate.getForObject(uri.toString(), StatsDto[].class);

        return Arrays.stream(stats).collect(Collectors.toUnmodifiableList());
    }
}