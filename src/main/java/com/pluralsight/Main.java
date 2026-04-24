package com.pluralsight;

import com.pluralsight.data.Transaction;
import com.pluralsight.data.TransactionFileManager;
import com.pluralsight.ui.Console;
import com.pluralsight.ui.PrintFormatUtility;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    private final static TransactionFileManager transactionFileManager = new TransactionFileManager("src/main/java/com/pluralsight/data/transaction.csv");
    private static ArrayList<Transaction> transactionLedger = transactionFileManager.loadAllTransaction();

    public static void main(String[] arg) {

        String accountingLedgerHomeMenuMsg = """
                
                D.) Add Deposit -
                P.) Make Payment
                L.) Ledger 
                X.) Exit the application
                
                Enter your command:
                """;

        String userInput;
        do {
            userInput = Console.askForString(accountingLedgerHomeMenuMsg).toUpperCase();

            switch (userInput) {
                case "D":
                    addTransaction();
                    break;
                case "L":
                    listAllTransaction(transactionLedger);
                    System.out.printf("Case L");
                    break;
                case "X":
                    return;
            }

        } while (userInput != "X");

    }


//    List transaction test
    public static void listAllTransaction(ArrayList<Transaction> transaction)
    {

        int transactionResult = Integer.parseInt(String.valueOf(transaction.size()));
        PrintFormatUtility.printTransactionHeader();
        System.out.printf("You have %d " + ((transactionResult == 1 ? "transaction" : "transactions") + "\n"), transactionResult);

        for(Transaction t: transaction){
            PrintFormatUtility.formattedTransaction(t);

        }

    }


    public static void addTransaction(){

        Double depositAmount = Console.askForDouble("How much are you depositing: ");
        String depositDescription = Console.askForString("Description: ");
        String depositVendor = Console.askForString("Vendor/Source of Deposit: ");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println("Confirm to add these?" + date + time + depositDescription + depositVendor + depositAmount);
        Transaction addNewTransaction = new Transaction(date, time, depositDescription, depositVendor, depositAmount);
        try {
            transactionFileManager.writeNewTransaction(addNewTransaction);
        } catch (Exception e ){
            System.out.printf(e.getMessage());
        }



    }


}
