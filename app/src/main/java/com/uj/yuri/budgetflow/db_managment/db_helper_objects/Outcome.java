package com.uj.yuri.budgetflow.db_managment.db_helper_objects;

import com.uj.yuri.budgetflow.db_managment.db_main_classes.Outcome_;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Outcome implements Outcome_ {
    protected String id;
    protected String nameOfOutcome;
    protected String amount;
    protected String startTime;
    protected String endTime;
    protected boolean active;
    protected String categoryId;
    protected int frequency;


    public Outcome(String id, String nameOfOutcome, String amount, String startTime, String endTime, boolean active, String categoryId, int frequency) {
        this.id = id;
        this.nameOfOutcome = nameOfOutcome;
        this.amount = amount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.categoryId = categoryId;
        this.frequency = frequency;

    }

    public Outcome(String nameOfOutcome, String amount, String startTime, String endTime, boolean active, String categoryId, int frequency) {
        this.id = "";
        this.nameOfOutcome = nameOfOutcome;
        this.amount = amount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.categoryId = categoryId;
        this.frequency = frequency;

    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return nameOfOutcome;
    }

    public String getAmount() {
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


}
