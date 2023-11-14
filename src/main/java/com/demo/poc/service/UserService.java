package com.demo.poc.service;

import com.demo.poc.dto.UserDto;
import com.demo.poc.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    List<UserDto> getUsersWithPagination(Pageable page);

    UserDto updateUser(UserDto user);

    void deleteUser(Long userId);
}