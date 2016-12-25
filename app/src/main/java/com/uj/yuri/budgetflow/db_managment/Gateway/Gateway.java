package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Yuri on 23.12.2016.
 */

public abstract class Gateway<T>{

    public abstract void  insert (T  ob, SQLiteDatabase db);
    public abstract void update (T ob, SQLiteDatabase db);
    public abstract void remove (T ob, SQLiteDatabase db);
    public abstract Cursor findAll(SQLiteDatabase db);
    public abstract Cursor find(String id, SQLiteDatabase db);
}
