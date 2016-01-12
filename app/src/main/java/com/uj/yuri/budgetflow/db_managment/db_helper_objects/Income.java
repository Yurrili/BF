package com.uj.yuri.budgetflow.db_managment.db_helper_objects;

import com.uj.yuri.budgetflow.db_managment.db_main_classes.Income_;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Income implements Income_ {
    protected String id;
    protected String nameOfIncome;
    protected float amount;
    protected String startTime;
    protected String endTime;
    protected boolean active;
    protected String description;
    protected int frequency;
    protected int duration;

    public Income(String id, String nameOfIncome, String amount, String startTime, String endTime, boolean active, String desciption, int frequency, int duration) {
        this.id = id;
        this.nameOfIncome = nameOfIncome;
        this.amount = Float.parseFloat(amount);
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

    public String getNameOfIncome() {
        return nameOfIncome;
    }

    public String getAmount() {
        return amount +"";
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

    public int getDuration() {
        return duration;
    }
}
