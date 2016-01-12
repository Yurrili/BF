package com.uj.yuri.budgetflow.db_managment.db_helper_objects;

import com.uj.yuri.budgetflow.db_managment.db_main_classes.Category_;

/**
 * Created by Yuri on 2016-01-03.
 */
public class Category implements Category_{
    protected String id;
    protected String categoryName;
    protected boolean active;

    public Category(String id, String categoryName, boolean active) {
        this.id = id;
        this.categoryName = categoryName;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
