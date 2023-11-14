package com.demo.poc.mapper;

import com.demo.poc.dto.UserDto;
import com.demo.poc.entity.Address;
import com.demo.poc.entity.User;

public class UserMapper {

    // Convert User JPA Entity into UserDto
    public static UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress() != null ? user.getAddress().getStreetName() : null,
                user.getAddress() != null ? user.getAddress().getCityName() : null,
                user.getAddress() != null ? user.getAddress().getState() : null,
                user.getAddress() != null ? user.getAddress().getPincode() : null
        );
        return userDto;
    }

    // Convert UserDto into User JPA Entity
    public static User mapToUser(UserDto userDto){
        Address addressDetail = new Address();
        addressDetail.setStreetName(userDto.getStreetName());
        addressDetail.setCityName(userDto.getCityName());
        addressDetail.setState(userDto.getState());
        addressDetail.setPincode(userDto.getPincode());

        User user = new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                addressDetail
        );

        return user;
    }
}
