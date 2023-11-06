package ru.practicum.main.location.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LocationDto {
    private Double lat;
    private Double lon;
}