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

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private Context ctx;

    public DateBaseHelperImpl(Context context) {
        this.ctx = context;
    }

    public void insertIncome(Income ob) {
        IncomeGateway incomeGateway = new IncomeGateway(ctx, ob);
        incomeGateway.insert();
    }

    public void updateIncome(Income ob) {
        IncomeGateway incomeGateway = new IncomeGateway(ctx, ob);
        incomeGateway.update();
    }

    public void removeIncome(Income ob) {
        IncomeGateway incomeGateway = new IncomeGateway(ctx, ob);
        incomeGateway.remove();
    }

    public Income selectIncome(String id){
        IncomeGateway incomeGateway = new IncomeGateway(ctx);
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
        IncomeGateway incomeGateway = new IncomeGateway(ctx);
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
        IncomeGateway incomeGateway = new IncomeGateway(ctx);
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
        IncomeGateway incomeGateway = new IncomeGateway(ctx);
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
        IncomeGateway incomeGateway = new IncomeGateway(ctx);

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
        OutcomeGateway outcomeGateway = new OutcomeGateway(ctx, ob);
        outcomeGateway.insert();
    }

    public void updateOutcome(Outcome ob) {
        OutcomeGateway outcomeGateway = new OutcomeGateway(ctx, ob);
        outcomeGateway.update();
    }

    public void removeOutcome(Outcome ob) {
        OutcomeGateway outcomeGateway = new OutcomeGateway(ctx, ob);
        outcomeGateway.remove();
    }

    public Outcome selectOutcome(String id){
        OutcomeGateway outcomeGateway = new OutcomeGateway(ctx);
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
        OutcomeGateway outcomeGateway = new OutcomeGateway(ctx);
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
        OutcomeGateway outcomeGateway = new OutcomeGateway(ctx);
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
        OutcomeGateway outcomeGateway = new OutcomeGateway(ctx);
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
        SaldoHistoryGateway saldoGateway = new SaldoHistoryGateway(ctx, new Saldo(Utility.getDayBeforeToday(), amount));
        saldoGateway.insert();
    }

    public void updateSaldo(String amount) {
        SaldoHistoryGateway saldoGateway = new SaldoHistoryGateway(ctx, new Saldo(Utility.getDayBeforeToday(), amount));
        saldoGateway.update();
    }

    public void removeSaldo(String amount) {
        SaldoHistoryGateway saldoGateway = new SaldoHistoryGateway(ctx, new Saldo(Utility.getDayBeforeToday(), amount));
        saldoGateway.remove();
    }

    public Saldo selectSaldo(String id) {
        SaldoHistoryGateway saldoGateway = new SaldoHistoryGateway(ctx);
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
        SaldoHistoryGateway saldoGateway = new SaldoHistoryGateway(ctx);

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
        CategoryGateway categoryGateway = new CategoryGateway(ctx, ob);
        categoryGateway.insert();
    }

    public void updateCategory(Category ob) {
        CategoryGateway categoryGateway = new CategoryGateway(ctx, ob);
        categoryGateway.update();
    }

    public void removeCategory(Category ob) {
        CategoryGateway categoryGateway = new CategoryGateway(ctx, ob);
        categoryGateway.remove();
    }

    public HashMap<String, Category> selectAllCategories() {
        HashMap<String, Category> hash_list = new HashMap<>();
        CategoryGateway categoryGateway = new CategoryGateway(ctx);
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
