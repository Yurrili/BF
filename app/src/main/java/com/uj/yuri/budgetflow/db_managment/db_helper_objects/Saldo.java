package com.uj.yuri.budgetflow.db_managment.db_helper_objects;


import com.uj.yuri.budgetflow.db_managment.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Yuri on 23.12.2016.
 */

public class Saldo {

    private String id;
    private String data;
    private Money amount;

    public Saldo(String data, String amount) {
        this.data = data;
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
    }

    public Saldo(String data, Double amount) {
        this.data = data;
        this.amount = new Money(amount, Currency.getInstance(Locale.getDefault()));
    }

    public Saldo(String data, BigDecimal amount) {
        this.data = data;
        this.amount = new Money(amount, Currency.getInstance(Locale.getDefault()));
    }

    public Saldo(String data, Money amount) {
        this.data = data;
        this.amount = amount;
    }


    public Saldo(String id, String data, String amount) {
        this.id = id;
        this.data = data;
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
    }

    public Saldo(String id, String data, Double amount) {
        this.id = id;
        this.data = data;
        this.amount = new Money(amount, Currency.getInstance(Locale.getDefault()));
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Money getAmount() {
        return amount;
    }

    public String getStringAmount() {
        return amount.toFormattedString();
    }
}
