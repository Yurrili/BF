package com.uj.yuri.budgetflow.db_managment;

import android.util.Pair;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;


import java.text.ParseException;
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

    void insertSaldoHist(String ob);

    Pair<String,Double> selectHistSaldo();
    ArrayList<Pair<String,Double>> selectLastSaldo();
    ArrayList<Income> selectAllIncomes();
    ArrayList<Outcome> selectAllOutcomes();
    ArrayList<Income> selectMontlyIncomes();
    ArrayList<Income> selectDailyIncomes();
    ArrayList<Double> selectAllIncomesToday();
    ArrayList<Double> selectAllOutcomesToday();
    Outcome selectOutcome(String id);
    Income selectIncome(String id);


    HashMap<String, Category> selectAllCategories();
}
