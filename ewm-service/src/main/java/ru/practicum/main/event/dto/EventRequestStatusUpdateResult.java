package ru.practicum.main.event.dto;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.main.participation.dto.ParticipationRequestDto;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}