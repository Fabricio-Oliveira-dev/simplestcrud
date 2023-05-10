package com.devfabricio.springcrud.servicesImpl;

import com.devfabricio.springcrud.dto.UserDto;
import com.devfabricio.springcrud.entities.User;
import com.devfabricio.springcrud.exceptions.EmailAlreadyExistsException;
import com.devfabricio.springcrud.exceptions.ResourceNotFoundException;
import com.devfabricio.springcrud.mapper.StructUserMapper;
import com.devfabricio.springcrud.mapper.UserMapper;
import com.devfabricio.springcrud.repositories.UserRepository;
import com.devfabricio.springcrud.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        // Convert UserDto into user JPA Entity
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if(optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists for Users");
        }

        User user = StructUserMapper.MAPPER.mapToUser(userDto);
        User savedUser = userRepository.save(user);

        // Convert User JPA Entity to UserDto
        UserDto savedUserDto = StructUserMapper.MAPPER.mapToUserDto(savedUser);
        return savedUserDto;
    }

    @Override
    @Transactional
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );
        return StructUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> StructUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user.getId())
        );
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        userRepository.save(existingUser);

        User updatedUser = userRepository.save(existingUser);
        return StructUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );
        userRepository.deleteById(userId);
    }
}
