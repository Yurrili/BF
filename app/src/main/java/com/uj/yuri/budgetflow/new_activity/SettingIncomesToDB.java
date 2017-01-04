package com.uj.yuri.budgetflow.new_activity;

import android.widget.CheckBox;

import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yuri on 2016-01-18.
 */
public class SettingIncomesToDB {

    DateBaseHelper helper;

    public SettingIncomesToDB(DateBaseHelper db){
        this.helper = db;
    }


}
