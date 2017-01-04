package com.uj.yuri.budgetflow.DataManagment.ObjectsDO;

import com.uj.yuri.budgetflow.DataManagment.Money;
import com.uj.yuri.budgetflow.MActivity.ListViewAdapter.EntryElement;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Income extends EntryElement {

    protected Money amount;
    protected String nameOfIncome;
    protected String startTime;
    protected String endTime;
    protected boolean active;
    protected String description;
    protected int frequency;
    protected int duration;

    public Income(String id, String nameOfIncome, String amount, String startTime, String endTime, boolean active,  String frequency, String desciption, int duration) {
        super(id);
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.nameOfIncome = nameOfIncome;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.description = desciption;
        this.frequency = Integer.parseInt(frequency);
        this.duration = duration;
    }


    public Income(String nameOfIncome, String amount, String startTime, String endTime, boolean active, int frequency, String desciption, int duration) {
        super("");
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.nameOfIncome = nameOfIncome;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.description = desciption;
        this.frequency = frequency;
        this.duration = duration;
    }

    public Income(String id, String name, String amount, String startTime, String endTime, boolean active, int frequency, String description, int duration) {
        super(id);
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.nameOfIncome = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.description = description;
        this.frequency = frequency;
        this.duration = duration;
    }

    public String getName() {
        return nameOfIncome;
    }

    @Override
    public String getAmountString() {
        return amount.toFormattedString();
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

    public String getDescription() {
        return description;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public boolean whatAmI() {
        return false;
    }


    public int getDuration() {
        return duration;
    }

    public Money getAmount() {
        return amount;
    }
}
