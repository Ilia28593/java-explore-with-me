package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.model.EndpointHit;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ResponseEndpointHitDto;

@UtilityClass
public class EndpointHitMapper {

    public static EndpointHit endpointHitDtoToEndpointHit(EndpointHitDto endpointHitDto) {
        return new EndpointHit()
                .setApp(endpointHitDto.getApp())
                .setUri(endpointHitDto.getUri())
                .setIp(endpointHitDto.getIp())
                .setTimestamp(endpointHitDto.getTimestamp());
    }

    public static ResponseEndpointHitDto endpointHitToRequestEndpointHitDto(EndpointHit endpointHit) {
        return new ResponseEndpointHitDto()
                .setApp(endpointHit.getApp())
                .setUri(endpointHit.getUri())
                .setIp(endpointHit.getIp())
                .setTimestamp(endpointHit.getTimestamp());
    }
}
