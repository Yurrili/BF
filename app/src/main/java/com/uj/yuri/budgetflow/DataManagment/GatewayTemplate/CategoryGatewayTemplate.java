package com.uj.yuri.budgetflow.DataManagment.GatewayTemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.uj.yuri.budgetflow.DataManagment.IdentityMap;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Entries;

/**
 * Created by Yuri on 23.12.2016.
 */

public class CategoryGatewayTemplate extends GatewayTemplate implements GatewayInterface<Category> {

    public CategoryGatewayTemplate(Context context) {
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
        try {
            Category cat = IdentityMap.CategoryIdentityMap.isInto(id);
            if ( cat != null) {
                return cat;
            } else {
                dba = this.getReadableDatabase();
                Cursor c = dba.query(Entries.Categories.TABLE_NAME,
                        Entries.Categories.selectAllList,
                        Entries.Categories._ID,
                        new String[]{id},
                        null, null, null);

                if (c != null) {
                    c.moveToFirst();
                    Category inc = new Category(c.getString(0), c.getString(1));
                    IdentityMap.CategoryIdentityMap.add(inc);
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




}
