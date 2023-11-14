package com.demo.poc.service.impl;

import com.demo.poc.dto.UserDto;
import com.demo.poc.entity.Address;
import com.demo.poc.mapper.UserMapper;
import com.demo.poc.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import com.demo.poc.entity.User;
import com.demo.poc.repository.UserRepository;
import com.demo.poc.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        // Convert UserDto into User JPA Entity
        User user = UserMapper.mapToUser(userDto);

        User userSavedToDB = this.userRepository.save(user);
        Address address = new Address();
        address.setStreetName(userDto.getStreetName());
        address.setCityName(userDto.getCityName());
        address.setState(userDto.getState());
        address.setPincode(userDto.getPincode());

        this.addressService.addAddress(address);
        userSavedToDB.setAddress(address);

        // Convert User JPA entity to UserDto
        UserDto savedUserDto = UserMapper.mapToUserDto(userSavedToDB);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersWithPagination(Pageable page) {
        Iterable<User> users = userRepository.findAll(page);
        return StreamSupport.stream(users.spliterator(), false)
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getId()).get();
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());

        Address existingAddress = existingUser.getAddress();
        existingAddress.setStreetName(userDto.getStreetName());
        existingAddress.setCityName(userDto.getCityName());
        existingAddress.setState(userDto.getState());
        existingAddress.setPincode(userDto.getPincode());

        existingUser.setAddress(existingAddress);
        this.addressService.addAddress(existingAddress);
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
