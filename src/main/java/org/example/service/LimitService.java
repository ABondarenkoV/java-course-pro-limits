package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.LimitsProperties;
import org.example.dto.LimitOperationResponseDto;
import org.example.dto.LimitRequestDto;
import org.example.dto.LimitResponseDto;
import org.example.entity.Limit;
import org.example.exception.InsufficientLimitException;
import org.example.repository.LimitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitService {

    private final LimitRepository limitRepository;
    private final LimitsProperties limitsProperties;

    public void resetDailyLimitsForAllUsers() {
        limitRepository.resetAllLimits(limitsProperties.getDefaultDailyLimit());
    }

    private Limit findOrCreateLimit(Long userId) {
        return limitRepository.findById(userId)
                .orElseGet(() -> {
                    log.info("Пользователь не найден : {} - Выполняется создание", userId);

                    Limit newLimit = new Limit();
                    newLimit.setUserId(userId);
                    newLimit.setAvailableLimit(limitsProperties.getDefaultDailyLimit());

                    return limitRepository.save(newLimit);
                });
    }

    @Transactional
    public LimitResponseDto getLimit(Long userId){
        log.info("Запрос лимита для пользователя: {}", userId);

        Limit limit = findOrCreateLimit(userId);
        return new LimitResponseDto(limit.getUserId(), limit.getAvailableLimit());
    }

    @Transactional
    public LimitOperationResponseDto reserveLimit(Long userId, LimitRequestDto request){
        log.info("Выполнение резервирования лимита для пользователя : {}", userId);
        Limit limit = findOrCreateLimit(userId);

        if(limit.getAvailableLimit().compareTo(request.amount()) < 0) {
            throw new InsufficientLimitException("Лимит пользователя ID : " + userId +" израсходован!");
        }
        limit.setAvailableLimit(limit.getAvailableLimit().subtract(request.amount()));
        limit.setReservedLimit(limit.getReservedLimit().add(request.amount()));
        limit.setLastUpdate(LocalDateTime.now());
        limitRepository.save(limit);

        return new LimitOperationResponseDto(limit.getUserId(),limit.getAvailableLimit(), limit.getReservedLimit(),"Лимит зарезервирован!");
    }

    @Transactional
    public LimitOperationResponseDto applyReservedLimit(Long userId, LimitRequestDto request) {
        log.info("Выполнение подтверждения лимита для пользователя : {}", userId);
        Limit limit = findOrCreateLimit(userId);

        if (limit.getReservedLimit().compareTo(request.amount()) < 0) {
            throw new InsufficientLimitException("Недостаточно зарезервированных средств для подтверждения");
        }

        limit.setReservedLimit(limit.getReservedLimit().subtract(request.amount()));
        limit.setLastUpdate(LocalDateTime.now());
        limitRepository.save(limit);

        return new LimitOperationResponseDto(limit.getUserId(),limit.getAvailableLimit(), limit.getReservedLimit(),"Лимит подтвержден!");
    }
    @Transactional
    public LimitOperationResponseDto rollbackLimit(Long userId, LimitRequestDto request) {
        log.info("Выполнение отката лимита для пользователя : {}", userId);
        Limit limit = findOrCreateLimit(userId);

        if (limit.getReservedLimit().compareTo(request.amount()) < 0) {
            throw new InsufficientLimitException("Недостаточно зарезервированных средств для отмены");
        }

        limit.setReservedLimit(limit.getReservedLimit().subtract(request.amount()));
        limit.setAvailableLimit(limit.getAvailableLimit().add(request.amount()));
        limit.setLastUpdate(LocalDateTime.now());
        limitRepository.save(limit);

        return new LimitOperationResponseDto(limit.getUserId(),limit.getAvailableLimit(), limit.getReservedLimit(),"Лимит отменен!");
    }
}
