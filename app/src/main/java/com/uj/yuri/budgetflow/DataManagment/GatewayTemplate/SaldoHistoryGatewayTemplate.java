package com.uj.yuri.budgetflow.DataManagment.GatewayTemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Entries;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Saldo;

/**
 * Created by Yuri on 23.12.2016.
 */

public class SaldoHistoryGatewayTemplate extends GatewayTemplate implements GatewayInterface<Saldo> {

    public SaldoHistoryGatewayTemplate(Context context) {
        super(context);
    }

    @Override
    public void insert(Saldo ob) {
        dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Entries.Hist_Saldo.COLUMN_TIME,  ob.getData());
        values.put(Entries.Hist_Saldo.COLUMN_AMOUNT, ob.getAmount().amount().doubleValue());

        dba.insert(Entries.Hist_Saldo.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update(Saldo ob) {
        dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Entries.Hist_Saldo.COLUMN_AMOUNT, ob.getAmount().amount().doubleValue());
        values.put(Entries.Hist_Saldo.COLUMN_TIME, ob.getData());
        dba.update( Entries.Hist_Saldo.TABLE_NAME,  values,
                                                    Entries.Hist_Saldo._ID,
                                                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove(Saldo ob) {
        dba = this.getWritableDatabase();
        dba.delete( Entries.Hist_Saldo.TABLE_NAME,
                    Entries.Hist_Saldo._ID,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public Cursor findAll() {
        dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Hist_Saldo.TABLE_NAME,
                                    Entries.Hist_Saldo.selectAllList,
                                    null, null, null, null, null);

        return cursor;
    }

    @Override
    public Saldo find(String id) {
        dba = this.getReadableDatabase();
        String selection = Entries.Hist_Saldo._ID + " = '" + id + "'";

        Cursor c = dba.query(   Entries.Hist_Saldo.TABLE_NAME,
                new String[] {  Entries.Hist_Saldo.COLUMN_TIME,
                                Entries.Hist_Saldo.COLUMN_AMOUNT },
                                selection,
                                null, null, null, null);


        if (c != null) {
            c.moveToFirst();
            Saldo gg = new Saldo(c.getString(0), c.getString(1));
            c.close();
            return gg;
        }

        return null;
    }

    public Cursor findLast(){
        dba = this.getReadableDatabase();
        String selection = Entries.Hist_Saldo.COLUMN_TIME + " = '"+ Utility.getDayBeforeToday() + "'";

        Cursor c = dba.query(   Entries.Hist_Saldo.TABLE_NAME,
                                new String[] {  Entries.Hist_Saldo.COLUMN_TIME,
                                                Entries.Hist_Saldo.COLUMN_AMOUNT },
                                                selection,
                                                null, null, null, null);
        return c;
    }

}
