package ru.practicum.mapper;


import ru.practicum.model.EndpointHit;
import ru.practicum.statsDto.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.constant.Constants.DATE_FORMAT;

public class StatsMapper {
    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return new EndpointHitDto()
                .setId(endpointHit.getId())
                .setApp(endpointHit.getApp())
                .setUri(endpointHit.getUri())
                .setIp(endpointHit.getIp())
                .setTimestamp(endpointHit.getTimestamp().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    }

    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return new EndpointHit()
                .setApp(endpointHitDto.getApp())
                .setUri(endpointHitDto.getUri())
                .setIp(endpointHitDto.getIp())
                .setTimestamp(LocalDateTime.parse(endpointHitDto.getTimestamp(), DateTimeFormatter.ofPattern(DATE_FORMAT)));
    }
}
