package com.uj.yuri.budgetflow.view_managment_listview;

/**
 * Created by Yuri on 2016-01-13.
 */
public class HeaderFirstL implements Entries_list_{

    String StartTime;
    public HeaderFirstL(){

    }

    public HeaderFirstL(Entries_list_ L){
        StartTime = L.getStartTime();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getAmount() {
        return "";
    }

    @Override
    public String getStartTime() {
        return StartTime;
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
