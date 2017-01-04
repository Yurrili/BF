package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;

import static com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries.SQL_CREATE_ENTRIES_Categories;
import static com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries.SQL_CREATE_ENTRIES_Hist_Saldo;
import static com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries.SQL_CREATE_ENTRIES_Incomes;
import static com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries.SQL_CREATE_ENTRIES_Outcomes;


/**
 * Created by Yuri on 23.12.2016.
 */

public class Gateway extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BudgetFlow.db";
    protected  SQLiteDatabase dba;

    public Gateway(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void closeDB(){
        dba.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_Categories);
        Log.d("DB", "created Categories");
        db.execSQL(SQL_CREATE_ENTRIES_Incomes);
        Log.d("DB", "created Incomes");
         db.execSQL(SQL_CREATE_ENTRIES_Hist_Saldo);
         Log.d("DB", "created Hist_Saldo");
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Categories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Incomes.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Outcomes.TABLE_NAME);
       // db.execSQL("DROP TABLE IF EXISTS " + Entries.Notifications.TABLE_NAME);
        Log.d("DB", "DATABASES DROPPED");

        onCreate(db);
    }
}
