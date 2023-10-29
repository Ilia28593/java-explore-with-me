package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.EndpointHitDto;
import ru.practicum.explorewithme.dto.ViewStatsDto;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.model.ViewStats;

@Mapper(componentModel = "spring")
public interface StatMapper {
    EndpointHitDto endpointToEndPointDto(EndpointHit endpointHit);

    EndpointHit endpointDtoToEndPoint(EndpointHitDto endpointHitDto);

    ViewStatsDto statsToStatsDto(ViewStats viewStats);

    ViewStats statsDtoToStats(ViewStatsDto viewStatsDto);
}
