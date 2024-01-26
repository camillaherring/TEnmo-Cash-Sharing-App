package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.BalanceDTO;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal returnBalanceByUserId (Integer userId) {
        String sql = "SELECT balance FROM account " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE account.user_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
    }

    @Override
    public Integer getUserIdFromAccountId (Integer accountId) {
        String sql = "SELECT user_id FROM account " +
                "WHERE account_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, accountId);
    }

    public String getUsernameFromAccountid(Integer accountId){
        String sql ="SELECT username FROM tenmo_user " +
                "JOIN account ON account.user_id = tenmo_user.user_id " +
                "WHERE account_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, accountId);
    }

//adds to balance
    @Override
    public void addToBalanceWithUserId(Integer userId, BigDecimal transferAmount) {
        String sql = "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE account_id = " +
                "(SELECT account_id FROM account WHERE user_id = ?)";
        jdbcTemplate.update(sql, transferAmount, userId);
    }

//subtracts from balance
    @Override
    public void withdrawFromBalanceWithUserId(Integer userId, BigDecimal transferAmount) {
        String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE account_id = " +
                "(SELECT account_id FROM account WHERE user_id = ?)";
        jdbcTemplate.update(sql, transferAmount, userId);
    }

    public Integer getAccountIdByUserId (Integer userId) {
        String sql = "SELECT account_id FROM account " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE account.user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);

    }


    @Override
    public BalanceDTO getBalanceAndUsername(String username) {
        String sql = "SELECT username, balance\n" +
                "FROM account\n" +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id\n" +
                "WHERE username = ?;";

        BalanceDTO balanceDTO = null;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                String user = results.getString("username");
                BigDecimal balance = results.getBigDecimal("balance");

                balanceDTO = new BalanceDTO();
                balanceDTO.setUsername(user);
                balanceDTO.setBalance(balance);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        System.out.println(balanceDTO.getBalance());
        return balanceDTO;
    }






}
