package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ResponseEndpointHitDto;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatService {
    ResponseEndpointHitDto createStatHit(EndpointHitDto endpointHitDto);

    Collection<ViewStats> getStatHit(LocalDateTime start, LocalDateTime end,
                                     Collection<String> uris, boolean unique);
}
