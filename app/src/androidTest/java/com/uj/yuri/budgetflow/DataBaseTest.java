package com.uj.yuri.budgetflow;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelperImpl;

/**
 * Created by Yuri on 2016-01-12.
 */
public class DataBaseTest  extends ApplicationTestCase<Application> {
    DateBaseHelper db;
    public DataBaseTest() {
        super(Application.class);
        this.db = new DateBaseHelperImpl(getContext());
    }



}
