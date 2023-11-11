package ru.practicum.main.comment.mapper;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentWithFullAuthorDto;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

import static ru.practicum.main.constant.Constants.timeNow;

public class CommentMapper {

    public static CommentWithFullAuthorDto toCommentWithFullAuthorDto(CommentDto commentDto, User user) {
        return new CommentWithFullAuthorDto()
                .setId(commentDto.getId())
                .setText(commentDto.getText())
                .setEvent(commentDto.getEventId())
                .setCreatedOn(commentDto.getCreatedOn())
                .setUpdatedOn(commentDto.getUpdatedOn())
                .setAuthor(user);
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto()
                .setId(comment.getId())
                .setText(comment.getText())
                .setEventId(comment.getEvent().getId())
                .setCreatedOn(comment.getCreatedOn())
                .setUpdatedOn(comment.getUpdatedOn())
                .setAuthorId(comment.getAuthor().getId());
    }

    public static Comment toComment(CommentDto commentDto, User user, Event event) {
        return new Comment()
                .setText(commentDto.getText())
                .setEvent(event)
                .setAuthor(user)
                .setCreatedOn(timeNow());
    }
}