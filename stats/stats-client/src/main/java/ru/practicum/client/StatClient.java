package ru.practicum.client;

import org.springframework.http.ResponseEntity;
import ru.practicum.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatClient {
    ResponseEntity<String> saveHit(String app, String uri, String ip, LocalDateTime timestamp);

    ResponseEntity<Collection<StatsDto>> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean unique);
}