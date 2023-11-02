package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.constant.Constants.DATA_TIME_PATTERN;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public class EndpointDto {
    @NotNull
    private String app;
    @NotBlank
    private String uri;
    @NotNull
    private String ip;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATA_TIME_PATTERN)
    private LocalDateTime timestamp;
}