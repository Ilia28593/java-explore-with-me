package ru.practicum.main.location.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LocationDto {
    private Double lat;
    private Double lon;
}