package com.register.application.dto;

import com.register.application.models.Role;

public record UserDTO(String username, String password, Role role) {
}
