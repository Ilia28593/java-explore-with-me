package ru.practicum.ewm.compilation.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.Collection;

@Setter
@Getter
public class CompilationDto {

    private Long id;
    private Collection<EventShortDto> events;
    private Boolean pinned;
    private String title;

}
