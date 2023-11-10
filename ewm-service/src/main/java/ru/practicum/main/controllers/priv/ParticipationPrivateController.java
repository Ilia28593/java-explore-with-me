package ru.practicum.main.controllers.priv;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.participation.dto.ParticipationRequestDto;
import ru.practicum.main.participation.service.ParticipationService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class ParticipationPrivateController {

    private final ParticipationService participationService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> addParticipationRequestPrivate(@Positive @PathVariable(required = false) Long userId,
                                                                                  @Positive @RequestParam(required = false) Long eventId) {
        return new ResponseEntity<>(participationService.addParticipationRequestPrivate(userId, eventId),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getParticipationRequestPrivate(@NotNull @Positive @PathVariable Long userId) {
        return new ResponseEntity<>(participationService.getParticipationRequestPrivate(userId), HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> updateRejectedParticipationRequestPrivate(@NotNull @Positive @PathVariable Long userId,
                                                                                             @NotNull @Positive @PathVariable(required = true,
                                                                                                     name = "requestId") Long requestId) {
        return new ResponseEntity<>(participationService.updateRejectedParticipationRequestPrivate(userId, requestId),
                HttpStatus.OK);
    }
}