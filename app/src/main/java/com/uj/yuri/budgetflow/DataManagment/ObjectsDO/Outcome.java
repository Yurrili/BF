package com.uj.yuri.budgetflow.DataManagment.ObjectsDO;

import com.uj.yuri.budgetflow.DataManagment.Money;
import com.uj.yuri.budgetflow.MActivity.ListViewAdapter.EntryElement;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Outcome extends EntryElement {

    protected String id;
    protected String nameOfOutcome;
    protected Money amount;
    protected String startTime;
    protected String endTime;
    protected boolean active;
    protected String categoryId;
    protected int frequency;



    public Outcome(String id, String nameOfOutcome, String amount, String startTime, String endTime, boolean active, String categoryId, int frequency) {
        super(id);

            this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));


        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.nameOfOutcome = nameOfOutcome;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.categoryId = categoryId;
        this.frequency = frequency;

    }


    public Outcome(long id, String nameOfOutcome, double amount, String startTime, String endTime, boolean active, String categoryId, int frequency) {
        super(String.valueOf(id));

        this.nameOfOutcome = nameOfOutcome;
        this.amount = new Money(amount, Currency.getInstance(Locale.getDefault()));
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.categoryId = categoryId;
        this.frequency = frequency;

    }

    public Outcome(String nameOfOutcome, String amount, String startTime, String endTime, boolean active, String categoryId, int frequency) {
        super("");
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.id = "";
        this.nameOfOutcome = nameOfOutcome;
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.categoryId = categoryId;
        this.frequency = frequency;

    }

    @Override
    public String getName() {
        return nameOfOutcome;
    }

    public String getAmountString() {
        return amount.toFormattedString();
    }
    public Money getAmount() {
        return amount;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return active;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public boolean whatAmI() {
        return true;
    }
}
