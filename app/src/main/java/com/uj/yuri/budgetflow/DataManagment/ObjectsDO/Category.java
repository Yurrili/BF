package com.uj.yuri.budgetflow.DataManagment.ObjectsDO;

import com.uj.yuri.budgetflow.MActivity.ListViewAdapter.EntryElement;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Category extends EntryElement {
    protected String id;
    protected String categoryName;
    protected String colorOfRound;

    public Category(String id, String categoryName) {
        super(id);
        this.categoryName = categoryName;
        this.colorOfRound = "y";

    }

    public Category(String id, String categoryName, String colorOfRound) {
        super(id);
        this.categoryName = categoryName;
        this.colorOfRound = colorOfRound;
    }

    public Category(String categoryName) {
        super("");
        this.categoryName = categoryName;
    }


    public String getName() {
        return categoryName;
    }

    @Override
    public String getAmountString() {
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
