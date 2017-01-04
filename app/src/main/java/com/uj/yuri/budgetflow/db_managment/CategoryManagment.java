package com.uj.yuri.budgetflow.db_managment;

import android.content.Context;
import android.database.Cursor;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.Gateway.CategoryGateway;

import java.util.HashMap;

/**
 * Created by Yuri on 27.12.2016.
 */

public class CategoryManagment {

    private CategoryGateway categoryGateway;

    public CategoryManagment(Context ctx){
        categoryGateway = new CategoryGateway(ctx);
    }

    public CategoryGateway getCategoryGateway() {
        return categoryGateway;
    }

        public HashMap<String, Category> selectAllCategories() {
        HashMap<String, Category> hash_list = new HashMap<>();

        Cursor c = categoryGateway.findAll();

        int i = 0;
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                hash_list.put(c.getString(0), new Category(c.getString(0), c.getString(1), (i%7) + ""));
                i++;
            }
            c.close();
        }

        categoryGateway.closeDB();
        return hash_list;
    }
}
