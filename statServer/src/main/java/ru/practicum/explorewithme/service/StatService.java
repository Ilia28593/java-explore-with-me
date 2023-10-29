package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.EndpointDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;

import java.util.Collection;

public interface StatService {
    EndpointDto createStatHit(EndpointDto endpointDto);

    Collection<ViewStatsDto> getStatHit(String start, String end,
                                        Collection<String> uris, boolean unique);
}
