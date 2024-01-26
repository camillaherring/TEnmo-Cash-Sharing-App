package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.BalanceDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
public class AccountController {

    private AccountDao accountDao;
    private TransferDao transferDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    //I need to be able to see my Account Balance
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path="/balance", method = RequestMethod.GET)
    public BalanceDTO getBalance(Principal principal1) {
        return accountDao.getBalanceAndUsername(principal1.getName());
    }

    //I need to be able to send a transfer of a specific amount of TE Bucks to a registered user
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/transfer", method = RequestMethod.PUT)
    public TransferDTO updateBalance(@RequestBody TransferDTO transferDTO, Principal principal) {
        return transferDao.updateBalance(transferDTO.getUsername(), principal.getName(), transferDTO.getAmount());
    }

    //I need an endpoint that shows the users I can send money to
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path="/sendable_users", method = RequestMethod.GET)
    public List<User> getSendableUsers(@RequestBody Principal principal) {
        List<User> sendableUsers = userDao.findSendableUsers(principal.getName());
        return sendableUsers;
    }

    //I need to be able to see transfers I have sent or received (getAllTransfersByUserId)
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path="/user_transfers", method = RequestMethod.GET)
    public List<JdbcTransferDao> getAllTransfers(@RequestBody Principal principal) {

        List<JdbcTransferDao> transfersByUser = transferDao.getAllTransfersByUsername(principal.getName());
        return transfersByUser;
    }

    //I need to be able to retrieve the details of any transfer based upon the transferId (getTransferByTransferId)
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path="/transfers", method = RequestMethod.GET)
    public TransferDTO showTransferByTransferId(Integer transferId) {
        return transferDao.getTransferByTransferId(transferId);
    }

}
