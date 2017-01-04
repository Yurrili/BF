package com.uj.yuri.budgetflow.db_managment;

import android.content.Context;
import android.database.Cursor;
import android.widget.CheckBox;

import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.Gateway.IncomeGateway;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import static com.uj.yuri.budgetflow.Utility.getToday;

/**
 * Created by Yuri on 26.12.2016.
 * TABLE MODULE
 */

public class IncomeManagment {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private IncomeGateway incomeGateway ;

    public IncomeManagment(Context ctx) {
        this.incomeGateway = new IncomeGateway(ctx);
    }

    public IncomeGateway getIncomeGateway() {
        return incomeGateway;
    }

    public Money getTodaysIncome() {
        Double sum = 0.0;
        Cursor cc = incomeGateway.selectDate(getToday());

        if (cc != null) {
            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
                if(cc.getString(cc.getColumnIndex(Entries.Incomes.COLUMN_FREQUENCY)).equals("0"))
                    sum += Double.parseDouble(cc.getString(cc.getColumnIndex(Entries.Incomes.COLUMN_AMOUNT)));
            }
            cc.close();
        }

        incomeGateway.closeDB();

        return new Money(sum, Currency.getInstance(Locale.getDefault()));
    }

    public ArrayList<Income> selectAllIncomes(){
        ArrayList<Income> list = new ArrayList<>();
        Cursor c = incomeGateway.findAll();
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(new Income(c.getString(0),
                                    c.getString(1),
                                    c.getString(2),
                                    c.getString(3),
                                    c.getString(4),
                                    true,
                                    c.getString(5),
                                    c.getString(6),
                                    c.getInt(7)));
            }
        }
        incomeGateway.closeDB();
        return list;
    }

    public ArrayList<Income> selectDailyIncomes() {
        ArrayList<Income> list = new ArrayList<>();

        try {
            Date date1 = sdf.parse(getToday());
            Cursor cc = incomeGateway.selectFrequency(Frequency.DAILY);

            if (cc != null) {
                for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
                    Income a = new Income(  cc.getString(0),
                                            cc.getString(1),
                                            cc.getString(2),
                                            cc.getString(3),
                                            cc.getString(4),
                                            true,
                                            cc.getString(5),
                                            cc.getString(6),
                                            cc.getInt(7));

                    if ( a.getStartTime().equals(a.getEndTime())){
                        list.add(a);
                    } else if (!sdf.parse(a.getEndTime()).before(date1)){
                        list.add(a);
                    }
                }
                cc.close();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        incomeGateway.closeDB();

        return list;
    }

    public ArrayList<Income> selectMontlyIncomes() {
        ArrayList<Income> list = new ArrayList<>();

        Date date1 = null;
        try {
            date1 = sdf.parse(getToday());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Cursor cc = incomeGateway.selectFrequency(Frequency.MONTHLY);

        if (cc != null) {
            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
                Income a = new Income(  cc.getString(0),
                        cc.getString(1),
                        cc.getString(2),
                        cc.getString(3),
                        cc.getString(4),
                        true,
                        cc.getString(5),
                        cc.getString(6),
                        cc.getInt(7));

                if ( a.getStartTime().equals(a.getEndTime())){
                    String[] gg = a.getStartTime().split("-");
                    String[] gg1 = getToday().split("-");
                    if( gg[0].equals(gg1[0]))
                        list.add(a);
                } else try {
                    if (!sdf.parse(a.getEndTime()).before(date1)){
                        String[] gg = a.getStartTime().split("-");
                        String[] gg1 = getToday().split("-");
                        if( gg[0].equals(gg1[0]))
                            list.add(a);
                        //czy data jest konca jest pozniej niz dzisij
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
        incomeGateway.closeDB();

        return list;
    }

    public void DailySet(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity,
                         Income createPreparedOneNotInf, Income createPreparedOneInf){
        dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));
        Days days = Days.daysBetween(new DateTime(past), new DateTime(today));

        if(infinity.isChecked()){
            Income prepared_one = createPreparedOneInf;
            incomeGateway.insert(prepared_one);

            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))) {

                for (int j = 0; j < days.getDays(); j++) {
                    dd.add(Calendar.DATE, 1);
                    insertInfinityIncomes(prepared_one, dd, "4");
                }
            }

        } else {
            Income prepared_one = createPreparedOneNotInf;
            incomeGateway.insert(prepared_one);
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
            incomeGateway.insert(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))) {

                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    insertInfinityIncomes(prepared_one, dd, "5");
                }
            }

        } else {

            Income prepared_one = createPreparedOneNotInf;
            incomeGateway.insert(prepared_one);
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
            incomeGateway.update(prepared_one);

            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today)) && days.getDays() > 0) {

                for (int j = 0; j < days.getDays(); j++) {
                    dd.add(Calendar.DATE, 1);
                    insertInfinityIncomes(prepared_one, dd, "4");
                }
            }

        } else {
            Income prepared_one = createPreparedOneNotInf;
            incomeGateway.update(prepared_one);
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
            incomeGateway.update(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today)) && d > 0) {

                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    insertInfinityIncomes(prepared_one, dd, "5");
                }
            }

        } else {
            Income prepared_one = createPreparedOneNotInf;
            incomeGateway.update(prepared_one);
            if (Utility.chechIfDates(Utility.formatData.format(past), Utility.formatData.format(today))&& d > 0) {

                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    insertNotInfinityIncomes(prepared_one, dd, "5");
                }
            }
        }
    }

    private void insertInfinityIncomes(Income prepared_one, Calendar dd, String cat){
        incomeGateway.insert(new Income(prepared_one.getId(),
                prepared_one.getName(),
                String.valueOf(prepared_one.getAmount().amount().doubleValue()),
                Utility.formatData.format(dd.getTime()),
                Utility.formatData.format(dd.getTime()),
                true,
                prepared_one.getDescription(),
                cat,
                prepared_one.getDuration()));
    }

    private void insertNotInfinityIncomes(Income prepared_one, Calendar dd, String cat){
        incomeGateway.insert(new Income(prepared_one.getId(),
                prepared_one.getName(),
                String.valueOf(prepared_one.getAmount().amount().doubleValue()),
                Utility.formatData.format(dd.getTime()),
                prepared_one.getEndTime(),
                true,
                cat,
                prepared_one.getDescription(),
                prepared_one.getDuration()));

    }

    public Money getIncomesFromMonth() {

        ArrayList<Income> out_list = selectAllIncomes();
        ArrayList<Double> o_list = new ArrayList<>();
        if( out_list.isEmpty()){
            return new Money(0, Currency.getInstance(Locale.getDefault()));
        }

        for (int i = 0; i < out_list.size(); i++) {
            if (Utility.chechIfDates(out_list.get(i).getStartTime(), getToday())) {
                o_list.add(out_list.get(i).getAmount().amount().doubleValue());
            }
        }

        if( o_list.isEmpty()){
            return new Money(0, Currency.getInstance(Locale.getDefault()));
        }

        double sum_out = 0;
        for(Double d : o_list)
            sum_out += d;

        return new Money(sum_out, Currency.getInstance(Locale.getDefault()));
    }
}
