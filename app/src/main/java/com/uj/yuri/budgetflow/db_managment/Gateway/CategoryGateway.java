package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;

/**
 * Created by Yuri on 23.12.2016.
 */

public class CategoryGateway extends Gateway implements GatewayInterface<Category> {

    public CategoryGateway(Context context) {
        super(context);
    }

    @Override
    public void insert(Category ob) {
        dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, ob.getName());

        dba.insert(Entries.Categories.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update(Category ob) {
        dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, ob.getName());

        dba.update( Entries.Categories.TABLE_NAME,
                    values,
                    Entries.Categories._ID,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove(Category ob) {
        dba = this.getWritableDatabase();
        dba.delete( Entries.Categories.TABLE_NAME,
                    Entries.Categories._ID,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public Cursor findAll() {
        dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Categories.TABLE_NAME,
                                    Entries.Categories.selectAllList,
                                    null, null, null, null, null);

        return cursor;
    }

    @Override
    public Category find(String id) {
        dba = this.getReadableDatabase();
        Cursor c = dba.query(  Entries.Categories.TABLE_NAME,
                                    Entries.Categories.selectAllList,
                                    Entries.Categories._ID ,
                                    new String[]{ id },
                                    null, null, null);

        if (c != null) {
            c.moveToFirst();
            Category inc = new Category( c.getString(0), c.getString(1));
            c.close();
            return inc;
        }
        return null;
    }




}
