package com.register.application.controller;

import com.register.application.dto.ErrorResponseDTO;
import com.register.application.dto.UserDTO;
import com.register.application.exception.UserAlreadyExistsException;
import com.register.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            return ResponseEntity.status(201).build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDTO(String.format("User with username: %s already exists", userDTO.username()), LocalDateTime.now()));
        }
    }
}
