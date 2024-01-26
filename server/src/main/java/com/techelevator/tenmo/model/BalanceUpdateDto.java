package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class BalanceUpdateDto {

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private BigDecimal amount;
}
