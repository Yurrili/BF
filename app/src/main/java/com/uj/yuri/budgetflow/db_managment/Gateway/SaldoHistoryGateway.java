package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Saldo;

/**
 * Created by Yuri on 23.12.2016.
 */

public class SaldoHistoryGateway extends Gateway<Saldo> {
    @Override
    public void insert(Saldo ob, SQLiteDatabase dba) {
        ContentValues values = new ContentValues();
        values.put(Entries.Hist_Saldo.COLUMN_TIME,  ob.getData());
        values.put(Entries.Hist_Saldo.COLUMN_AMOUNT, ob.getAmount());

        dba.insert(Entries.Hist_Saldo.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update(Saldo ob, SQLiteDatabase dba) {
        ContentValues values = new ContentValues();

        values.put(Entries.Hist_Saldo.COLUMN_AMOUNT, ob.getAmount());
        values.put(Entries.Hist_Saldo.COLUMN_TIME, ob.getData());
        dba.update( Entries.Hist_Saldo.TABLE_NAME,  values,
                                                    Entries.Hist_Saldo._ID,
                                                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove(Saldo ob, SQLiteDatabase dba) {
        dba.delete( Entries.Hist_Saldo.TABLE_NAME,
                    Entries.Hist_Saldo._ID,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public Cursor findAll(SQLiteDatabase dba) {
        Cursor cursor = dba.query(  Entries.Hist_Saldo.TABLE_NAME,
                                    Entries.Hist_Saldo.selectAllList,
                                    null, null, null, null, null);
        dba.close();
        return cursor;
    }

    @Override
    public Cursor find(String id, SQLiteDatabase dba) {
        String selection = Entries.Hist_Saldo._ID + " = '" + id + "'";

        Cursor c = dba.query(   Entries.Hist_Saldo.TABLE_NAME,
                new String[] {  Entries.Hist_Saldo.COLUMN_TIME,
                                Entries.Hist_Saldo.COLUMN_AMOUNT },
                                selection,
                                null, null, null, null);
        dba.close();
        return c;
    }

    public Cursor findLast(SQLiteDatabase dba){
        String selection = Entries.Hist_Saldo.COLUMN_TIME + " = '"+ Utility.getDayBeforeToday() + "'";

        Cursor c = dba.query(   Entries.Hist_Saldo.TABLE_NAME,
                                new String[] {  Entries.Hist_Saldo.COLUMN_TIME,
                                                Entries.Hist_Saldo.COLUMN_AMOUNT },
                                                selection,
                                                null, null, null, null);
        dba.close();
        return c;
    }
}
