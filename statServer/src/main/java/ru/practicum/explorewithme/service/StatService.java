package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.EndpointDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatService {
    EndpointDto createStatHit(EndpointDto endpointDto);

    Collection<ViewStatsDto> getStatHit(LocalDateTime start, LocalDateTime end,
                                        Collection<String> uris, boolean unique);
}
