package com.uj.yuri.budgetflow.DataManagment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CheckBox;

import com.uj.yuri.budgetflow.DataManagment.GatewayTemplate.GatewayTemplate;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Income;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Saldo;
import com.uj.yuri.budgetflow.DataManagment.TableModule.AccountantManagment;
import com.uj.yuri.budgetflow.DataManagment.TableModule.CategoryManagment;
import com.uj.yuri.budgetflow.DataManagment.TableModule.IncomeManagment;
import com.uj.yuri.budgetflow.DataManagment.TableModule.OutcomeManagment;
import com.uj.yuri.budgetflow.DataManagment.TableModule.SaldoManagment;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Yuri on 04.01.2017.
 */

public class GatewayLogicDB {
    private static GatewayLogicDB mInstance = null;

    private AccountantManagment accountantManagment;
    private OutcomeManagment outcomeManagment;
    private SaldoManagment saldoManagment;
    private IncomeManagment incomeManagment;
    private CategoryManagment categoryManagment;


    public GatewayLogicDB(Context ctx) {
        SQLiteDatabase helper = new GatewayTemplate(ctx).getWritableDatabase();
        saldoManagment = new SaldoManagment(ctx);
        outcomeManagment = new OutcomeManagment(ctx);
        incomeManagment = new IncomeManagment(ctx);
        categoryManagment = new CategoryManagment(ctx);
        accountantManagment = new AccountantManagment(outcomeManagment, saldoManagment);
    }

    public static GatewayLogicDB getInstance(Context ctx){
        if(mInstance == null)
        {
            System.err.println("Gateway created");
            mInstance = new GatewayLogicDB(ctx);
        }
        System.err.println("Instance exist");
        return mInstance;
    }

    /**
     * SALDO
     */

    public Saldo selectLastSaldo() {
        return saldoManagment.selectLastSaldo();
    }

    public void update(Saldo ob) {
        saldoManagment.getSaldoGateway().update(ob);
    }

    public void remove(Saldo ob) {
        saldoManagment.getSaldoGateway().remove(ob);
    }

    public void insert(Saldo ob) {
        saldoManagment.getSaldoGateway().insert(ob);
    }

    /**
     * INCOME
     */

    public void update(Income ob) {
        incomeManagment.getIncomeGateway().insert(ob);
    }

    public void remove(Income ob) {
        incomeManagment.getIncomeGateway().remove(ob);
    }

    public void insert(Income ob) {
        incomeManagment.getIncomeGateway().insert(ob);
    }

    public Income findIncome(String id) {
        return incomeManagment.getIncomeGateway().find(id);
    }

    public ArrayList<Income> selectAllIncomes(){
        return incomeManagment.selectAllIncomes();
    }

    public ArrayList<Income> selectDailyIncomes() {
        return incomeManagment.selectDailyIncomes();
    }

    public ArrayList<Income> selectMontlyIncomes() {
        return incomeManagment.selectMontlyIncomes();
    }

    public Money getTodaysIncome() {
        return incomeManagment.getTodaysIncome();
    }

    public void DailySet(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity,
                         Income createPreparedOneNotInf, Income createPreparedOneInf){
        incomeManagment.DailySet(date_split, dd, past, today, infinity, createPreparedOneNotInf, createPreparedOneInf);
    }

    public void MontlySet(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity
            ,Income createPreparedOneNotInf, Income createPreparedOneInf){
        incomeManagment.MontlySet(date_split, dd, past, today, infinity, createPreparedOneNotInf, createPreparedOneInf);
    }

    public void DailySetUp(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity,
                           Income createPreparedOneNotInf,Income createPreparedOneInf){
        incomeManagment.DailySetUp(date_split, dd, past, today, infinity, createPreparedOneNotInf, createPreparedOneInf);

    }

    public void MontlySetUp(String[] date_split, Calendar dd, Date past, Date today, CheckBox infinity
            ,Income createPreparedOneNotInf, Income createPreparedOneInf){
        incomeManagment.MontlySetUp(date_split, dd, past, today, infinity, createPreparedOneNotInf, createPreparedOneInf);
    }

    public Money getIncomesFromMonth() {
        return incomeManagment.getIncomesFromMonth();
    }

    /**
     * OUTCOME
     */

    public void update(Outcome ob) {
        outcomeManagment.getOutcomeGateway().insert(ob);
    }

    public void remove(Outcome ob) {
        outcomeManagment.getOutcomeGateway().remove(ob);
    }

    public void insert(Outcome ob) {
        outcomeManagment.getOutcomeGateway().insert(ob);
    }

    public Outcome findOutcome(String id) {
        return outcomeManagment.getOutcomeGateway().find(id);
    }

    public Money selectTodaysOutcome(){
        return outcomeManagment.selectTodaysOutcome();
    }

    public ArrayList<Outcome> selectAllOutcomes(){
        return outcomeManagment.selectAllOutcomes();
    }

    public ArrayList<Outcome> selectAllTodaysOutcomes() {
        return outcomeManagment.selectAllTodaysOutcomes();
    }

    public Money getOutcomesFromMonth() {
        return outcomeManagment.getOutcomesFromMonth();
    }

    /**
     * Category
     */

    public void update(Category ob) {
        categoryManagment.getCategoryGateway().insert(ob);
    }

    public void remove(Category ob) {
        categoryManagment.getCategoryGateway().remove(ob);
    }

    public void insert(Category ob) {
        categoryManagment.getCategoryGateway().insert(ob);
    }

    public Map<Integer, Category> selectAllCategories() {
        return categoryManagment.selectAllCategories();
    }

    public Category findCategory(String id) {
        return categoryManagment.getCategoryGateway().find(id);
    }

    /**
     * Accountant
     */

    public Money countSaldo(){
        return accountantManagment.countSaldo();
    }

}
