package com.uj.yuri.budgetflow.db_managment.db_helper_objects;

import com.uj.yuri.budgetflow.m_activity.view_managment_listview.Entries_list_;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Category implements Entries_list_{
    protected String id;
    protected String categoryName;
    protected String colorOfRound;

    public Category(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
        this.colorOfRound = "y";

    }

    public Category(String id, String categoryName, String colorOfRound) {
        this.id = id;
        this.categoryName = categoryName;
        this.colorOfRound = colorOfRound;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return categoryName;
    }

    @Override
    public String getAmount() {
        return null;
    }

    @Override
    public String getStartTime() {
        return null;
    }

    @Override
    public String getEndTime() {
        return null;
    }

    @Override
    public int getFrequency() {
        return 0;
    }

    @Override
    public boolean whatAmI() {
        return false;
    }

    public String getColor(){
        return colorOfRound;
    }
}
