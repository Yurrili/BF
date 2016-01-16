package com.uj.yuri.budgetflow.db_managment;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yuri on 2016-01-03.
 */
public interface DateBaseHelper_ {

    void insertIncome(Income ob);
    void updateIncome(Income ob);
    void removeIncome(Income ob);

    void insertOutcome(Outcome ob);
    void updateOutcome(Outcome ob);
    void removeOutcome(Outcome ob);

    void insertCategory(Category ob);
    void updateCategory(Category ob);
    void removeCategory(Category ob);

    ArrayList<Income> selectAllIncomes();
    ArrayList<Outcome> selectAllOutcomes();
    ArrayList<Category> selectAllCategorie();

    ArrayList<Double> selectTodaysOutcomes(String data);
    HashMap<String, Category> selectAllCategories();
}
