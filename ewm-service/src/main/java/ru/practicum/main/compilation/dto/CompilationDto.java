package ru.practicum.main.compilation.dto;


import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}