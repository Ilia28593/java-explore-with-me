package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.EndpointDto;
import ru.practicum.model.Endpoint;

@Mapper(componentModel = "spring")
public interface EndpointMapper {
    EndpointDto toDto(Endpoint endpoint);

    Endpoint fromDto(EndpointDto endpointDto);
}