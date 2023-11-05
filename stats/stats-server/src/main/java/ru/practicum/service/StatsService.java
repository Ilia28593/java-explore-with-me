package ru.practicum.service;

import ru.practicum.statsDto.EndpointHitDto;
import ru.practicum.statsDto.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatsService {

    EndpointHitDto createStatHit(EndpointHitDto endpointHitDto);

    List<ViewStats> getStatHit(LocalDateTime start, LocalDateTime end, Collection<String> uris, boolean unique);
}
