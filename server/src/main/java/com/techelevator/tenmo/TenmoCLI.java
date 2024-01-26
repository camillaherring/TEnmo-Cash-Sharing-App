package com.techelevator.tenmo;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.TransferDTO;

import java.math.BigDecimal;
import java.util.Scanner;

public class TenmoCLI {

    private static JdbcAccountDao jdbcAccountDao;
    private static JdbcTransferDao jdbcTransferDao;
    private static TransferDao transferDao;

//    public static void main(String[] args)  {
//        System.out.println("***Welcome to TEnmo***");
//        boolean stay = true;
//        Scanner loginScanner = new Scanner(System.in);
//
//        while (stay) {
//            System.out.println("***Main Menu***");
//            System.out.println("Please enter your login info");
//            System.out.println("Username:");
//            String username = loginScanner.next();
//            System.out.println("Password:");
//            String password = loginScanner.next();
//            // how do we check that password is correct?
//
//            Scanner mainMenuScanner = new Scanner(System.in);
//            System.out.println("(1) View Balance");
//            System.out.println("(2) Transfer Money");
//            System.out.println("(3) View Transfer History");
//            System.out.println("(4) Get Transfer by Transfer ID");
//            System.out.println("(5) Exit");
//            String selection = mainMenuScanner.nextLine();
//
//            if (selection.equals("1")) {
//                //go to balance
//                jdbcAccountDao.getBalanceByUsername(username);
//                break;
//            } else if (selection.equals("2")) {
//                //initiate transfer. ask for sending and receiving account and transfer amount
//                System.out.println("How much would you like to transfer?");
//                BigDecimal transferAmount = mainMenuScanner.nextBigDecimal();
//                System.out.println("Who would you like to transfer this to?");
//                String receiver = mainMenuScanner.next();
//                jdbcAccountDao.updateBalance(receiver, username, transferAmount);
//                break;
//            } else if (selection.equals("3")) {
//                // view transfer log
//                System.out.println("Please enter your User ID:");
//                int userId = mainMenuScanner.nextInt();
//                transferDao.getAllTransfersByUserId(userId);
//            } else if (selection.equals("4")) {
//                // view transfer by ID
//                System.out.println("Please enter a Transfer ID:");
//                int transferId = mainMenuScanner.nextInt();
//                jdbcTransferDao.getTransferByTransferId(transferId);
//            } else if (selection.equals("5")) {
//                stay = false;
//            } else {
//                System.out.println("Please select a valid option");
//            }
        }
    }
}
