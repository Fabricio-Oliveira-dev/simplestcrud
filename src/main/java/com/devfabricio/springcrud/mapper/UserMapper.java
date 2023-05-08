package com.devfabricio.springcrud.mapper;

import com.devfabricio.springcrud.dto.UserDto;
import com.devfabricio.springcrud.entities.User;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        // Convert User JPA Entity into UserDto
        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
        return userDto;
    }

    // Convert UserDto into user JPA Entity
    public static User mapToUser(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );
        return user;
    }
}
