package com.devfabricio.springcrud.mapper;

import com.devfabricio.springcrud.dto.UserDto;
import com.devfabricio.springcrud.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StructUserMapper {

    StructUserMapper MAPPER = Mappers.getMapper(StructUserMapper.class);

    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);
}
