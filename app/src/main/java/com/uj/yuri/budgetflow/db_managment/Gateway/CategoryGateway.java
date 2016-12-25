package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;

/**
 * Created by Yuri on 23.12.2016.
 */

public class CategoryGateway extends Gateway<Category> {

    @Override
    public void insert(Category ob, SQLiteDatabase dba) {
        ContentValues values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, ob.getName());

        dba.insert(Entries.Categories.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update(Category ob, SQLiteDatabase dba) {
        ContentValues values = new ContentValues();

        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, ob.getName());

        dba.update( Entries.Categories.TABLE_NAME,
                    values,
                    Entries.Categories._ID,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove(Category ob, SQLiteDatabase dba) {
        dba.delete( Entries.Categories.TABLE_NAME,
                    Entries.Categories._ID,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public Cursor findAll(SQLiteDatabase dba) {
        Cursor cursor = dba.query(  Entries.Categories.TABLE_NAME,
                                    Entries.Categories.selectAllList,
                                    null, null, null, null, null);
        dba.close();
        return cursor;
    }

    @Override
    public Cursor find(String id, SQLiteDatabase dba) {
        Cursor cursor = dba.query(  Entries.Categories.TABLE_NAME,
                                    Entries.Categories.selectAllList,
                                    Entries.Categories._ID ,
                                    new String[]{ id },
                                    null, null, null);
        dba.close();
        return cursor;
    }


}
