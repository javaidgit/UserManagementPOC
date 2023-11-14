package com.demo.poc.controller;

import com.demo.poc.dto.Tweet;
import com.demo.poc.dto.UserDto;
import lombok.AllArgsConstructor;
import com.demo.poc.entity.User;
import com.demo.poc.service.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    // build create User REST API
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // build get user by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Build Get All Users REST API
    // http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("with_pagination")
    public ResponseEntity<List<UserDto>> getUsersWithPagination(
            @RequestParam(defaultValue = "10", required = false)
            Integer pageSize,
            @RequestParam(defaultValue = "0", required = false)
            Integer page
    ) throws Exception {

        Pageable paging  = PageRequest.of(page, pageSize);

        List<UserDto> userDtos = userService.getUsersWithPagination(paging);

        return new ResponseEntity<>(userDtos, HttpStatus.ACCEPTED);
    }

    // Build Update User REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/users/1
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                           @RequestBody UserDto userDto){
        userDto.setId(userId);
        UserDto updatedUser = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Build Delete User REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

    @GetMapping("/call-third-party-tweets")
    public List<Tweet> getTweets() {
        final String uri = getThirdPartyApiServiceUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Tweet>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Tweet>>(){});

        List<Tweet> result = response.getBody();
        //result.forEach(tweet -> System.out.println(tweet.toString()));
        return result;
    }

    private String getThirdPartyApiServiceUri() {
        return "http://localhost:" + 8080 + "/third-party-tweets";
    }

}