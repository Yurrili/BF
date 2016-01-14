package com.uj.yuri.budgetflow.db_managment.db_main_classes;

import com.uj.yuri.budgetflow.view_managment.Entries_list_;

/**
 * Created by Yuri on 2016-01-03.
 */
public interface Income_ extends Entries_list_ {
    String getId() ;
    String getName() ;
    String getAmount() ;
    String getStartTime() ;
    String getEndTime() ;
    boolean isActive();
    String getDescription();
    int getFrequency();
    int getDuration() ;
}
