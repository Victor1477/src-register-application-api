package com.register.application.core.dto;

import com.register.application.security.models.Role;

public record UserDTO(String username, String password, Role role) {
}
