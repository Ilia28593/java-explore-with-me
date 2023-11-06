package ru.practicum.main.controllers.admin;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserRequest;
import ru.practicum.main.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserAdminController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(HttpServletRequest request,
                                           @Valid @NonNull @RequestBody UserRequest newUserRequest) {
        log.info("Post request received: add user.");
        return new ResponseEntity<>(userService.addUserAdmin(newUserRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(HttpServletRequest request,
                                                  @RequestParam(required = false) List<Long> ids,
                                                  @PositiveOrZero
                                                  @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @Positive
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get request received: get users.");
        return new ResponseEntity<>(userService.getUsersAdmin(ids, from, size), HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(HttpServletRequest request,
                           @NonNull @Positive @PathVariable("userId") Long userId) {
        log.info("Delete request received: delete user.");
        userService.deleteUserAdmin(userId);
    }
}