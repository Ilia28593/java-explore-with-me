package ru.practicum.main.comment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private Long id;
    private String text;
    private Long event;
    private Long author;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
