package com.pluralsight;

import com.pluralsight.data.TransactionFileManager;
import com.pluralsight.ui.Console;

public class Main {
    private final static TransactionFileManager transactionFileManager = new TransactionFileManager("src/main/java/com/pluralsight/data/transaction.csv");
    public static void main(String[] arg){

        String accountingLedgerHomeMenuMsg = """
                
                D.) Add Deposit - prompt user for the deposit information and save it
                to the csv file
                P) Make Payment (Debit) - prompt user for the debit information
                and save it to the csv file
                L) Ledger - display the ledger screen
                X) Exit - exit the application
                
                Enter your command:
                
                """;

        String userInput;
        do {
            userInput = Console.askForString(accountingLedgerHomeMenuMsg).toLowerCase();

            switch (userInput){
                case "L":
                    break;
                case "X":
                    return;
            }

        } while (userInput != "X");

    }
}
