package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.constants.Constant.DATA_FORMAT_PATTERN;

@Data
@RequiredArgsConstructor
public class EndpointHitDto {
    @NotNull
    private String app;
    @NotNull
    private String uri;
    @NotNull
    private String ip;
    @NotNull
    @JsonFormat(pattern = DATA_FORMAT_PATTERN)
    private LocalDateTime timestamp;

}
