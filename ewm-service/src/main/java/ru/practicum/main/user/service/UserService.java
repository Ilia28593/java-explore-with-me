package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserRequest;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserDto addUserAdmin(UserRequest newUserRequest);

    Collection<UserDto> getUsersAdmin(List<Long> ids, Integer from, Integer size);

    void deleteUserAdmin(Long id);
}
