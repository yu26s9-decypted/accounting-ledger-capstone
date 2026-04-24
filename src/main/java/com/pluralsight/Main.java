package com.pluralsight;

import com.pluralsight.data.Transaction;
import com.pluralsight.data.TransactionFileManager;
import com.pluralsight.ui.Console;
import com.pluralsight.ui.PrintFormatUtility;

import java.util.ArrayList;

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
    public static void listAllTransaction(ArrayList<Transaction> transaction){
        int userInput;

        PrintFormatUtility.printTransactionHeader();
        for(Transaction t: transaction){
            PrintFormatUtility.formattedTransaction(t);
        }
    }

}
