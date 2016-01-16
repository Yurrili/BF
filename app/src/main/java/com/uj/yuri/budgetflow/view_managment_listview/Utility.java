package com.uj.yuri.budgetflow.view_managment_listview;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Yuri on 2016-01-15.
 */
public class Utility {

    public static double getSaldo(DateBaseHelper_ db){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        db.selectAllIncomes();
        ArrayList<Double> listPlus = db.selectTodaysOutcomes(day + "-" + month + 1 + "-" + year);

     //trzeba pobierac te które beda sie liczyc do dzisiejszego saldo !
        return 0;
    }
}
