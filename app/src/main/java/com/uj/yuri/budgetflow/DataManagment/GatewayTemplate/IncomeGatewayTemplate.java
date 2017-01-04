package com.uj.yuri.budgetflow.DataManagment.GatewayTemplate;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.uj.yuri.budgetflow.DataManagment.Frequency;
import com.uj.yuri.budgetflow.DataManagment.IdentityMap;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Entries;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Income;


/**
 * Created by Yuri on 23.12.2016.
 */

public class IncomeGatewayTemplate extends GatewayTemplate implements GatewayInterface<Income> {


    public IncomeGatewayTemplate(Context context) {
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

        long id = dba.insert(Entries.Incomes.TABLE_NAME, null, values);
        Income obc = new Income(String.valueOf(id), ob.getName(), String.valueOf(ob.getAmount().amount().doubleValue()), dateTimeStart, dateTimeFinish, ob.isActive(), ob.getFrequency(), ob.getDescription(), ob.getDuration());
        IdentityMap.IncomesIdentityMap.add(obc);
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
                    Entries.Incomes._ID + "=" + ob.getId(), null);

        Income obc = new Income(ob.getId(), ob.getName(), String.valueOf(ob.getAmount().amount().doubleValue()), dateTimeStart, dateTimeFinish, ob.isActive(), ob.getFrequency(), ob.getDescription(), ob.getDuration());
        IdentityMap.IncomesIdentityMap.remove(obc);
        IdentityMap.IncomesIdentityMap.add(obc);
        dba.close();
    }

    @Override
    public void remove(Income ob) {
        dba = this.getWritableDatabase();
        dba.delete( Entries.Incomes.TABLE_NAME,
                    Entries.Incomes._ID + "=" + ob.getId() , null);
        IdentityMap.IncomesIdentityMap.remove(ob);
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
        try {
            Income inc = IdentityMap.IncomesIdentityMap.isInto(id);
            if ( inc != null) {
                return inc;
            } else {
                Cursor c = dba.query(Entries.Incomes.TABLE_NAME,
                        Entries.Incomes.selectAllList,
                        Entries.Incomes._ID,
                        new String[]{id},
                        null, null, null);


                if (c != null) {
                    c.moveToFirst();
                    inc = new Income(c.getString(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            true,
                            c.getString(5),
                            c.getString(6),
                            c.getInt(7));
                    IdentityMap.IncomesIdentityMap.add(inc);
                    c.close();
                    return inc;
                }
                return null;
            }
        } catch (Exception e) {
                e.printStackTrace();
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
