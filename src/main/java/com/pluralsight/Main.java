package com.pluralsight;

import com.pluralsight.data.Transaction;
import com.pluralsight.data.TransactionFileManager;
import com.pluralsight.ui.Console;
import com.pluralsight.ui.PrintFormatUtility;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    private final static TransactionFileManager transactionFileManager = new TransactionFileManager("files/transaction.csv");
    private static ArrayList<Transaction> transactionLedger = transactionFileManager.loadAllTransaction();

    public static void main(String[] arg)
    {

        if(arg.length > 0 && arg[0].equalsIgnoreCase("--GUI")){
            //do the gui
            System.out.println("GUI");
        }

        else {

            String accountingLedgerHomeMenuMsg = """
                
                \tWELCOME TO STASH BUSINESS ACCOUNTING.
                \tStash Accounting, a division of Stash Banking, USA.
                \tThe bank for innovative companies.
                
                \tWhat would you like to accomplish today?
                
                \t--------- Select an option. ---------
                \tD.) Add Deposit
                \tP.) Make Payment
                \tL.) Ledger
                \tS.) AI Summarize
                \tX.) Exit the application
                \t-------------------------------------
                Enter your command: ›""";

            String userInput;
            do {
                System.out.printf("%n");
                userInput = Console.askForString(accountingLedgerHomeMenuMsg).toUpperCase();

                switch (userInput) {
                    case "D" -> addTransactionOfDeposit();
                    case "L" -> ledgerOptionMenuMsg();
                    case "P" -> addTransactionOfPayment();
                    case "S" -> useAISummarizer();
                    case "X" -> {
                        System.out.println("Thank you for visiting Stash Accounting!");
                        return;
                    }
                }

            } while (!userInput.equalsIgnoreCase("X") );

        }

    }

// Ledger Options

    public static void ledgerOptionMenuMsg()
    {

        String ledgerOptionMenu = """
                \tSTASH // LEDGER MENU
              
                \tSelect an action.
                
                \tA. Display all ledger transaction
                \tD. Display all deposits
                \tP. Display all payments
                \tR. View reports
                \tH. Exit to home
                
                Enter a command: ›""";
        String userInput;
        do {
            System.out.printf("%n %n %n");
            userInput = Console.askForString(ledgerOptionMenu).toUpperCase();

            switch (userInput)
            {
                case "A" -> listAllTransaction(transactionLedger);
                case "D" -> displayDepositTransaction();
                case "P" -> displayPaymentTransaction();
                case "R" -> {
                    String r = reportMenuOption();

                    if(r.equalsIgnoreCase("HOME")){
                        return;
                    }
                }
                case "H" -> {
                    return;
                }
            }
        } while (true);
    }





    public static void listAllTransaction(ArrayList<Transaction> transaction) {

        while (true) {
            double spend = 0;
            transaction.sort(Comparator.comparing(Transaction::getDate).reversed());
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


    public static void addTransactionOfDeposit() {

        boolean isAddingTransaction = true;
        double convertDepToDouble = 0;


        while (isAddingTransaction) {
            String depositAmount = Console.askForString("How much are you depositing? (press x to exit): ");

            if (depositAmount.equalsIgnoreCase("x")) {
                System.out.printf("Exiting deposit.");
                return;
            }

            if(depositAmount.contains("$")){
                System.out.println("Value cannot include any symbols");
                continue;
            }

            try {

                convertDepToDouble  = Double.parseDouble(depositAmount);

                if (convertDepToDouble < 0){
                    System.out.println("You can't deposit a payment of less than $0 silly!");
                    continue;
                }

                System.out.printf("Deposit was accepted for the amoutn of: $%.2f \nPlease proceed with filling out the other details. \n", convertDepToDouble);

            } catch (Exception e){
                System.out.println("An error occured. " + e.getMessage());
            }


            String depositDescription = Console.askForString("Description: ");
            String depositVendor = Console.askForString("Vendor/Source of Deposit: ");
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().withNano(0);

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

    public static void addTransactionOfPayment() {
        boolean isMakingPayment = true;
        boolean validAmount = false;
        Double convertDepositToDouble = 0.00;
        LocalDate date = null;
        LocalTime time  = null;
        while (!validAmount)
        {
            try {
                String paymentAmount = Console.askForString("How much are you paying? (press x to exit): ");
                convertDepositToDouble = -Double.parseDouble(paymentAmount);
                validAmount = true;
                if (paymentAmount.equalsIgnoreCase("x")) {
                    System.out.printf("Exiting payment option.");
                    return;
                }

            } catch (Exception e){
                System.out.println("Your input was invalid. Please try again");
            }
        }

        boolean validDate = false;

        String transactionTimeOption = """
                \tWhat time do you want to log?
                
                
                \t[1] Current Time
                \t[2] Custom Time
                
                Enter your command› :""";

        while(!validDate){


           int userInput = Console.askForInt(transactionTimeOption,1,2);

           if(userInput == 1){
                date = LocalDate.now();
                time = LocalTime.now().withNano(0);
           } else if  (userInput == 2) {
               DateTimeFormatter format1 = DateTimeFormatter.ofPattern("M/d/yyyy");
               DateTimeFormatter format2 = DateTimeFormatter.ofPattern("MMMM d yyyy");
               DateTimeFormatter format3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");


               boolean parsedDate = false;

               while(!parsedDate)
               {

                   String askDate = Console.askForString("Enter the date of the transaction:");

                   try {
                       date = LocalDate.parse(askDate, format1);
                       parsedDate = true;
                   } catch (Exception error1){
                       try {
                           date = LocalDate.parse(askDate, format2);
                           parsedDate = true;
                       } catch (Exception error2){
                           try {
                               date = LocalDate.parse(askDate, format3);
                               parsedDate = true;
                           } catch (Exception error3){
                               System.out.println("Invalid date time format. Please try again. Error Message: \n" + error3.getMessage());

                           }
                       }
                   }
               }


               boolean parsedTime = false;

               while(!parsedTime) {
                   try {
                       String askTime = Console.askForString("Enter the time of the transaction");
                       DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        time = LocalTime.parse(askTime, timeFormatter);
                        parsedTime = true;
                   } catch (Exception e){
                       System.out.println("Invalid time format. Please try again. (Valid format example: 12:30:25)");
                   }
               }


               validDate = true;

           }
       }


        String depositDescription = Console.askForString("Description: ");
        String depositVendor = Console.askForString("Vendor/Source of Deposit: ");


        Transaction addNewTransaction = new Transaction(date, time, depositDescription, depositVendor, convertDepositToDouble);

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
        } else
        {
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
        if (exit);
    }

    public static String reportMenuOption() {
        String ledgerReportMenuOption = """
                \tWELCOME TO STASH BUSINESS ACCOUNTING.
                
                
                \tSTASH // BUSINESS REPORTING SUITE®
                
                \tSelect an option.
                
                \t1. View month to date report
                \t2. View previous month report
                \t3. View Year-To-Date report
                \t4. View Previous Year report
                \t5. View Searh by Vendor
                \t6. Custom Search
                \t0. Back - Return to the ledger page.
                \tH. Return to home.
                
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

            Console.promptForExit("Press to exit", "x");

        }
    }


    public static void viewPrevMonth() {
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
       Console.promptForExit("Press to exit", "x");

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
        Console.promptForExit("Press to exit", "x");

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

       Console.promptForExit("Press to exit", "x");


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

        Console.promptForExit("Press to exit", "x");
    }

    /**
     * custom search
     */


    public static void filterByCustomSearch() {
        ArrayList<Transaction> matchedTransaction = new ArrayList<>();
        boolean validStartDate = false;
        LocalDate startDate = null;
        LocalDate endDate = null;
        boolean parsedStartDate = false;
        boolean validEndDate = false;
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("M/d/yyyy");



        while(!validStartDate) {

            while (!parsedStartDate){
                String askStartDate = Console.askForString("What is the start date (format: 4/4/2026)");

                if(askStartDate.isBlank()){
                    parsedStartDate = true;
                    validStartDate = true;
                    break;
                }

                try {
                    startDate = LocalDate.parse(askStartDate, format1);
                    parsedStartDate = true;
                    System.out.println(startDate);

                } catch (Exception e){
                    System.out.println("An error occurred." + e.getMessage());

                }

                validStartDate = true;


            }

        }


        boolean parsedEndDate = false;
        while(!validEndDate){


           while(!parsedEndDate){
               String askEndDate = Console.askForString("What is the end date?");

               if (askEndDate.isBlank()){
                   parsedEndDate = true;
                   validEndDate = true;
                   break;
               }
               try {
                   endDate = LocalDate.parse(askEndDate, format1);
                   System.out.println(endDate);
                   parsedEndDate = true;

               } catch (Exception e) {
                   System.out.println("An error occurred. " + e.getMessage());
               }

               validEndDate = true;
           }

        }

        String description = Console.askForString("Description you want to search for");
        String vendor = Console.askForString("Whats the vendor");

        double amount = 0;



        boolean valid = false;

        while(!valid){
            String inputAmount = Console.askForString("Whats the amount?");

            if(inputAmount.isBlank()){
                valid = true;

            } else {
                try {
                    amount = Double.parseDouble(inputAmount);
                    valid = true;
                } catch (NumberFormatException e){
                    System.out.println("This input is invalid please try again");
                }
            }
        }


        for(Transaction t: transactionLedger){
            boolean matchesDateRange =
                    (startDate == null || !t.getDate().isBefore(startDate)) &&
                    (endDate == null || !t.getDate().isAfter(endDate));
            boolean matchesDescription =
                    description.isBlank() || t.getDescription().toLowerCase().contains(description.toLowerCase());
            boolean matchesVendor =
                    vendor.isBlank() || t.getVendor().toLowerCase().contains(vendor.toLowerCase());
            boolean matchesAmount =
                    amount == 0 || t.getAmount() == amount;

            if(matchesAmount && matchesDescription && matchesVendor && matchesDateRange){
                matchedTransaction.add(t);
            }

        }

        String r = (matchedTransaction.size() <= 1 ? "result" : "results");
        int matchedArrLength = matchedTransaction.size();

        System.out.printf("Found %d %s\n",matchedArrLength, r);

        PrintFormatUtility.printTransactionHeader();

        for(Transaction t : matchedTransaction){
            PrintFormatUtility.formattedTransaction(t);

        }

        Console.promptForExit("Press to exit", "x");

    }


    /**
     *  Send a request to the Openrouter API using the gemini model to analyze & summarize user query and generates a AI powered transaction response.
     */
    public static void callOpenAPI(String userText) {
        String openRouterKey = System.getenv("OPENROUTER_API_KEY");
        HttpClient client = HttpClient.newHttpClient();

        String requestBody = String.format("""
                {
                  "model": "google/gemini-2.5-flash",
                  "messages": [
                
                    {
                      "role": "user",
                      "content": "%s"
                    }
                    {
                      "role" : "assistant"
                      "content": "You are a expert financial advisor."
                    }
                  ]
                }
                """, userText);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openRouterKey)
                .POST(HttpRequest.BodyPublishers.ofString((requestBody)))
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }



    public static void useAISummarizer(){
        try {
            String fileContent = Files.readString(Path.of("files/transaction.csv"));
            String userInput = Console.askForString("What would you like to ask the Gemini?");
            System.out.println("Processing your request. Please wait...");
            callOpenAPI(userInput + fileContent);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        Console.promptForExit("Press to exit", "x");

    }

}