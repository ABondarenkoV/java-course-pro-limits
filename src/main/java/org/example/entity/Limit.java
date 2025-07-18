package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_limits")
public class Limit {

    @Id
    @Column(name = "user_id")
    private Long userId;
    //доступный лимит
    @Column(name = "available_Limit")
    private BigDecimal availableLimit;
    //зарезервированный лимит
    @Column(name = "reserved_Limit")
    private BigDecimal reservedLimit ;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
}
