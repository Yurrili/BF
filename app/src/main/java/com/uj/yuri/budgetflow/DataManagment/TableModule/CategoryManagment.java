package com.uj.yuri.budgetflow.DataManagment.TableModule;

import android.content.Context;
import android.database.Cursor;

import com.uj.yuri.budgetflow.DataManagment.IdentityMap;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;
import com.uj.yuri.budgetflow.DataManagment.GatewayTemplate.CategoryGatewayTemplate;

import java.util.Map;

/**
 * Created by Yuri on 27.12.2016.
 */

public class CategoryManagment {

    private CategoryGatewayTemplate categoryGateway;

    public CategoryManagment(Context ctx){
        categoryGateway = new CategoryGatewayTemplate(ctx);
    }

    public CategoryGatewayTemplate getCategoryGateway() {
        return categoryGateway;
    }

    public Map<Integer, Category> selectAllCategories() {

        Cursor c = categoryGateway.findAll();

        int i = 0;
        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

               // Category cat = new Category(c.getString(0), c.getString(1), (i%7) + "");
                Category cat = null;
                try {
                    cat = IdentityMap.CategoryIdentityMap.isInto(c.getString(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ( cat == null) {
                    IdentityMap.CategoryIdentityMap.add(new Category(c.getString(0), c.getString(1), (i%7) + ""));
                    i++;
                }

            }
            c.close();
        }

        categoryGateway.closeDB();
        return IdentityMap.CategoryIdentityMap.getHash();
    }
}
