package org.example.dto;

import java.math.BigDecimal;

public record LimitResponseDto(Long userId, BigDecimal dailyLimit) {

}
