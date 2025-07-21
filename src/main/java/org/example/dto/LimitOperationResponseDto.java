package org.example.dto;

import java.math.BigDecimal;

public record LimitOperationResponseDto(Long userId, BigDecimal availableLimit , BigDecimal reservedLimit, String message) {
}
