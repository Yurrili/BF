package com.uj.yuri.budgetflow.db_managment.db_helper_objects;


import java.text.DecimalFormat;

/**
 * Created by Yuri on 23.12.2016.
 */

public class Saldo {

    private String id;
    private String data;
    private Double amount;

    public Saldo(String data, String amount) {
        this.data = data;
        this.amount = Double.parseDouble(amount);
    }

    public Saldo(String data, Double amount) {
        this.data = data;
        this.amount = amount;
    }

    public Saldo(String id, String data, String amount) {
        this.id = id;
        this.data = data;
        this.amount =Double.parseDouble(amount);
    }

    public Saldo(String id, String data, Double amount) {
        this.id = id;
        this.data = data;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getStringAmount() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(amount);
    }

    public Double getAmount() {
        return amount;
    }
}
