package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LimitOperationResponseDto;
import org.example.dto.LimitRequestDto;
import org.example.dto.LimitResponseDto;
import org.example.service.LimitService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/limits")
public class UserLimitController {
    private final LimitService limitService;

    @GetMapping("/user/{userId}")
    public LimitResponseDto getUserLimit(@PathVariable Long userId) {
        return limitService.getLimit(userId);
    }

    @PostMapping("/user/{userId}/reserve")
    public LimitOperationResponseDto reserveLimit(@PathVariable Long userId,
                                                  @RequestBody LimitRequestDto request) {
        return limitService.reserveLimit(userId,request);
    }

    @PostMapping("/user/{userId}/confirm")
    public LimitOperationResponseDto confirmPayment(@PathVariable Long userId,
                                                    @RequestBody LimitRequestDto request) {
        return limitService.applyReservedLimit(userId,request);
    }
    @PostMapping("/user/{userId}/cancel")
    public LimitOperationResponseDto cancelPayment(@PathVariable Long userId,
                                                   @RequestBody LimitRequestDto request) {
        return limitService.rollbackLimit(userId,request);
    }

}
