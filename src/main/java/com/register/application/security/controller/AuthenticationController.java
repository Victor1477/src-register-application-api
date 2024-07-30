package com.register.application.security.controller;

import com.register.application.core.dto.ErrorResponseDTO;
import com.register.application.security.dto.TokenResponseDTO;
import com.register.application.security.dto.UserDTO;
import com.register.application.security.entity.User;
import com.register.application.security.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody UserDTO userDTO) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password());
            Authentication auth = authenticationManager.authenticate(usernamePassword);
            String token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok().body(new TokenResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(404)
                    .body(new ErrorResponseDTO(String.format("User not found for username: %s", userDTO.username()), LocalDateTime.now()));
        }
    }
}
