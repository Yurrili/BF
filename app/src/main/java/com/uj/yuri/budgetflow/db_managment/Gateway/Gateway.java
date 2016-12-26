package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Yuri on 23.12.2016.
 */

public abstract class Gateway<T> extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BudgetFlow.db";

    public Gateway(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public abstract void insert (T  ob);
    public abstract void update (T ob);
    public abstract void remove (T ob);
    public abstract Cursor findAll();
    public abstract Cursor find(String id);
}
