package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;


/**
 * Created by Yuri on 23.12.2016.
 */

public class OutcomeGateway extends Gateway<Outcome> {

    public OutcomeGateway(Context context) {
        super(context);
    }

    @Override
    public void insert(Outcome ob) {
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

    @Override
    public void update(Outcome ob) {
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

        dba.update( Entries.Outcomes.TABLE_NAME,
                    values,
                    Entries.Outcomes._ID ,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove(Outcome ob) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete( Entries.Outcomes.TABLE_NAME,
                    Entries.Outcomes._ID ,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public Cursor findAll() {
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Outcomes.TABLE_NAME,
                                    Entries.Outcomes.selectAllList,
                                    null, null, null, null, null);
        dba.close();
        return cursor;
    }

    @Override
    public Cursor find(String id) {
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Outcomes.TABLE_NAME,
                                    Entries.Outcomes.selectAllList,
                                    Entries.Outcomes._ID ,
                                    new String[]{ id },
                                    null, null, null);
        dba.close();
        return cursor;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Entries.SQL_CREATE_ENTRIES_Outcomes);
        Log.d("DB", "created Outcomes");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Outcomes.TABLE_NAME);
    }
}
