package com.pluralsight.ui;

import com.pluralsight.data.Transaction;

public class PrintFormatUtility {
    public static void formattedTransaction(Transaction t){

        System.out.printf("%-12s %-8s %-20s %-10s $%10.2f \n",
                t.getDate(),
                t.getTime(),
                t.getDescription(),
                t.getVendor(),
                t.getAmount()

                );
    }

    public static void printTransactionHeader(){
        System.out.printf("%-12s", "WELCOME TO STASH BUSINESS ACCOUNTING. \n");
        System.out.printf("%-12s", "Stash Accounting, a division of Stash Banking, USA. \n");
        System.out.printf("%-12s", "The bank for innovative companies. \n");
        System.out.printf("\n");

        System.out.printf(
                "%-12s %-8s %-20s %-15s %-20s%n",
                "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT"
        );
    System.out.println("----------------------------------------------------------------------------");
    }


}
