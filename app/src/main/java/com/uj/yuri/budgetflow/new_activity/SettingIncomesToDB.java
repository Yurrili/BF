package com.uj.yuri.budgetflow.new_activity;

import android.widget.CheckBox;

import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
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

    DateBaseHelper_ helper;

    public SettingIncomesToDB(DateBaseHelper_ db){
        this.helper = db;
    }

    public void DailySet(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity,
                         Income createPreparedOneNotInf,Income createPreparedOneInf){
        dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));
        Days days = Days.daysBetween(new DateTime(past), new DateTime(today));

        if(infinity.isChecked()){
            Income prepared_one = createPreparedOneInf;
            helper.insertIncome(prepared_one);

            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))) {

                for (int j = 0; j < days.getDays(); j++) {
                    dd.add(Calendar.DATE, 1);
                    insertInfinityIncomes(prepared_one, dd, "4");
                }
            }

        } else {
            Income prepared_one = createPreparedOneNotInf;
            helper.insertIncome(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))) {

                for( int j =0; j < days.getDays(); j++) {
                    dd.add(Calendar.DATE, 1);
                    insertNotInfinityIncomes(prepared_one, dd, "4");
                }
            }
        }
    }

    public void MontlySet(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity
                          ,Income createPreparedOneNotInf, Income createPreparedOneInf){
        Months months = Months.monthsBetween(new DateTime(past), new DateTime(today));
        int d = months.getMonths();
        dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1]) - 1, Integer.parseInt(date_split[0]));

        if(infinity.isChecked()){
            Income prepared_one = createPreparedOneInf;
            helper.insertIncome(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))) {

                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    insertInfinityIncomes(prepared_one, dd, "5");
                }
            }

        } else {

            Income prepared_one = createPreparedOneNotInf;
            helper.insertIncome(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))) {

                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    insertNotInfinityIncomes(prepared_one, dd, "5");
                }
            }
        }
    }

    public void DailySetUp(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity,
                         Income createPreparedOneNotInf,Income createPreparedOneInf){
        dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));
        Days days = Days.daysBetween(new DateTime(past), new DateTime(today));

        if(infinity.isChecked()){
            Income prepared_one = createPreparedOneInf;
            helper.updateIncome(prepared_one);

            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today)) && days.getDays() > 0) {

                for (int j = 0; j < days.getDays(); j++) {
                    dd.add(Calendar.DATE, 1);
                    insertInfinityIncomes(prepared_one, dd, "4");
                }
            }

        } else {
            Income prepared_one = createPreparedOneNotInf;
            helper.updateIncome(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))&& days.getDays() > 0) {

                for( int j =0; j < days.getDays(); j++) {
                    dd.add(Calendar.DATE, 1);
                    insertNotInfinityIncomes(prepared_one, dd, "4");
                }
            }
        }
    }


    public void MontlySetUp(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity
            ,Income createPreparedOneNotInf, Income createPreparedOneInf){
        Months months = Months.monthsBetween(new DateTime(past), new DateTime(today));
        int d = months.getMonths();
        dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1]) - 1, Integer.parseInt(date_split[0]));

        if(infinity.isChecked()){
            Income prepared_one = createPreparedOneInf;
            helper.updateIncome(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today)) && d > 0) {

                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    insertInfinityIncomes(prepared_one, dd, "5");
                }
            }

        } else {
            Income prepared_one = createPreparedOneNotInf;
            helper.updateIncome(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))&& d > 0) {

                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    insertNotInfinityIncomes(prepared_one, dd, "5");
                }
            }
        }
    }


    private void insertInfinityIncomes(Income prepared_one, Calendar dd, String cat){
        helper.insertIncome(new Income(prepared_one.getId(),
                prepared_one.getName(),
                prepared_one.getAmount(),
                Utility.formatData.format(dd.getTime()),
                Utility.formatData.format(dd.getTime()),
                true,
                prepared_one.getDescription(),
                cat,
                prepared_one.getDuration()));
    }

    private void insertNotInfinityIncomes(Income prepared_one, Calendar dd, String cat){
        helper.insertIncome(new Income(prepared_one.getId(),
                prepared_one.getName(),
                prepared_one.getAmount(),
                Utility.formatData.format(dd.getTime()),
                prepared_one.getEndTime(),
                true,
                cat,
                prepared_one.getDescription(),
                prepared_one.getDuration()));

    }
}
