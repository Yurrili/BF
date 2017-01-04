package com.uj.yuri.budgetflow.db_managment;

/**
 * Created by Yuri on 25.12.2016.
 */

public enum Frequency {
    SINGLE(0),
    DAILY(1),
    MONTHLY(2),
    YEARLY(3);

    private final int value;

    Frequency(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
