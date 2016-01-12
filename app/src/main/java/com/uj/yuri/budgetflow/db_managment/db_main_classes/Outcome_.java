package com.uj.yuri.budgetflow.db_managment.db_main_classes;

/**
 * Created by Yuri on 2016-01-03.
 */

public interface Outcome_ {

     String getId();

     String getNameOfOutcome() ;

     String getAmount() ;

     String getStartTime() ;

     String getEndTime() ;

     boolean isActive() ;

     String getCategoryId() ;

     int getFrequency() ;

}
