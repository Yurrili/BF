package com.uj.yuri.budgetflow.db_managment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Yuri on 2015-12-28.
 */

public class DateBaseHelper extends SQLiteOpenHelper implements DateBaseHelper_ {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BudgetFlow.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_TYPE =" INTEGER";

    private IncomeGateway incomeGateway = new IncomeGateway();
    private OutcomeGateway outcomeGateway = new OutcomeGateway();
    private CategoryGateway categoryGateway = new CategoryGateway();
    private SaldoHistoryGateway saldoGateway = new SaldoHistoryGateway();

    private static final String SQL_CREATE_ENTRIES_Incomes =
            "CREATE TABLE " + Entries.Incomes.TABLE_NAME + " (" +
                    Entries.Incomes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Entries.Incomes.COLUMN_INCOME_NAME + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_ACTIVE + INTEGER_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DATETIME_START + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DATETIME_FINISH + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_FREQUENCY + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    Entries.Incomes.COLUMN_DURATION + INTEGER_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_Outcomes =
            "CREATE TABLE " + Entries.Outcomes.TABLE_NAME + " (" +
                    Entries.Outcomes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Entries.Outcomes.COLUMN_OUTCOME_NAME + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_AMOUNT + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_ACTIVE + INTEGER_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_DATETIME_START + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_DATETIME_FINISH + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_FREQUENCY + TEXT_TYPE + COMMA_SEP +
                    Entries.Outcomes.COLUMN_CATEGORY_ID + INTEGER_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_Categories =
            "CREATE TABLE " + Entries.Categories.TABLE_NAME + " (" +
                    Entries.Categories._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Entries.Categories.COLUMN_CATEGORY_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_Hist_Saldo =
            "CREATE TABLE " + Entries.Hist_Saldo.TABLE_NAME + " (" +
                    Entries.Hist_Saldo.COLUMN_TIME + TEXT_TYPE + COMMA_SEP +
                    Entries.Hist_Saldo.COLUMN_AMOUNT + TEXT_TYPE +
                    " )";

    public DateBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_Categories);
        Log.d("DB", "created Categories");
        db.execSQL(SQL_CREATE_ENTRIES_Incomes);
        Log.d("DB", "created Incomes");
        db.execSQL(SQL_CREATE_ENTRIES_Hist_Saldo);
        Log.d("DB", "created Notifications");
        db.execSQL(SQL_CREATE_ENTRIES_Outcomes);
        Log.d("DB", "created Outcomes");

        ContentValues values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Food");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Fun Money");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Personal");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Household Items/Supplies");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Transportation");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Clothing");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Rent");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Household Repairs");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Utilities/Bills");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Medical");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Insurance");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Education");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Savings");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Gifts");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Others");
        db.insert(Entries.Categories.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(Entries.Hist_Saldo.COLUMN_TIME, "Food");
        values.put(Entries.Hist_Saldo.COLUMN_AMOUNT, Utility.getDayBeforeToday());
        db.insert(Entries.Hist_Saldo.TABLE_NAME, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Categories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Incomes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Outcomes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Hist_Saldo.TABLE_NAME);
        Log.d("DB", "DATABASES DROPPED");

        onCreate(db);
    }

    /**
     * INCOME
     */

    @Override
    public void insertIncome(Income ob) {
        incomeGateway.insert(ob, this.getWritableDatabase());
    }

    @Override
    public void updateIncome(Income ob) {
        incomeGateway.update(ob, this.getWritableDatabase());
    }

    @Override
    public void removeIncome(Income ob) {
        incomeGateway.remove(ob, this.getWritableDatabase());
    }

    @Override
    public Income selectIncome(String id){
        Cursor c = incomeGateway.find(id, this.getReadableDatabase());

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

    @Override
    public ArrayList<Double> selectAllIncomesToday() {
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Double> list = new ArrayList<>();

        String selection = Entries.Incomes.COLUMN_DATETIME_START + " = '"
                + Utility.getToday() + "'";

        Cursor cc = dba.query(Entries.Incomes.TABLE_NAME,
                new String[]{Entries.Incomes.COLUMN_AMOUNT, Entries.Incomes.COLUMN_FREQUENCY},
                selection, null, null, null, null);

        if (cc != null) {
            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
                if(cc.getString(1).equals("0"))
                    list.add(Double.parseDouble(cc.getString(0)));
            }
        }

        cc.close();
        dba.close();
        return list;
    }

    @Override
    public ArrayList<Income> selectAllIncomes(){
        ArrayList<Income> list = new ArrayList<>();
        Cursor c = incomeGateway.findAll(this.getReadableDatabase());
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

    @Override
    public ArrayList<Income> selectDailyIncomes() {
        ArrayList<Income> list = new ArrayList<>();

        try {
            Date date1 = sdf.parse(Utility.getToday());

            Cursor cc = incomeGateway.selectFrequency(Frequency.DAILY, this.getReadableDatabase());

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

    @Override
    public ArrayList<Income> selectMontlyIncomes() {
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Income> list = new ArrayList<>();

        Date date1 = null;
        try {
            date1 = sdf.parse(Utility.getToday());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Cursor cc = incomeGateway.selectFrequency(Frequency.MONTHLY, this.getReadableDatabase());

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

    @Override
    public void insertOutcome(Outcome ob) {
        outcomeGateway.insert(ob, this.getWritableDatabase());
    }

    @Override
    public void updateOutcome(Outcome ob) {
        outcomeGateway.update(ob, this.getWritableDatabase());
    }

    @Override
    public void removeOutcome(Outcome ob) {
        outcomeGateway.remove(ob, this.getWritableDatabase());
    }

    @Override
    public Outcome selectOutcome(String id){
        Cursor c = outcomeGateway.find(id, this.getReadableDatabase());

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

    @Override
    public ArrayList<Double> selectTodaysOutcomes(String data){
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Double> list = new ArrayList<>();

        String selection = Entries.Outcomes.COLUMN_DATETIME_START + " = '"
                + data + "'";
        Cursor c = dba.query(Entries.Outcomes.TABLE_NAME,
                new String[] {Entries.Outcomes.COLUMN_AMOUNT},
                selection, null, null, null, null);

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(Double.parseDouble(c.getString(0)));
            }
        }

        c.close();
        dba.close();
        return list;
    }

    @Override
    public ArrayList<Outcome> selectAllOutcomes(){
        ArrayList<Outcome> list = new ArrayList<>();
        Cursor c = outcomeGateway.findAll(this.getReadableDatabase());

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

    @Override
    public ArrayList<Double> selectAllOutcomesToday() {
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Double> list = new ArrayList<>();

        String selection = Entries.Outcomes.COLUMN_DATETIME_START + " = '"
                + Utility.getToday() + "'";

        Cursor cc = dba.query(Entries.Outcomes.TABLE_NAME,
                new String[]{Entries.Outcomes.COLUMN_AMOUNT, Entries.Outcomes.COLUMN_CATEGORY_ID},
                selection, null, null, null, null);

        if (cc != null) {
            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
                if (    cc.getString(1).equals("0")  ||
                        cc.getString(1).equals("1")  ||
                        cc.getString(1).equals("2")  ||
                        cc.getString(1).equals("14")    )
                    list.add(Double.parseDouble(cc.getString(0)));
            }
        }

        cc.close();
        dba.close();
        return list;
    }

    /**
     * SALDO
     */

    @Override
    public void insertSaldo(String amount) {
        saldoGateway.insert(new Saldo(Utility.getDayBeforeToday(), amount), this.getWritableDatabase());
    }

    @Override
    public void updateSaldo(String amount) {
        saldoGateway.update(new Saldo(Utility.getDayBeforeToday(), amount), this.getWritableDatabase());
    }

    @Override
    public void removeSaldo(String amount) {
        saldoGateway.remove(new Saldo(Utility.getDayBeforeToday(), amount), this.getWritableDatabase());
    }

    @Override
    public Saldo selectSaldo(String id) {
        Cursor c = saldoGateway.find(id, this.getReadableDatabase());
        if (c != null) {
            c.moveToFirst();
            Saldo out =  new Saldo( c.getString(0),  c.getString(1));
            c.close();
            return out;
        }
        return null;
    }

    @Override
    public Saldo selectLastSaldo(){
        Saldo gg = new Saldo(Utility.getDayBeforeToday(), 0.0);
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor c = saldoGateway.findLast(this.getReadableDatabase());

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

    @Override
    public void insertCategory(Category ob) {
        categoryGateway.insert(ob, this.getWritableDatabase());
    }

    @Override
    public void updateCategory(Category ob) {
        categoryGateway.update(ob, this.getWritableDatabase());
    }

    @Override
    public void removeCategory(Category ob) {
        categoryGateway.remove(ob, this.getWritableDatabase());
    }

    @Override
    public HashMap<String, Category> selectAllCategories() {
        SQLiteDatabase dba = this.getReadableDatabase();
        HashMap<String, Category> hash_list = new HashMap<>();

        Cursor c = categoryGateway.findAll(this.getReadableDatabase());

        int i = 0;
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                hash_list.put(c.getString(0), new Category(c.getString(0), c.getString(1), (i%7) + ""));
                i++;
            }
            c.close();
        }

        dba.close();
        return hash_list;
    }





}
