package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BalanceDTO;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal returnBalanceByUserId (Integer userId);
    Integer getUserIdFromAccountId (Integer accountId);
    String getUsernameFromAccountid (Integer accountId);

    void addToBalanceWithUserId (Integer userId, BigDecimal transferAmount);

    void withdrawFromBalanceWithUserId (Integer userId, BigDecimal transferAmount);

    Integer getAccountIdByUserId (Integer userId);


    public BalanceDTO getBalanceAndUsername(String username);


}
