package ru.practicum.ewm.event.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
public class EventRequestStatusUpdateResult {

    private Collection<ParticipationRequestDto> confirmedRequests = new ArrayList<>();

    private Collection<ParticipationRequestDto> rejectedRequests = new ArrayList<>();

}
