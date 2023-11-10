package ru.practicum.main.participation.mapper;

import ru.practicum.main.participation.dto.ParticipationRequestDto;
import ru.practicum.main.participation.model.ParticipationRequest;

import java.time.format.DateTimeFormatter;

import static ru.practicum.main.constant.Constants.DATE_FORMAT;

public class ParticipationMapper {
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto()
                .setId(participationRequest.getId())
                .setCreated(participationRequest.getCreated().format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .setEvent(participationRequest.getEvent())
                .setRequester(participationRequest.getRequester())
                .setStatus(participationRequest.getStatus().toString());
    }
}