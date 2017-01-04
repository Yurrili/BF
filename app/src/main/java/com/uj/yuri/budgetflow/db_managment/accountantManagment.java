package com.uj.yuri.budgetflow.db_managment;

import android.content.Context;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Yuri on 27.12.2016.
 */

public class accountantManagment {

    OutcomeManagment outcomeManagment ;
    SaldoManagment saldoManagment;

    public accountantManagment(OutcomeManagment outcomeManagment, SaldoManagment saldoManagment) {
        this.outcomeManagment = outcomeManagment;
        this.saldoManagment = saldoManagment;
    }

    public accountantManagment(Context ctx) {
        this.outcomeManagment = new OutcomeManagment(ctx);
        this.saldoManagment = new SaldoManagment(ctx);
    }

    public Money countSaldo(){
        Money saldo = outcomeManagment.selectTodaysOutcome();
        outcomeManagment.getOutcomeGateway().closeDB();

        return saldo;
    }
}
