package com.pluralsight.data;

import java.sql.Time;
import java.util.Date;


public class Transaction {
    Date date;
    Time time;
    String description;
    String vendor;
    Double amount;

    public Transaction(Date date, Time time, String description, String vendor, Double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
        return  "Date" + date +
                "Time:" + time +
                "Description:" + description +
                "Vendor:" + vendor +
                "Amount:" + amount;
    }




}
