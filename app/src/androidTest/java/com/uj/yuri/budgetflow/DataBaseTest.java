package com.uj.yuri.budgetflow;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;

/**
 * Created by Yuri on 2016-01-12.
 */
public class DataBaseTest  extends ApplicationTestCase<Application> {
    DateBaseHelper_ db;
    public DataBaseTest() {
        super(Application.class);
        this.db = new DateBaseHelper(getContext());
    }



}
