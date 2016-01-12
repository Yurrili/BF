package com.uj.yuri.budgetflow.db_managment;

import com.uj.yuri.budgetflow.db_managment.db_main_classes.Category_;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.Income_;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.Outcome_;

import java.util.ArrayList;

/**
 * Created by Yuri on 2016-01-03.
 */
public interface DateBaseHelper_ {

    void insertIncome(Income_ ob);
    void updateIncome(Income_ ob);
    void removeIncome(Income_ ob);

    void insertOutcome(Outcome_ ob);
    void updateOutcome(Outcome_ ob);
    void removeOutcome(Outcome_ ob);

    void insertCategory(Category_ ob);
    void updateCategory(Category_ ob);
    void removeCategory(Category_ ob);

    ArrayList<Income_> selectAllIncomes();
    ArrayList<Outcome_> selectAllOutcomes();
    ArrayList<Category_> selectAllCategories();
}
