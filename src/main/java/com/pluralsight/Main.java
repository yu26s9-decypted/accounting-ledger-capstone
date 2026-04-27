package com.pluralsight;

import com.pluralsight.data.Transaction;
import com.pluralsight.data.TransactionFileManager;
import com.pluralsight.ui.Console;
import com.pluralsight.ui.PrintFormatUtility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
                Enter your command: ›""";

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

        } while (!userInput.equalsIgnoreCase("X") );

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
                
                Enter a command: ›""";
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
                    String r = reportMenuOption();

                    if (r.equals("HOME")) {
                        return;
                    }
                    break;
                case "H":
                    return;
            }


        } while (true);
    }




    public static void listAllTransaction(ArrayList<Transaction> transaction) {

        while (true) {
            double spend = 0;
            transaction.sort(Comparator.comparing(Transaction::getDate).reversed());
            LocalDate today = LocalDate.now();
            int transactionResult = Integer.parseInt(String.valueOf(transaction.size()));
            PrintFormatUtility.printTransactionHeader();

            System.out.printf("You have %d " + ((transactionResult == 1 ? "transaction" : "transactions") + "\n"), transactionResult);

            for (Transaction t : transaction) {
                PrintFormatUtility.formattedTransaction(t);
                spend += t.getAmount();
            }
            System.out.printf("Total Spend: $%,.2f", spend);


            boolean exit = Console.promptForExit("Press to exit", "x");
            if (exit)
                break;

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
        boolean exit = Console.promptForExit("Press to exit", "x");
        if (exit) {
            return;
        };
    }

    // Display payment transaction

    public static void displayPaymentTransaction() {
        for (Transaction t : transactionLedger) {
            if (t.getAmount() < 0) {
                PrintFormatUtility.formattedTransaction(t);
            }
        }
        boolean exit = Console.promptForExit("Press to exit", "x");
        if (exit) {
            return;
        };
    }

    public static String reportMenuOption() {
        String ledgerReportMenuOption = """
                WELCOME TO STASH BUSINESS ACCOUNTING.
                
                
                STASH // BUSINESS REPORTING SUITE®
                
                Select an option.
                
                1. View month to date report
                2. View previous month report
                3. View Year-To-Date report
                4. View Previous Year report
                5. View Searh by Vendor
                6. Custom Search
                0. Back - Return to the ledger page.
                H. Return to home.
                
                Enter your command: ›""";
        String userInput;
        while(true) {
            System.out.printf("%n %n %n");
            userInput = Console.askForString(ledgerReportMenuOption).toUpperCase();

            switch (userInput) {
                case "1":
                    viewMonthToDateReport();
                    break;
                case "2":
                    viewPrevMonth();
                    break;
                case "3":
                    viewYearToDate();
                    break;
                case "4":
                    viewPrevYear();
                    break;
                case "5":
                    filterByVendor();
                    break;
                case "6":
                    filterByCustomSearch();
                    break;
                case "0":
                    return "LEDGER";
                case "H":
                    return "HOME";
                default:
                    System.out.printf("Invalid option");

            }

        }
    }


    public static void viewMonthToDateReport() {

        while (true){
            LocalDate today = LocalDate.now();
            LocalDate startOfMoth = today.withDayOfMonth(1);

            System.out.printf("Month to Date Overview: %s Month: %s %n", today, startOfMoth);

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

    // bug: not printing view previous month.

    public static void viewPrevMonth() {
        LocalDate today = LocalDate.now();
        LocalDate previousMonthStartDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endMonth = previousMonthStartDate.withDayOfMonth(previousMonthStartDate.lengthOfMonth());

        System.out.printf("Previous Month Overview:");
        PrintFormatUtility.printTransactionHeader();
        for (Transaction t : transactionLedger) {
            LocalDate transactionDate = t.getDate();



            if (transactionDate.isBefore(endMonth) && transactionDate.isAfter(previousMonthStartDate) || transactionDate.isEqual(previousMonthStartDate)) {
                PrintFormatUtility.formattedTransaction(t);
            }


        }
        boolean exit = Console.promptForExit("Press to exit", "x");
        if (exit) {
            return;
        }
    }

    public static void viewYearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate previousYear = LocalDate.now().withDayOfYear(1);
        PrintFormatUtility.printTransactionHeader();

        for(Transaction t : transactionLedger) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.isBefore(today) && transactionDate.isAfter(previousYear)) {
                PrintFormatUtility.formattedTransaction(t);
            }

        }
        boolean exit = Console.promptForExit("Press to exit", "x");
        if (exit);


    }

    public static void viewPrevYear(){
        LocalDate startOfLastYear = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate endOfLastYear = LocalDate.now().minusYears(1).withMonth(12).withDayOfMonth(31);


        for(Transaction t: transactionLedger){
            LocalDate transactionDate = t.getDate();

            if(transactionDate.isBefore(endOfLastYear) && transactionDate.isAfter(startOfLastYear)){
                PrintFormatUtility.formattedTransaction(t);
            }
        }

        boolean exit = Console.promptForExit("Press to exit", "x");
        if (exit) {
            return;
        };

    }

    public static void filterByVendor(){
        try {

            String userSearchTerm = Console.askForString("Search for a vendor:");
            boolean vendorExist = false;

            for (Transaction t : transactionLedger)
            {
                if(t.getVendor().equalsIgnoreCase(userSearchTerm)){
                    PrintFormatUtility.formattedTransaction(t);
                    vendorExist = true;
                }

            }

            if(!vendorExist)
            {
                System.out.printf("Vendor could not be found. \n");
            }
        } catch (Exception e){
            System.out.printf(e.getMessage());
        }
        boolean exit = Console.promptForExit("Press to exit", "x");
        if (exit) {
            return;
        };
    }

    public static void filterByCustomSearch(){
        String startDate = Console.askForString("Start date (format: mm/dd/yyyy) : ");
        String endDate = Console.askForString("End Date (format: mm/dd/yyyy): ");
        String askForDescription = Console.askForString("Description:");
        String askForVendor = Console.askForString("Vendor: ");
        double askForAmount = Console.askForDouble("Amount: ");






        System.out.printf(formatDate(startDate));




        for(Transaction t : transactionLedger){

        }


    }

    public static String formatDate(String date){
        String[] split = date.split("/");
        String formattedStartDate = split[2] + "-" +
                split[0] + "-" +
                split[1];
        return formattedStartDate;
    }








}