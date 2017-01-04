package com.uj.yuri.budgetflow.MActivity.ListViewAdapter;


import com.uj.yuri.budgetflow.DataManagment.Money;

public abstract class EntryElement {

    protected String id;

    public EntryElement(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    protected Money amount;




    public abstract  String getAmountString();
    public abstract String getName() ;

    public abstract String getStartTime() ;
    public abstract String getEndTime() ;
    public abstract int getFrequency();
    public abstract boolean whatAmI();

}
