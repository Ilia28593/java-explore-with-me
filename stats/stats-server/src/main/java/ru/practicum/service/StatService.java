package ru.practicum.service;

import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatService {
    String createStatHit(EndpointDto endpointHitDto);

    Collection<StatsDto> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean unique);
}
