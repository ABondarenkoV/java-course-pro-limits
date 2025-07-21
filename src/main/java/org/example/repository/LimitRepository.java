package org.example.repository;

import org.example.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LimitRepository extends JpaRepository<Limit, Long> {

    @Modifying
    @Query("UPDATE Limit SET availableLimit=:availableLimit")
    void resetAllLimits(@Param("availableLimit") BigDecimal availableLimit);
}
