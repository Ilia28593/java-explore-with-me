package ru.practicum.main.controllers.admin;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserAdminController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @NonNull @RequestBody NewUserRequest userRequest) {
        log.info("Post request add user.");
        return new ResponseEntity<>(userService.addUserAdmin(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getUsers(@RequestParam(required = false) Collection<Long> ids,
                                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get request received users.");
        return new ResponseEntity<>(userService.getUsersAdmin(ids, from, size), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@NonNull @Positive @PathVariable("userId") Long userId) {
        log.info("Delete request user.");
        userService.deleteUserAdmin(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}