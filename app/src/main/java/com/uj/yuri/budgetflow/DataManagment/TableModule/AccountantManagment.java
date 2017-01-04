package com.uj.yuri.budgetflow.DataManagment.TableModule;

import android.content.Context;

import com.uj.yuri.budgetflow.DataManagment.Money;

/**
 * Created by Yuri on 27.12.2016.
 */

public class AccountantManagment {

    OutcomeManagment outcomeManagment ;
    SaldoManagment saldoManagment;

    public AccountantManagment(OutcomeManagment outcomeManagment, SaldoManagment saldoManagment) {
        this.outcomeManagment = outcomeManagment;
        this.saldoManagment = saldoManagment;
    }

    public AccountantManagment(Context ctx) {
        this.outcomeManagment = new OutcomeManagment(ctx);
        this.saldoManagment = new SaldoManagment(ctx);
    }

    public Money countSaldo(){
        Money saldo = outcomeManagment.selectTodaysOutcome();
        outcomeManagment.getOutcomeGateway().closeDB();

        return saldo;
    }
}
