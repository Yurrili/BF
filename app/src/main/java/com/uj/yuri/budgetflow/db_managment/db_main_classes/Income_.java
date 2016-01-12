package com.uj.yuri.budgetflow.db_managment.db_main_classes;

/**
 * Created by Yuri on 2016-01-03.
 */
public interface Income_ {
    String getId() ;
    String getNameOfIncome() ;
    String getAmount() ;
    String getStartTime() ;
    String getEndTime() ;
    boolean isActive();
    String getDescription();
    int getFrequency();
    int getDuration() ;
}
