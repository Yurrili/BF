package com.uj.yuri.budgetflow.m_activity.view_managment_listview;

/**
 * Created by Yuri on 2016-01-13.
 */
public class EmptyL implements Entries_list_{
    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getAmountString() {
        return "";
    }

    @Override
    public String getStartTime() {
        return "";
    }

    @Override
    public String getEndTime() {
        return "";
    }


    @Override
    public int getFrequency() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean whatAmI() {
        return false;
    }

}
