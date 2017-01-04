package com.uj.yuri.budgetflow.DataManagment.GatewayTemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.uj.yuri.budgetflow.DataManagment.IdentityMap;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Entries;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;


/**
 * Created by Yuri on 23.12.2016.
 */

public class OutcomeGatewayTemplate extends GatewayTemplate implements GatewayInterface<Outcome> {

    public OutcomeGatewayTemplate(Context context) {
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

        long id = dba.insert(Entries.Outcomes.TABLE_NAME, null, values);
        Outcome obc = new Outcome(id, ob.getName(), ob.getAmount().amount().doubleValue(),  dateTimeStart, dateTimeFinish, ob.isActive(), ob.getCategoryId(), ob.getFrequency());
        IdentityMap.OutcomesIdentityMap.add(obc);
        dba.close();
    }

    @Override
    public void update(Outcome ob) {
        dba = this.getWritableDatabase();
        String dateTimeStart = ob.getStartTime();
        String dateTimeFinish = ob.getEndTime();

        ContentValues valuesUpdate = new ContentValues();

        valuesUpdate.put(Entries.Outcomes.COLUMN_OUTCOME_NAME, ob.getName());
        valuesUpdate.put(Entries.Outcomes.COLUMN_CATEGORY_ID, ob.getCategoryId());
        valuesUpdate.put(Entries.Outcomes.COLUMN_DATETIME_START, dateTimeStart);
        valuesUpdate.put(Entries.Outcomes.COLUMN_DATETIME_FINISH, dateTimeFinish);
        valuesUpdate.put(Entries.Outcomes.COLUMN_AMOUNT, String.valueOf(ob.getAmount().amount().doubleValue()));
        valuesUpdate.put(Entries.Outcomes.COLUMN_FREQUENCY, ob.getFrequency());
        valuesUpdate.put(Entries.Outcomes.COLUMN_ACTIVE, ob.isActive());

        dba.update( Entries.Outcomes.TABLE_NAME,
                    valuesUpdate,
                    Entries.Outcomes._ID + "=" + ob.getId() ,
                    null);

        Outcome obc = new Outcome(ob.getId(), ob.getName(), String.valueOf(ob.getAmount().amount().doubleValue()),  dateTimeStart, dateTimeFinish, ob.isActive(), ob.getCategoryId(), ob.getFrequency());
        IdentityMap.OutcomesIdentityMap.remove(obc);
        IdentityMap.OutcomesIdentityMap.add(obc);
        dba.close();
    }

    // error
    @Override
    public void remove(Outcome ob) {
        dba = this.getWritableDatabase();
        dba.delete( Entries.Outcomes.TABLE_NAME,
                    Entries.Outcomes._ID + "=" + ob.getId(), null);
        IdentityMap.OutcomesIdentityMap.remove(ob);
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
        Outcome inc = null;
        try {
            inc = IdentityMap.OutcomesIdentityMap.isInto(id);

            if ( inc != null) {
                return inc;
            } else {
                String where = Entries.Outcomes._ID + "=" + id;
                Cursor c = dba.query(Entries.Outcomes.TABLE_NAME,
                        Entries.Outcomes.selectAllList,
                        where,
                        null,
                        null, null, null);


                if (c != null) {
                    c.moveToFirst();
                    inc = new Outcome(c.getString(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            true,
                            c.getString(5),
                            c.getInt(6));
                    IdentityMap.OutcomesIdentityMap.add(inc);
                    c.close();
                    return inc;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
