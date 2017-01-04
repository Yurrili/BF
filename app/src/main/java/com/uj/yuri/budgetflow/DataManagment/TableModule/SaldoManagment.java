package com.uj.yuri.budgetflow.DataManagment.TableModule;

import android.content.Context;
import android.database.Cursor;

import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Saldo;
import com.uj.yuri.budgetflow.DataManagment.GatewayTemplate.SaldoHistoryGatewayTemplate;

/**
 * Created by Yuri on 26.12.2016.
 */

public class SaldoManagment {

    private SaldoHistoryGatewayTemplate saldoGateway ;

    public SaldoManagment(Context ctx) {
        this.saldoGateway = new SaldoHistoryGatewayTemplate(ctx);
    }

    public SaldoHistoryGatewayTemplate getSaldoGateway() {
        return saldoGateway;
    }

    public Saldo selectLastSaldo(){
        Saldo gg = new Saldo(Utility.getDayBeforeToday(), 0.0);
        Cursor c = saldoGateway.findLast();

        if (c.getCount() > 0) {
            c.moveToFirst();
            gg = new Saldo(c.getString(0), c.getString(1));

            c.close();
        }

        saldoGateway.closeDB();
        return gg;
    }


}
