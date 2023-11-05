package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestService;

import javax.validation.ValidationException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> createRequest(@PathVariable Long userId,
                                                                 @RequestParam Long eventId) throws ValidationException {
        return new ResponseEntity<>(requestService.addRequest(userId, eventId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<ParticipationRequestDto>> findRequests(@PathVariable Long userId) {
        return new ResponseEntity<>(requestService.findRequests(userId), HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable Long userId,
                                                                 @PathVariable Long requestId) {
        return new ResponseEntity<>(requestService.cancelRequest(userId, requestId), HttpStatus.OK);
    }
}
