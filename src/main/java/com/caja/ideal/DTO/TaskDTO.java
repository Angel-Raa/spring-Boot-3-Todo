package com.caja.ideal.DTO;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record TaskDTO(@Column(unique = true, length = 40) String title, String description, Boolean finished, LocalDateTime createdAt) {
}
