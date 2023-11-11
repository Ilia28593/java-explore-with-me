package ru.practicum.exception;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private  List<String> errors;
    private  String message;
    private  String reason;
    private  HttpStatus status;
    private  String timestamp;
}
