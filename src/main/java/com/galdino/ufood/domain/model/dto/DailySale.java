package com.galdino.ufood.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class DailySale {

    private LocalDate date;
    private Long totalSales;
    private BigDecimal totalAmount;

}
