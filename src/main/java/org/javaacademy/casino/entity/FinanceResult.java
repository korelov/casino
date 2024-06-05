package org.javaacademy.casino.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinanceResult {
    private BigDecimal income;
    private BigDecimal outcome;
}
