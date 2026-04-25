package com.pluralsight;

import com.pluralsight.data.Transaction;
import com.pluralsight.data.TransactionFileManager;
import com.pluralsight.ui.Console;
import com.pluralsight.ui.PrintFormatUtility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    private final static TransactionFileManager transactionFileManager = new TransactionFileManager("src/main/java/com/pluralsight/data/transaction.csv");
    private static ArrayList<Transaction> transactionLedger = transactionFileManager.loadAllTransaction();

    public static void main(String[] arg) {

        String accountingLedgerHomeMenuMsg = """
                
                WELCOME TO STASH BUSINESS ACCOUNTING.
                Stash Accounting, a division of Stash Banking, USA.
                The bank for innovative companies.
                
                What would you like to accomplish today?
                
                --------- Select an option. ---------
                D.) Add Deposit
                P.) Make Payment
                L.) Ledger 
                X.) Exit the application
                -------------------------------------
                Enter your command:
                """;

        String userInput;
        do {
            System.out.printf("%n %n %n");
            userInput = Console.askForString(accountingLedgerHomeMenuMsg).toUpperCase();

            switch (userInput) {
                case "D":
                    addTransaction();
                    break;
                case "L":
                    ledgerOptionMenuMsg();
                    break;
                case "P":
                    makePayment();
                    break;
                case "X":
                    return;
            }

        } while (userInput.equalsIgnoreCase("X") );

    }

// Ledger Options

    public static void ledgerOptionMenuMsg() {

        String ledgerOptionMenu = """
                STASH // LEDGER MENU
              
                Select an action.
                
                A. Display all ledger transaction
                D. Display all deposits
                P. Display all payments
                R. View reports
                H. Exit to home
                """;

        String userInput;
        do {
            System.out.printf("%n %n %n");
            userInput = Console.askForString(ledgerOptionMenu).toUpperCase();

            switch (userInput) {
                case "A":
                    listAllTransaction(transactionLedger);
                    break;
                case "D":
                    displayDepositTransaction();
                    break;
                case "P":
                    displayPaymentTransaction();
                    break;
                case "R":
                    reportMenuOption();
                    break;
                case "H":
                    return;
            }


        } while (userInput.equalsIgnoreCase("H"));
    }

    //    List transaction test
    public static void listAllTransaction(ArrayList<Transaction> transaction) {

        while (true) {
            int transactionResult = Integer.parseInt(String.valueOf(transaction.size()));
            PrintFormatUtility.printTransactionHeader();
            System.out.printf("You have %d " + ((transactionResult == 1 ? "transaction" : "transactions") + "\n"), transactionResult);

            for (Transaction t : transaction) {
                PrintFormatUtility.formattedTransaction(t);
            }


            String userExitCommand = Console.askForString("Press x to exit once you are done viewing").toUpperCase();
            if (userExitCommand.equalsIgnoreCase("X")) {
                return;
            }
        }

    }


    public static void addTransaction() {

        boolean isAddingTransaction = true;

        while (isAddingTransaction) {
            String depositAmount = Console.askForString("How much are you depositing?: ");
            if (depositAmount.equalsIgnoreCase("x")) {
                System.out.printf("Exiting deposit.");
                return;
            }

            Double convertDepToDouble = Double.parseDouble(depositAmount);

            String depositDescription = Console.askForString("Description: ");
            String depositVendor = Console.askForString("Vendor/Source of Deposit: ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().withNano(0);
            String fTime = formatter.format(time);

            Transaction addNewTransaction = new Transaction(date, time, depositDescription, depositVendor, convertDepToDouble);


            System.out.printf("PLEASE CONFIRM YOUR DEPOSIT.");
            PrintFormatUtility.formattedTransaction(addNewTransaction);
            String confirmTransaction = Console.askForString("Confirm this transaction? (y/n): ");

            if (confirmTransaction.equalsIgnoreCase("Y")) {
                try {
                    transactionFileManager.writeNewTransaction(addNewTransaction);
                    System.out.printf("Your transaction has been saved to the csv.");
                } catch (Exception e) {
                    System.out.printf(e.getMessage());
                }
            } else {
                System.out.printf("This transaction was discarded.");
            }

            String promptForAnotherDeposit = Console.askForString("would you like to add another transaction? (y/n): ");
            if (promptForAnotherDeposit.equalsIgnoreCase("n")) {
                isAddingTransaction = false;

            }


        }

    }

    public static void makePayment() {
        boolean isMakingPayment = true;
        String paymentAmount = Console.askForString("How much are you paying?: ");
        if (paymentAmount.equalsIgnoreCase("x")) {
            System.out.printf("Exiting payment option.");
            return;
        }

        Double convertDepToDouble = -Double.parseDouble(paymentAmount);
        String depositDescription = Console.askForString("Description: ");
        String depositVendor = Console.askForString("Vendor/Source of Deposit: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);
        String fTime = formatter.format(time);

        Transaction addNewTransaction = new Transaction(date, time, depositDescription, depositVendor, convertDepToDouble);


        System.out.printf("PLEASE CONFIRM YOUR PAYMENT.");
        PrintFormatUtility.formattedTransaction(addNewTransaction);
        String confirmTransaction = Console.askForString("Confirm this payment? (y/n): ");

        if (confirmTransaction.equalsIgnoreCase("Y")) {
            try {
                transactionFileManager.writeNewTransaction(addNewTransaction);
                System.out.printf("Your payment transaction has been saved to the csv.");
            } catch (Exception e) {
                System.out.printf(e.getMessage());
            }
        } else {
            System.out.printf("This payment was discarded.");
        }
    }

//    Display deposits only

    public static void displayDepositTransaction() {
        for (Transaction t : transactionLedger) {
            if (t.getAmount() > 0) {
                PrintFormatUtility.formattedTransaction(t);
            }
        }
    }

    // Display payment transaction

    public static void displayPaymentTransaction() {
        for (Transaction t : transactionLedger) {
            if (t.getAmount() < 0) {
                PrintFormatUtility.formattedTransaction(t);
            }
        }
    }

    public static void reportMenuOption() {
        String ledgerReportMenuOption = """
                WELCOME TO STASH BUSINESS ACCOUNTING.
                
                
                STASH // BUSINESS REPORTING SUITE®
                
                Select an option.
                
                1. View month to date report
                2. View previous month report
                3. View Year-To-Date report
                4. View Previous Year report
                5. View Searh by Vendor
                0. Back - Return to the ledger page.
                H. Return to home.
                """;

        String userInput;
        do {
            System.out.printf("%n %n %n");
            userInput = Console.askForString(ledgerReportMenuOption).toUpperCase();

            switch (userInput) {
                case "1":
                    viewMonthToDateReport();
                    break;
                case "2":
                    //todo
                    break;
                case "3":
                    //todo
                    break;
                case "4":
                    // todo
                    break;
                case "5":
                    //todo
                    break;
                case "0":
                    break;
                case "H":
                    //todo
                    return;

            }


        } while (userInput != "H");
    }


    public static void viewMonthToDateReport() {

        while (true){
            LocalDate today = LocalDate.now();
            LocalDate startOfMoth = today.withDayOfMonth(1);

            System.out.printf("Today: %s Month: %s %n", today, startOfMoth);

            for (Transaction t : transactionLedger){
                LocalDate transactionDate = t.getDate();

                if(transactionDate.isBefore(today) && transactionDate.isAfter(startOfMoth)){
                    PrintFormatUtility.formattedTransaction(t);
                }
            }

            boolean exit =  Console.promptForExit("Press to exit", "x");
            if(exit){
                return;
            }
        }
    }




}
