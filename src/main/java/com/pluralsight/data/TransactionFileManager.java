package com.pluralsight.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class TransactionFileManager {
    private String transactionFileName;

    public TransactionFileManager(String transactionFileName) {
        this.transactionFileName = transactionFileName;
    }

    public String getTransactionFileName(){
        return  transactionFileName;
    }

    /**
     * Loads all transactions from disk.
     * @return
     */
    public ArrayList<Transaction> loadAllTransaction() {
        ArrayList<Transaction> transaction = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(this.transactionFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String transactionData;
            while ((transactionData = bufferedReader.readLine()) != null){
                try {
                    String[] transactionLine = transactionData.split("\\|");
                    Date transactionDate = Date.valueOf(transactionLine[0]);
                    Time transactionTime = Time.valueOf(transactionLine[1]);
                    String transactionDescription = transactionLine[2];
                    String transactionVendor = transactionLine[3];
                    Double transactionAmount = Double.parseDouble(transactionLine[4]);

                    Transaction t = new Transaction(transactionDate.toLocalDate(), transactionTime.toLocalTime(), transactionDescription, transactionVendor, transactionAmount);
                    transaction.add(t);



                } catch (Exception e){
                    System.out.printf("An error occured" + e.getMessage());
                }
            }




        } catch (IOException e){
            System.out.printf("An error occured." + e.getMessage());
        }
        return transaction;
    }



    /**
     * This writes new transactions to the disk.
     *
     */

    public void writeNewTransaction(Transaction transaction){
        try {
            FileWriter fileWriter = new FileWriter(this.transactionFileName, true);
            String formattedTransactionLine = transaction.getDate() + "|" +
                    transaction.getTime() + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getAmount();

            fileWriter.write(formattedTransactionLine + "\n");
            fileWriter.close();


        } catch (IOException e){
            System.out.printf(e.getMessage());
        }
    }

}
