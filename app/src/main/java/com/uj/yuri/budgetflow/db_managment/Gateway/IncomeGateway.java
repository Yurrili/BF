package com.uj.yuri.budgetflow.db_managment.Gateway;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uj.yuri.budgetflow.db_managment.Frequency;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;


/**
 * Created by Yuri on 23.12.2016.
 */

public class IncomeGateway extends Gateway implements GatewayInterface<Income> {


    public IncomeGateway(Context context) {
        super(context);
    }

    @Override
    public void insert(Income ob) {
        dba = this.getWritableDatabase();
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
        values.put(Entries.Incomes.COLUMN_AMOUNT, String.valueOf(ob.getAmount().amount().doubleValue()));

        dba.insert(Entries.Incomes.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update(Income ob) {
        dba = this.getWritableDatabase();
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
        values.put(Entries.Incomes.COLUMN_AMOUNT, String.valueOf(ob.getAmount().amount().doubleValue()));

        dba.update( Entries.Incomes.TABLE_NAME,
                    values,
                    Entries.Incomes._ID ,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove(Income ob) {
        dba = this.getWritableDatabase();
        dba.delete( Entries.Incomes.TABLE_NAME,
                    Entries.Incomes._ID ,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public Cursor findAll() {
        dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Incomes.TABLE_NAME,
                                    Entries.Incomes.selectAllList,
                                    null, null, null, null, null);

        return cursor;
    }

    @Override
    public Income find(String id) {
        dba = this.getReadableDatabase();
        Cursor c = dba.query(  Entries.Incomes.TABLE_NAME,
                                    Entries.Incomes.selectAllList,
                                    Entries.Incomes._ID ,
                                    new String[]{ id },
                                    null, null, null);


        if (c != null) {
            c.moveToFirst();
            Income inc = new Income(c.getString(0),
                                    c.getString(1),
                                    c.getString(2),
                                    c.getString(3),
                                    c.getString(4),
                                    true,
                                    c.getString(5),
                                    c.getString(6),
                                    c.getInt(7));
            c.close();
            return inc;
        }
        return null;
    }

    public Cursor selectFrequency(Frequency frequency) {
        dba = this.getReadableDatabase();
        String selection;
        switch (frequency){
            case MONTHLY:
                selection =  Entries.Incomes.COLUMN_FREQUENCY + " = '1' ";
                break;
            case YEARLY:
                selection =  Entries.Incomes.COLUMN_FREQUENCY + " = '2' ";
                break;
            default:
                selection =  Entries.Incomes.COLUMN_FREQUENCY + " = '1' " +
                            " OR " + Entries.Incomes.COLUMN_FREQUENCY + " = '0'";
                break;
        }

        Cursor cc = dba.query(  Entries.Incomes.TABLE_NAME,
                                Entries.Incomes.selectAllList,
                                selection, null, null, null, null);

        return cc;
    }

    public Cursor selectDate(String date){
        dba = this.getReadableDatabase();
        String selection = Entries.Incomes.COLUMN_DATETIME_START + " = '"
                + date + "'";

        Cursor cc = dba.query(Entries.Incomes.TABLE_NAME,
                Entries.Incomes.selectAllList,
                selection, null, null, null, null);


        return cc;
    }

}
