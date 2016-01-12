package com.uj.yuri.budgetflow;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DataBaseHelper;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.Income_;

/**
 * Created by Yuri on 2016-01-12.
 */
public class DataBaseTest  extends ApplicationTestCase<Application> {
    DateBaseHelper_ db;
    public DataBaseTest() {
        super(Application.class);
        this.db = new DataBaseHelper(getContext());
    }

    @SmallTest
    public void insertToDbTest1() {
        Income_ h1 = new Income("1","BUM","65", "s", "f", true, "tf", 1,1);
        db.insertIncome(h1);
        assert( db.selectAllIncomes().get(0).getId().equals(h1.getId()) );
    }

}
