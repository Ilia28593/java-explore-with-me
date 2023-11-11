package ru.practicum.main.exception;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ApiError  {
    private final List<String> errors;
    private final String message;
    private final String reason;
    private final HttpStatus status;
    private final String timestamp;
}
