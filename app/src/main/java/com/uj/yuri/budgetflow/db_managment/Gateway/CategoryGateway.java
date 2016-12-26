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

public class CategoryGateway extends Gateway {

    private Category ob;

    public CategoryGateway(Context context) {
        super(context);
    }

    public CategoryGateway(Context context, Category category) {
        super(context);
        this.ob = category;
    }

    public Category getCategory() {
        return this.ob;
    }

    @Override
    public void insert() {
        SQLiteDatabase dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, this.ob.getName());

        dba.insert(Entries.Categories.TABLE_NAME, null, values);
        dba.close();
    }

    @Override
    public void update() {
        SQLiteDatabase dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, this.ob.getName());

        dba.update( Entries.Categories.TABLE_NAME,
                    values,
                    Entries.Categories._ID,
                    new String[]{ ob.getId() });
        dba.close();
    }

    @Override
    public void remove() {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete( Entries.Categories.TABLE_NAME,
                    Entries.Categories._ID,
                    new String[]{ this.ob.getId() });
        dba.close();
    }

    public Cursor findAll() {
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Categories.TABLE_NAME,
                                    Entries.Categories.selectAllList,
                                    null, null, null, null, null);
        dba.close();
        return cursor;
    }

    @Override
    public Cursor find(String id) {
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(  Entries.Categories.TABLE_NAME,
                                    Entries.Categories.selectAllList,
                                    Entries.Categories._ID ,
                                    new String[]{ id },
                                    null, null, null);
        dba.close();
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Entries.SQL_CREATE_ENTRIES_Categories);
        Log.d("DB", "created Categories");
        putMockedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entries.Categories.TABLE_NAME);
    }

    private void putMockedData(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Food");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Fun Money");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Personal");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Household Items/Supplies");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Transportation");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Clothing");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Rent");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Household Repairs");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Utilities/Bills");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Medical");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Insurance");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Education");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Savings");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Gifts");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
        values = new ContentValues();
        values.put(Entries.Categories.COLUMN_CATEGORY_NAME, "Others");
        db.insert(Entries.Categories.TABLE_NAME, null, values);
    }
}
