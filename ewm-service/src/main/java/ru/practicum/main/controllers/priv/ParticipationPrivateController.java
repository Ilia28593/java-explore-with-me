package ru.practicum.main.controllers.priv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.participation.dto.ParticipationRequestDto;
import ru.practicum.main.participation.service.ParticipationService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class ParticipationPrivateController {

    private final ParticipationService participationService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> addParticipationRequestPrivate(@Positive @PathVariable(required = false) Long userId,
                                                                                  @Positive @RequestParam(required = false) Long eventId) {
        log.info("Post request add participation.");
        return ResponseEntity.status(HttpStatus.CREATED).body(participationService.addParticipationRequestPrivate(userId, eventId));
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getParticipationRequestPrivate(@NotNull @Positive @PathVariable Long userId) {
        log.info("Get request participation.");
        return ResponseEntity.ok(participationService.getParticipationRequest(userId));
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> updateRejectedParticipationRequestPrivate(@NotNull @Positive @PathVariable Long userId,
                                                                                             @NotNull @Positive @PathVariable(required = true,
                                                                                                     name = "requestId") Long requestId) {
        log.info("Updating request participation.");
        return ResponseEntity.ok(participationService.updateRejectedParticipationRequestPrivate(userId, requestId));
    }
}