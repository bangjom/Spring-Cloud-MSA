package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true)
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }


    @GetMapping("/welcome")
    @Timed(value = "users.welcome", longTask = true)
    public String welcome() {
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        UserDto userDto = userService.createUser(UserMapper.INSTANCE.requestUserToUserDto(user));
        ResponseUser responseUser = UserMapper.INSTANCE.userDtoToResponseUser(userDto);
        return ResponseEntity.ok(responseUser);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        ArrayList<ResponseUser> result = new ArrayList<>();
        userService.getUserByAll()
                .forEach(user -> result.add(UserMapper.INSTANCE.entityToResponseUser(user)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto finduser = userService.getUserByUserId(userId);
        ResponseUser responseUser = UserMapper.INSTANCE.userDtoToResponseUser(finduser);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

}
