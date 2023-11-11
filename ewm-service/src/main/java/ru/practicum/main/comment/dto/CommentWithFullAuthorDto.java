package ru.practicum.main.comment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class CommentWithFullAuthorDto {
    private Long id;
    private String text;
    private Long eventId;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private User author;
}
