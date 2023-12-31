package ru.practicum.main.user.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserShortDto {

    private Long id;
    private String name;
}