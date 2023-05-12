package com.caja.ideal.mapper;

import java.time.LocalDateTime;

public record TaskMapper(String title, String description, Boolean finished, LocalDateTime createdAt) {
}
