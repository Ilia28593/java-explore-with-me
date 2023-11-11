package ru.practicum.main.controllers.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentWithFullAuthorDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin")
public class CommentAdminController {

    private final CommentService commentService;

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentByIdForAdmin(@Positive @PathVariable Long userId, @Positive @PathVariable Long commentId) {
        commentService.deleteCommentByIdForAdmin(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/events/{eventId}/comments")
    public ResponseEntity<List<CommentWithFullAuthorDto>> getCommentsForAdmin(@PathVariable Long eventId,
                                                                              @PositiveOrZero @RequestParam(name = "from",
                                                                                      defaultValue = "0") Integer from,
                                                                              @Positive @RequestParam(name = "size",
                                                                                      defaultValue = "10") Integer size) {
        return new ResponseEntity<>(commentService.getCommentsForAdmin(eventId, from, size), HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentWithFullAuthorDto> getCommentByIdForAdmin(@Positive @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getCommentByIdForAdmin(commentId), HttpStatus.OK);
    }
}