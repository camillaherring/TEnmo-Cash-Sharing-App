package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<JdbcTransferDao> getAllTransfersByUserId(Integer userId) {
        List<JdbcTransferDao> transfers = new ArrayList<>();
        String sql = "SELECT transaction_id, sending, receiving, amount FROM transactions WHERE sending = ? OR receiving = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            JdbcTransferDao transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    public List<JdbcTransferDao> getAllTransfersByUsername(String username) {
        List<JdbcTransferDao> transfers = new ArrayList<>();
        String sql = "SELECT transaction_id, sending, receiving, amount FROM transactions JOIN tenmo_user ON transactions.user_id = tenmo_user.transaction_id WHERE tenmo_user.username = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        while(results.next()) {
            JdbcTransferDao transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }
    @Override
    public TransferDTO updateBalance(String receivingUsername, String sendingUsername, BigDecimal amount) {

        //check balance of sender
        //check for matching account_id
        if(amount.compareTo(getBalanceByUsername(sendingUsername)) == 1) {
            System.out.println("Please enter a value less than your current balance.");
        } else if(sendingUsername.equalsIgnoreCase(receivingUsername)) {
            System.out.println("Please select a different user to send funds to. (Cannot send funds to yourself)");
        } else if(amount.compareTo(BigDecimal.valueOf(0.0)) == 0) {
            System.out.println("Please enter a value greater than 0.");
        }
        //sql for updating the receiver's balance
        System.out.println(receivingUsername);
        System.out.println(amount);

        String sql = "UPDATE account SET balance = balance + ? WHERE account.account_id = (SELECT account.account_id " +
                "FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE username = ?);";

        jdbcTemplate.update(sql, amount, receivingUsername);

        //sql for updating sender's balance
        System.out.println(sendingUsername);
        System.out.println(amount);

        String sql2 = "UPDATE account SET balance = balance - ? WHERE account.account_id = (SELECT account.account_id " +
                "FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?);";

        jdbcTemplate.update(sql2, amount, sendingUsername);
        //creates new transfer in the transfer table
        String sql3 =
                "INSERT INTO transactions (receiving, sending, amount) VALUES (" +
                        "(SELECT account.account_id FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?), " +
                        "(SELECT account.account_id FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?), " +
                        "?);";

        jdbcTemplate.update(sql3, receivingUsername, sendingUsername, amount);
        Integer newTransferId;
        try {
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, receivingUsername, sendingUsername, amount);
        } catch (DataAccessException e) {

        } TransferDTO newTransfer = getTransferByTransferId(newTransferId); //why does it say 'may not be instantiated"???

        System.out.println("Transfer approved.");

        return newTransfer;
    }

   @Override
   @RequestMapping(path = "/", method = RequestMethod.GET)
   public TransferDTO getTransferByTransferId(int transferId) {
        String sql = "SELECT receiving, sending, amount FROM transactions WHERE transaction_id = ?";
        TransferDTO transfer = null;
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                transfer = mapRowToTransferDTO(results);
            }
        } catch (DataAccessException e) {

        }
       return transfer;
    }


    @Override
    public Transfer createTransfer(Transfer newTransfer){
        String sql = "INSERT INTO transfer VALUES (?, ? ,?) RETURNING transfer_id";
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class,
                newTransfer.getSendingId(), newTransfer.getReceivingId(), newTransfer.getAmount());
        newTransfer.setTransferId(transferId);
        return newTransfer;
    }
    public BigDecimal getBalanceByUsername(String username) {
        String sql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?;";
        BigDecimal balance = BigDecimal.valueOf(0);
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                balance = results.getBigDecimal("balance");

            }
        } catch (DataAccessException e) {

        }
        return balance;
    }


    private TransferDTO mapRowToTransferDTO(SqlRowSet rowSet) {
        TransferDTO transferDTO = new TransferDTO();
        return transferDTO;
    }

    private JdbcTransferDao mapRowToTransfer(SqlRowSet rowSet) {
        JdbcTransferDao transfer = new JdbcTransferDao((JdbcTemplate) rowSet);
        return transfer;
    }
}
