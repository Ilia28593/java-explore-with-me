package ru.practicum.main.user.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserShortDto {

    private Long id;
    private String name;
}