package com.register.application.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String message, LocalDateTime timestamp) {
}
