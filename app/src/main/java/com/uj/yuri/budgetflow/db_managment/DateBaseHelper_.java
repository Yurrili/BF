package com.uj.yuri.budgetflow.db_managment;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Saldo;


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

    void insertSaldo(String ob);
    void updateSaldo(String ob);
    void removeSaldo(String ob);

    void insertCategory(Category ob);
    void updateCategory(Category ob);
    void removeCategory(Category ob);

    Saldo selectSaldo(String id);
    Outcome selectOutcome(String id);
    Income selectIncome(String id);
    Saldo selectLastSaldo();

    ArrayList<Income> selectAllIncomes();
    ArrayList<Double> selectAllIncomesToday();
    ArrayList<Double> selectAllOutcomesToday();
    ArrayList<Outcome> selectAllOutcomes();
    HashMap<String, Category> selectAllCategories();

    ArrayList<Income> selectMontlyIncomes();
    ArrayList<Income> selectDailyIncomes();

    ArrayList<Double> selectTodaysOutcomes(String data);

}
