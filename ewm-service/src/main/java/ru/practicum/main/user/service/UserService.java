package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface UserService {
    UserDto addUserAdmin(NewUserRequest userRequest);

    List<UserDto> getUsersAdmin(List<Long> ids, Integer from, Integer size);

    void deleteUserAdmin(Long id);

    User getUserById(Long id);
}
