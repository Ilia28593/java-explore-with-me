package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

import java.util.Collection;

public interface UserService {

    User getUserIfExist(Long userId);

    UserDto addUserAdmin(NewUserRequest newUserRequest);

    Collection<UserDto> getUsersAdmin(Collection<Long> ids, Integer from, Integer size);

    void deleteUserAdmin(Long id);
}
