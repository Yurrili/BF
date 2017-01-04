package com.uj.yuri.budgetflow.db_managment;

import android.content.Context;
import android.database.Cursor;

import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.Gateway.OutcomeGateway;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import static com.uj.yuri.budgetflow.Utility.getToday;

/**
 * Created by Yuri on 26.12.2016.
 */
public class OutcomeManagment {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private OutcomeGateway outcomeGateway ;

    public OutcomeManagment(Context ctx) {
        this.outcomeGateway = new OutcomeGateway(ctx);
    }

    public OutcomeGateway getOutcomeGateway() {
        return outcomeGateway;
    }

    public Money selectTodaysOutcome(){
        Double sum = 0.0;
        Cursor cc = outcomeGateway.findAll();

        if (cc != null) {
            for (cc.moveToFirst(); !cc.isAfterLast(); cc.moveToNext()) {
                if (cc.getString(3).equals(getToday()))
                    sum += Double.parseDouble(cc.getString(2));

            }
            cc.close();
        }
        outcomeGateway.closeDB();

        return new Money(sum, Currency.getInstance(Locale.getDefault()));
    }

    public ArrayList<Outcome> selectAllOutcomes(){
        ArrayList<Outcome> list = new ArrayList<>();
        Cursor c = outcomeGateway.findAll();

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                list.add(new Outcome(   c.getString(0),
                                        c.getString(1),
                                        c.getString(2),
                                        c.getString(3),
                                        c.getString(4),
                                        true,
                                        c.getString(5),
                                        c.getInt(6)));
            }
            c.close();
        }

        outcomeGateway.closeDB();
        return list;
    }

    public ArrayList<Outcome> selectAllTodaysOutcomes() {
        ArrayList<Outcome> list = new ArrayList<>();

        Cursor c = outcomeGateway.selectDate(getToday());

        if (c != null) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    list.add(new Outcome(c.getString(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            true,
                            c.getString(5),
                            c.getInt(6)));

            }
            c.close();
        }

        outcomeGateway.closeDB();
        return list;
    }

    public Money getOutcomesFromMonth() {
        ArrayList<Outcome> out_list = selectAllOutcomes();
        ArrayList<Double> o_list = new ArrayList<>();
        if( out_list.isEmpty()){
            return new Money(0, Currency.getInstance(Locale.getDefault()));
        }

        for (int i = 0; i < out_list.size(); i++) {
            if (Utility.chechIfDates(out_list.get(i).getStartTime(), getToday())) {
                o_list.add(out_list.get(i).getAmount().amount().doubleValue());
            }
        }

        if( o_list.isEmpty()){
            return new Money(0, Currency.getInstance(Locale.getDefault()));
        }

        double sum_out = 0;
        for(Double d : o_list)
            sum_out += d;

        return new Money(sum_out, Currency.getInstance(Locale.getDefault()));
    }

}
