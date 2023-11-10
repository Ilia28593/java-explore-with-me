package ru.practicum.main.compilation.dto;


import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class UpdateCompilationRequest {
    private List<Long> events;
    private boolean pinned;
    @Size(min = 1, max = 50)
    private String title;
}