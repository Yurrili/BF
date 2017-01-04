package com.uj.yuri.budgetflow.db_managment.db_helper_objects;

import com.uj.yuri.budgetflow.db_managment.Money;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.Entries_list_;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Income implements Entries_list_ {
    protected String id;
    protected String nameOfIncome;
    protected Money amount;
    protected String startTime;
    protected String endTime;
    protected boolean active;
    protected String description;
    protected int frequency;
    protected int duration;

    public Income(String id, String nameOfIncome, String amount, String startTime, String endTime, boolean active,  String frequency, String desciption, int duration) {
        this.id = id;
        this.nameOfIncome = nameOfIncome;
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.description = desciption;
        this.frequency = Integer.parseInt(frequency);
        this.duration = duration;
    }


    public Income(String nameOfIncome, String amount, String startTime, String endTime, boolean active, int frequency, String desciption, int duration) {
        this.id = "";
        this.nameOfIncome = nameOfIncome;
        this.amount = new Money(Double.parseDouble(amount), Currency.getInstance(Locale.getDefault()));
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.description = desciption;
        this.frequency = frequency;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return nameOfIncome;
    }

    @Override
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
}
