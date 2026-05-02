package com.pluralsight.ui;

import com.pluralsight.model.Transaction;

import java.time.format.DateTimeFormatter;

public class PrintFormatUtility {
    public static void formattedTransaction(Transaction t){

        String sign = ((t.getAmount()) < 0 ? "-" : "");
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.printf("%-12s %-15s %-40s %-25s %10s$%.2f%n",
                t.getDate(),
                t.getTime().format(formatTime),
                t.getDescription(),
                t.getVendor(),
                sign,
                Math.abs(t.getAmount())
                );
    }

    public static void printTransactionIntro(){
        System.out.printf("%-12s", "WELCOME TO STASH BUSINESS ACCOUNTING. \n");
        System.out.printf("%-12s", "Stash Accounting, a division of Stash Banking, USA. \n");
        System.out.printf("%-12s", "The bank for innovative companies. \n");
        System.out.printf("\n");
    }

    public static void printTransactionHeader(){

        System.out.printf(
                "%-12s %-15s %-40s %-25s %15s%n",
                "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT"
        );
    System.out.println("---------------------------------------------------------------------------------------------------------------------------");
    }




}
