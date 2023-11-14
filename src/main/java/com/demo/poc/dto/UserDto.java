package com.demo.poc.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private String streetName;
    private String cityName;
    private String state;
    private Integer pincode;
}
