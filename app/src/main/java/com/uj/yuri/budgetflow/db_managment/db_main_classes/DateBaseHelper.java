package com.uj.yuri.budgetflow.db_managment.db_main_classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;


import java.util.ArrayList;
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

    private static final String SQL_CREATE_ENTRIES_Incomes =
            "CREATE TABLE " + Entries.Incomes.TABLE_NAME + " (" +
                    Entries.Incomes.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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
                    Entries.Outcomes.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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
                    Entries.Categories.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Entries.Categories.COLUMN_CATEGORY_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_Notifications =
            "CREATE TABLE " + Entries.Notifications.TABLE_NAME + " (" +
                    Entries.Notifications.COLUMN_TIME + TEXT_TYPE +
                    " )";

    public DateBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_Categories);
        Log.d("DB", "created Categories");
        db.execSQL(SQL_CREATE_ENTRIES_Incomes);
        Log.d("DB", "created Incomes");
        db.execSQL(SQL_CREATE_ENTRIES_Notifications);
        Log.d("DB", "created Notifications");
        db.execSQL(SQL_CREATE_ENTRIES_Outcomes);
        Log.d("DB", "created Outcomes");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Categories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Incomes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Outcomes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Notifications.TABLE_NAME);
        Log.d("DB", "DATABASES DROPPED");

        onCreate(db);
    }

    public void insertIncome(Income ob) {
        SQLiteDatabase dba = this.getWritableDatabase();

        String dateTimeStart = ob.getStartTime();
        String dateTimeFinish = ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Incomes.COLUMN_INCOME_NAME, ob.getName());
        values.put(Entries.Incomes.COLUMN_DURATION, ob.getDuration());
        values.put(Entries.Incomes.COLUMN_DESCRIPTION, ob.getDescription());
        values.put(Entries.Incomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Incomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Incomes.COLUMN_ACTIVE, ob.isActive());
        values.put(Entries.Incomes.COLUMN_FREQUENCY, ob.getFrequency());
        values.put(Entries.Incomes.COLUMN_AMOUNT, ob.getAmount());

        dba.insert(Entries.Incomes.TABLE_NAME, null, values);
        dba.close();
    }

    public void updateIncome(Income ob) {
        SQLiteDatabase dba = this.getWritableDatabase();

        String dateTimeStart = ob.getStartTime();
        String dateTimeFinish = ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Incomes.COLUMN_INCOME_NAME, ob.getName());
        values.put(Entries.Incomes.COLUMN_DURATION, ob.getDuration());
        values.put(Entries.Incomes.COLUMN_DESCRIPTION, ob.getDescription());
        values.put(Entries.Incomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Incomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Incomes.COLUMN_ACTIVE, ob.isActive());
        values.put(Entries.Incomes.COLUMN_FREQUENCY, ob.getFrequency());
        values.put(Entries.Incomes.COLUMN_AMOUNT, ob.getAmount());

        dba.update(Entries.Incomes.TABLE_NAME, values, Entries.Incomes.COLUMN_ID + "='" + ob.getId() + "'", null);
        dba.close();
    }

    public void removeIncome(Income ob) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Entries.Incomes.TABLE_NAME, Entries.Incomes.COLUMN_ID + "='" + ob.getId() + "'", null);
        dba.close();
    }

    public void insertOutcome(Outcome ob) {
        SQLiteDatabase dba = this.getWritableDatabase();

        String dateTimeStart = ob.getStartTime();
        String dateTimeFinish = ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Outcomes.COLUMN_OUTCOME_NAME, ob.getName());
        values.put(Entries.Outcomes.COLUMN_CATEGORY_ID, ob.getCategoryId());
        values.put(Entries.Outcomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Outcomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Outcomes.COLUMN_ACTIVE, ob.isActive());
        values.put(Entries.Outcomes.COLUMN_FREQUENCY, ob.getFrequency());
        values.put(Entries.Outcomes.COLUMN_AMOUNT, ob.getAmount());

        dba.insert(Entries.Outcomes.TABLE_NAME, null, values);
        dba.close();
    }

    public void updateOutcome(Outcome ob) {
        SQLiteDatabase dba = this.getWritableDatabase();

        String dateTimeStart = ob.getStartTime();
        String dateTimeFinish = ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Outcomes.COLUMN_OUTCOME_NAME, ob.getName());
        values.put(Entries.Outcomes.COLUMN_CATEGORY_ID, ob.getCategoryId());
        values.put(Entries.Outcomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Outcomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Outcomes.COLUMN_ACTIVE, ob.isActive());
        values.put(Entries.Outcomes.COLUMN_FREQUENCY, ob.getFrequency());
        values.put(Entries.Outcomes.COLUMN_AMOUNT, ob.getAmount());

        dba.update(Entries.Outcomes.TABLE_NAME, values, Entries.Outcomes.COLUMN_ID + "='" + ob.getId() + "'", null);
        dba.close();
    }

    public void removeOutcome(Outcome ob) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Entries.Outcomes.TABLE_NAME, Entries.Outcomes.COLUMN_ID + "='" + ob.getId() + "'", null);
        dba.close();
    }

    public void insertCategory(Category ob) {
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, ob.getName());
        dba.insert(Entries.Categories.TABLE_NAME, null, values);
        dba.close();
    }

    public void updateCategory(Category ob) {
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, ob.getName());

        dba.update(Entries.Categories.TABLE_NAME, values, Entries.Categories.COLUMN_ID + "='" + ob.getId() + "'", null);
        dba.close();
    }

    public void removeCategory(Category ob) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Entries.Categories.TABLE_NAME, Entries.Categories.COLUMN_ID + "='" + ob.getId() + "'", null);
        dba.close();
    }

    public ArrayList<Income> selectAllIncomes(){
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Income> list = new ArrayList<>();


        Cursor c = dba.query(Entries.Incomes.TABLE_NAME,
                Entries.Incomes.selectAllList,
                null, null, null, null, null);

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(new Income( c.getString(0),
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

        c.close();
        dba.close();
        return list;
    }

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

    public ArrayList<Outcome> selectAllOutcomes(){
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Outcome> list = new ArrayList<>();


        Cursor c = dba.query(Entries.Outcomes.TABLE_NAME,
                Entries.Outcomes.selectAllList,
                null, null, null, null, null);

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(new Outcome( c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        true,
                        c.getString(5),
                        c.getInt(6)));
            }
        }

        c.close();
        dba.close();
        return list;
    }

    @Override
    public ArrayList<Category> selectAllCategorie() {
        SQLiteDatabase dba = this.getReadableDatabase();
        ArrayList<Category> list = new ArrayList<>();

        Cursor c = dba.query(Entries.Categories.TABLE_NAME,
                new String [] { "Id", "Name" },
                null, null, null, null, null);

        int i = 0;
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(new Category(c.getString(0), c.getString(1), (i%7) + ""));
                i++;
            }
        }

        c.close();
        dba.close();
        return list;
    }

    @Override
    public HashMap<String, Category> selectAllCategories() {
        SQLiteDatabase dba = this.getReadableDatabase();
            HashMap<String, Category> hash_list = new HashMap<>();

        Cursor c = dba.query(Entries.Categories.TABLE_NAME,
                new String [] { "Id", "Name" },
                null, null, null, null, null);

        int i = 0;
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                hash_list.put(c.getString(0), new Category(c.getString(0), c.getString(1), (i%7) + ""));
                i++;
            }
        }

        c.close();
        dba.close();
        return hash_list;
    }
}
