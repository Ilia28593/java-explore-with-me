package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.UserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserDto addUserAdmin(UserRequest userRequest);

    Collection<UserDto> getUsersAdmin(Collection<Long> ids, Integer from, Integer size);

    void deleteUserAdmin(Long id);
    User getUserById(Long id);
}
