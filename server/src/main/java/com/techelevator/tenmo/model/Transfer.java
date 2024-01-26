package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private Integer transferId;
    private Integer receivingId;
    private Integer sendingId;
    private BigDecimal amount;

    public Transfer(Integer sendingId, Integer receivingId, BigDecimal amount) {
        this.sendingId = sendingId;
        this.receivingId = receivingId;
        this.amount = amount;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(Integer receivingId) {
        this.receivingId = receivingId;
    }

    public Integer getSendingId() {
        return sendingId;
    }

    public void setSendingId(Integer sendingId) {
        this.sendingId = sendingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    //copied from user
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(transferId, transfer.transferId) && Objects.equals(sendingId, transfer.sendingId) && Objects.equals(receivingId, transfer.receivingId) && Objects.equals(amount, transfer.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferId, sendingId, receivingId, amount);
    }

    @Override
    public String toString() {
        return "{" +
                "transferId : " + transferId + ",\n" +
                "transferAmount : " + amount + ",\n" +
                "from : " + sendingId + ",\n" +
                "to : " + receivingId + "\n" +
                "}";
    }
}
