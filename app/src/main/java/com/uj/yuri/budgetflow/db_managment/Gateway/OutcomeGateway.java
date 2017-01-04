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

public class OutcomeGateway extends Gateway implements GatewayInterface<Outcome> {

    public OutcomeGateway(Context context) {
        super(context);
    }

    @Override
    public void insert(Outcome ob) {
       dba = this.getWritableDatabase();
        String dateTimeStart = ob.getStartTime();
        String dateTimeFinish = ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Outcomes.COLUMN_OUTCOME_NAME, ob.getName());
        values.put(Entries.Outcomes.COLUMN_CATEGORY_ID, ob.getCategoryId());
        values.put(Entries.Outcomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Outcomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Outcomes.COLUMN_ACTIVE, ob.isActive());
        values.put(Entries.Outcomes.COLUMN_FREQUENCY, ob.getFrequency());
        values.put(Entries.Outcomes.COLUMN_AMOUNT, String.valueOf(ob.getAmount().amount().doubleValue()));

        dba.insert(Entries.Outcomes.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update(Outcome ob) {
       dba = this.getWritableDatabase();
        String dateTimeStart = ob.getStartTime();
        String dateTimeFinish = ob.getEndTime();

        ContentValues values = new ContentValues();

        values.put(Entries.Outcomes.COLUMN_OUTCOME_NAME, ob.getName());
        values.put(Entries.Outcomes.COLUMN_CATEGORY_ID, ob.getCategoryId());
        values.put(Entries.Outcomes.COLUMN_DATETIME_START, dateTimeStart);
        values.put(Entries.Outcomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        values.put(Entries.Outcomes.COLUMN_ACTIVE, ob.isActive());
        values.put(Entries.Outcomes.COLUMN_FREQUENCY, ob.getFrequency());
        values.put(Entries.Outcomes.COLUMN_AMOUNT, String.valueOf(ob.getAmount().amount().doubleValue()));

        dba.update( Entries.Outcomes.TABLE_NAME,
                    values,
                    Entries.Outcomes._ID ,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove(Outcome ob) {
       dba = this.getWritableDatabase();
        dba.delete( Entries.Outcomes.TABLE_NAME,
                    Entries.Outcomes._ID ,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public Cursor findAll() {
       dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Outcomes.TABLE_NAME,
                                    Entries.Outcomes.selectAllList,
                                    null, null, null, null, null);

        return cursor;
    }

    @Override
    public Outcome find(String id) {
       dba = this.getReadableDatabase();
        Cursor c = dba.query(  Entries.Outcomes.TABLE_NAME,
                                    Entries.Outcomes.selectAllList,
                                    Entries.Outcomes._ID ,
                                    new String[]{ id },
                                    null, null, null);


        if (c != null) {
            c.moveToFirst();
            Outcome inc = new Outcome(  c.getString(0),
                                        c.getString(1),
                                        c.getString(2),
                                        c.getString(3),
                                        c.getString(4),
                                        true,
                                        c.getString(5),
                                        c.getInt(6));
            c.close();
            return inc;
        }
        return null;
    }

    public Cursor selectDate(String date){
       dba = this.getReadableDatabase();
        String selection = Entries.Outcomes.COLUMN_DATETIME_START + " = "
                + date ;

        return dba.query( Entries.Outcomes.TABLE_NAME,
                new String[]{Entries.Outcomes.COLUMN_AMOUNT},
                selection, null, null, null, null);
    }

}
