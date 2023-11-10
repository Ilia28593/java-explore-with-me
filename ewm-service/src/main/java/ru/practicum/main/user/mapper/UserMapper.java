package ru.practicum.main.user.mapper;

import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto()
                .setId(user.getId())
                .setName(user.getName());
    }

    public static User toUser(NewUserRequest userRequest) {
        return new User()
                .setName(userRequest.getName())
                .setEmail(userRequest.getEmail());
    }
}