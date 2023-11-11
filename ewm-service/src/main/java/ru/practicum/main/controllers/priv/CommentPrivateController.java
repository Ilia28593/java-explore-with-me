package ru.practicum.main.controllers.priv;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Validated
@RestController
@AllArgsConstructor
public class CommentPrivateController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentDto> addCommentForUser(@NonNull @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.addCommentForUser(commentDto), HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentForUser(@Positive @PathVariable Long commentId,
                                                           @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateCommentForUser(commentId, commentDto), HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByIdForUser(@Positive @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getCommentByIdForUser(commentId), HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsForUser(@PathVariable Long eventId,
                                                               @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                               @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return new ResponseEntity<>(commentService.getCommentsForUser(eventId, from, size), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentByIdForUser(@Positive @PathVariable Long userId, @Positive @PathVariable Long commentId) {
        commentService.deleteCommentByIdForUser(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}