package ru.practicum.service;

import ru.practicum.EndpointDto;
import ru.practicum.StatsDto;
import ru.practicum.model.Endpoint;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatService {
    Endpoint createStatHit(EndpointDto endpointHitDto);

    Collection<StatsDto> getStatHit(LocalDateTime start, LocalDateTime end,
                                    Collection<String> uris, boolean unique);
}
