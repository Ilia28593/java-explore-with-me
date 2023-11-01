package ru.practicum.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseEndpointHitDto {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;

}
