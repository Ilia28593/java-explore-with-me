package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.EndpointDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.model.Endpoint;
import ru.practicum.explorewithme.model.ViewStats;

@Mapper(componentModel = "spring")
public interface StatMapper {
    EndpointDto endpointToEndPointDto(Endpoint endpointHit);

    Endpoint endpointDtoToEndPoint(EndpointDto endpointHitDto);

    ViewStatsDto statsToStatsDto(ViewStats viewStats);

    ViewStats statsDtoToStats(ViewStatsDto viewStatsDto);
}
