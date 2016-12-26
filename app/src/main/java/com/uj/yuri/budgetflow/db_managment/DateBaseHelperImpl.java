package com.uj.yuri.budgetflow.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uj.yuri.budgetflow.db_managment.Gateway.CategoryGateway;
import com.uj.yuri.budgetflow.db_managment.Gateway.IncomeGateway;
import com.uj.yuri.budgetflow.db_managment.Gateway.OutcomeGateway;
import com.uj.yuri.budgetflow.db_managment.Gateway.SaldoHistoryGateway;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Saldo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Yuri on 2015-12-28.
 */

public class DateBaseHelperImpl implements DateBaseHelper  {

    private IncomeGateway incomeGateway ;
    private OutcomeGateway outcomeGateway ;
    private CategoryGateway categoryGateway ;
    private SaldoHistoryGateway saldoGateway ;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public DateBaseHelperImpl(Context context) {

        incomeGateway = new IncomeGateway(context);
        outcomeGateway = new OutcomeGateway(context);
        categoryGateway = new CategoryGateway(context);
        saldoGateway = new SaldoHistoryGateway(context);

    }

    public void insertIncome(Income ob) {
        incomeGateway.insert(ob);
    }


    public void updateIncome(Income ob) {
        incomeGateway.update(ob);
    }


    public void removeIncome(Income ob) {
        incomeGateway.remove(ob);
    }

    public Income selectIncome(String id){
        Cursor c = incomeGateway.find(id);

        if (c != null) {
            c.moveToFirst();
            Income income = new Income( c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    true,
                    c.getString(5),
                    c.getString(6),
                    c.getInt(7));
            c.close();
            return income;
        }
        return null;
    }

    public ArrayList<Double> selectAllIncomesToday() {
        //SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Double> list = new ArrayList<>();
//
//        String selection = Entries.Incomes.COLUMN_DATETIME_START + " = '"
//                + Utility.getToday() + "'";
//
//        Cursor cc = dba.query(Entries.Incomes.TABLE_NAME,
//                new String[]{Entries.Incomes.COLUMN_AMOUNT, Entries.Incomes.COLUMN_FREQUENCY},
//                selection, null, null, null, null);
//
//        if (cc != null) {
//            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
//                if(cc.getString(1).equals("0"))
//                    list.add(Double.parseDouble(cc.getString(0)));
//            }
//        }
//
//        cc.close();
//        dba.close();
        return list;
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
        return list;
    }


    public ArrayList<Income> selectDailyIncomes() {
        ArrayList<Income> list = new ArrayList<>();

        try {
            Date date1 = sdf.parse(Utility.getToday());

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

        return list;
    }


    public ArrayList<Income> selectMontlyIncomes() {
        ArrayList<Income> list = new ArrayList<>();

        Date date1 = null;
        try {
            date1 = sdf.parse(Utility.getToday());
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
                    String[] gg1 = Utility.getToday().split("-");
                    if( gg[0].equals(gg1[0]))
                        list.add(a);
                } else try {
                    if (!sdf.parse(a.getEndTime()).before(date1)){
                        String[] gg = a.getStartTime().split("-");
                        String[] gg1 = Utility.getToday().split("-");
                        if( gg[0].equals(gg1[0]))
                            list.add(a);
                        //czy data jest konca jest pozniej niz dzisij
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        return list;
    }

    /**
     * OUTCOME
     */


    public void insertOutcome(Outcome ob) {
        outcomeGateway.insert(ob);
    }


    public void updateOutcome(Outcome ob) {
        outcomeGateway.update(ob);
    }


    public void removeOutcome(Outcome ob) {
        outcomeGateway.remove(ob);
    }


    public Outcome selectOutcome(String id){
        Cursor c = outcomeGateway.find(id);

        if (c != null) {
            c.moveToFirst();
            Outcome out =  new Outcome( c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    true,
                    c.getString(5),
                    c.getInt(6));
            c.close();
            return out;
        }
        return null;
    }


    public ArrayList<Double> selectTodaysOutcomes(String data){
//        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Double> list = new ArrayList<>();
//
//        String selection = Entries.Outcomes.COLUMN_DATETIME_START + " = '"
//                + data + "'";
//        Cursor c = dba.query(Entries.Outcomes.TABLE_NAME,
//                new String[] {Entries.Outcomes.COLUMN_AMOUNT},
//                selection, null, null, null, null);
//
//        if (c != null) {
//            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
//                list.add(Double.parseDouble(c.getString(0)));
//            }
//        }
//
//        c.close();
//        dba.close();
        return list;
    }


    public ArrayList<Outcome> selectAllOutcomes(){
        ArrayList<Outcome> list = new ArrayList<>();
        Cursor c = outcomeGateway.findAll();

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(new Outcome(   c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        true,
                        c.getString(5),
                        c.getInt(6)));
            }
            c.close();
        }
        return list;
    }


    public ArrayList<Double> selectAllOutcomesToday() {
//        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Double> list = new ArrayList<>();

//        String selection = Entries.Outcomes.COLUMN_DATETIME_START + " = '"
//                + Utility.getToday() + "'";
//
//        Cursor cc = dba.query(Entries.Outcomes.TABLE_NAME,
//                new String[]{Entries.Outcomes.COLUMN_AMOUNT, Entries.Outcomes.COLUMN_CATEGORY_ID},
//                selection, null, null, null, null);
//
//        if (cc != null) {
//            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
//                if (    cc.getString(1).equals("0")  ||
//                        cc.getString(1).equals("1")  ||
//                        cc.getString(1).equals("2")  ||
//                        cc.getString(1).equals("14")    )
//                    list.add(Double.parseDouble(cc.getString(0)));
//            }
//        }
//
//        cc.close();
//        dba.close();
        return list;
    }

    /**
     * SALDO
     */


    public void insertSaldo(String amount) {
        saldoGateway.insert(new Saldo(Utility.getDayBeforeToday(), amount));
    }


    public void updateSaldo(String amount) {
        saldoGateway.update(new Saldo(Utility.getDayBeforeToday(), amount));
    }


    public void removeSaldo(String amount) {
        saldoGateway.remove(new Saldo(Utility.getDayBeforeToday(), amount));
    }


    public Saldo selectSaldo(String id) {
        Cursor c = saldoGateway.find(id);
        if (c != null) {
            c.moveToFirst();
            Saldo out =  new Saldo( c.getString(0),  c.getString(1));
            c.close();
            return out;
        }
        return null;
    }


    public Saldo selectLastSaldo(){
        Saldo gg = new Saldo(Utility.getDayBeforeToday(), 0.0);
        Cursor c = saldoGateway.findLast();

        if (c != null) {
            c.moveToFirst();
            gg = new Saldo(c.getString(0), c.getString(1));
            c.close();
        }

        return gg;
    }

    /**
     * CATEGORY
     */


    public void insertCategory(Category ob) {
        categoryGateway.insert(ob);
    }


    public void updateCategory(Category ob) {
        categoryGateway.update(ob);
    }


    public void removeCategory(Category ob) {
        categoryGateway.remove(ob);
    }

    public HashMap<String, Category> selectAllCategories() {
        HashMap<String, Category> hash_list = new HashMap<>();

        Cursor c = categoryGateway.findAll();

        int i = 0;
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                hash_list.put(c.getString(0), new Category(c.getString(0), c.getString(1), (i%7) + ""));
                i++;
            }
            c.close();
        }

        return hash_list;
    }

}
