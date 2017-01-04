package com.uj.yuri.budgetflow.MActivity.ListViewAdapter;

/**
 * Created by Yuri on 2016-01-13.
 */
public class EmptyElement extends EntryElement {

    public EmptyElement() {
        super("");
    }

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
        return "";
    }

    @Override
    public boolean whatAmI() {
        return false;
    }

}
