package com.pluralsight.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;


public class Transaction {
    LocalDate date;
    LocalTime time;
    String description;
    String vendor;
    Double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, Double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }




    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time.toLocalTime();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return  "Date is..." + date +
                "Time:" + time +
                "Description:" + description +
                "Vendor:" + vendor +
                "Amount:" + amount;
    }





}
