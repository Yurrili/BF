package com.uj.yuri.budgetflow.db_managment.Gateway;

import android.database.Cursor;

/**
 * Created by Yuri on 03.01.2017.
 */

public interface GatewayInterface<T> {
    public abstract void insert (T  ob);
    public abstract void update (T ob);
    public abstract void remove (T ob);
    public abstract Cursor findAll();
    public abstract T find(String id);
}
