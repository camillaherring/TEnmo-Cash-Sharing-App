package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<JdbcTransferDao> getAllTransfersByUserId(Integer userId);

    TransferDTO getTransferByTransferId(int transferId);

    Transfer createTransfer(Transfer newTransfer);
    public List<JdbcTransferDao> getAllTransfersByUsername(String username);

    public BigDecimal getBalanceByUsername(String username);

    public TransferDTO updateBalance(String receivingUsername, String sendingUsername, BigDecimal amount);

}
