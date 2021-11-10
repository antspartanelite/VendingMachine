package com.company.dto;

import java.math.BigDecimal;

public enum Coin {
    TWOPOUND(new BigDecimal(2)),
    ONEPOUND(new BigDecimal(1)),
    FIFTYPENCE(new BigDecimal("0.5")),
    TWENTYPENCE(new BigDecimal("0.2")),
    TENPENCE(new BigDecimal("0.1")),
    FIVEPENCE(new BigDecimal("0.05")),
    TWOPENCE(new BigDecimal("0.02")),
    ONEPENCE(new BigDecimal("0.01"));

    private BigDecimal value;

    Coin(BigDecimal value){
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
