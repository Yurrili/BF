package com.uj.yuri.budgetflow.MActivity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.DataManagment.GatewayLogicDB;
import com.uj.yuri.budgetflow.DataManagment.Money;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Saldo;

import java.util.Currency;
import java.util.Locale;

public class FragmentMain extends Fragment {
    private View myFragmentView;
    private static final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private static GatewayLogicDB gatewayLogicDB;
    static private Money Max_am;
    static TextView expensive;
    static TextView incomes;
    static Money saldo;
    static Money saldo_db_before;

    static CircularProgressBar circularProgressBar;
    static TextView sum_to_spend;
    private static Context ctx;
    public FragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_two_main_activity, container, false);
        preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        ctx = getContext();
        gatewayLogicDB = GatewayLogicDB.getInstance(getContext());
        setLay();
        saldo_db_before = new Money(0, Currency.getInstance(Locale.getDefault()));
        CheckIfDates();
        setValuses();

        setOnClicks();



        circularProgressBar.setProgressWithAnimation(getProgress(), 2500); // Default duration = 1500ms

        return myFragmentView;
    }

    private void CheckIfDates(){
        // pROBABLY BUG
        String date = Utility.getDayBeforeDayBeforeToday();
        Saldo date_saldo = gatewayLogicDB.selectLastSaldo();
        if ( date.equals(date_saldo.getData())){
            gatewayLogicDB.insert(new Saldo(Utility.getDayBeforeToday(), saldo));
        }

        saldo_db_before = gatewayLogicDB.selectLastSaldo().getAmount();
    }

    private void setLay(){
        sum_to_spend = (TextView)myFragmentView.findViewById(R.id.sum_to_spend);
        circularProgressBar = (CircularProgressBar)myFragmentView.findViewById(R.id.yourCircularProgressbar);
        expensive = (TextView)myFragmentView.findViewById(R.id.month_outcomes);
        incomes = (TextView)myFragmentView.findViewById(R.id.month_incomes);

    }

    private void setValuses(){
        Max_am = new Money(Double.parseDouble(preferences.getString("Max", "20")), Currency.getInstance(Locale.getDefault()));

        try {
            saldo =  Max_am.subtract(getSaldo()).add(gatewayLogicDB.getTodaysIncome()).add(saldo_db_before);
        } catch (Exception e) {
            e.printStackTrace();
        }
        expensive.setText(gatewayLogicDB.getOutcomesFromMonth().toFormattedString());
        incomes.setText(gatewayLogicDB.getIncomesFromMonth().toFormattedString());
        sum_to_spend.setText(saldo.toFormattedString());
    }

    private void setOnClicks(){
        sum_to_spend.setOnClickListener(new View.OnClickListener() {
            EditText max;
            TextView text;
            Button dialogButton;
            TextInputLayout inputLayoutMax;

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle(getString(R.string.inf_dial2));

                max = (EditText) dialog.findViewById(R.id.max);
                text = (TextView) dialog.findViewById(R.id.text_dial);
                dialogButton = (Button) dialog.findViewById(R.id.add_button);
                inputLayoutMax = (TextInputLayout) dialog.findViewById(R.id.input_max_limit);

                text.setText(getString(R.string.inf_dial1));
                max.setHint(getString(R.string.inf_dial3) + new Money(Double.parseDouble(preferences.getString("Max", "20")), Currency.getInstance(Locale.getDefault())).toFormattedString());


                dialogButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        if (validateLimit()) {
                            dialog.dismiss();
                            Max_am = new Money(Double.parseDouble(max.getText().toString()), Currency.getInstance(Locale.getDefault()));
                            SharedPreferences.Editor preferencesEditor = preferences.edit();
                            preferencesEditor.putString("Max", Max_am.toString());

                            preferencesEditor.apply();
                            try {
                                saldo = Max_am.subtract(getSaldo()).add(gatewayLogicDB.getTodaysIncome()).add(saldo_db_before);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            sum_to_spend.setText(saldo.toFormattedString());
                            circularProgressBar.setProgressWithAnimation(getProgress(), 2500); // Default duration = 1500ms
                        }
                    }

                    private boolean validateLimit() {

                        if (max.getText().toString().isEmpty()) {
                            inputLayoutMax.setError(getString(R.string.error_limit));
                            requestFocus(max);
                            return false;
                        } else {
                            inputLayoutMax.setErrorEnabled(false);
                        }

                        return true;
                    }

                    private void requestFocus(View view) {
                        if (view.requestFocus()) {
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private static Money getSaldo() throws Exception {
        Money sumOut = gatewayLogicDB.countSaldo();

        Double value = Max_am.subtract(sumOut).add(gatewayLogicDB.getTodaysIncome()).add(saldo_db_before).amount().doubleValue();

        if (  value> 0){
            sum_to_spend.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary));
            circularProgressBar.setColor(ContextCompat.getColor(ctx, R.color.greeno));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.greeno1));
        } else {
            sum_to_spend.setTextColor(ContextCompat.getColor(ctx, R.color.redo));
            circularProgressBar.setColor(ContextCompat.getColor(ctx, R.color.redo));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.redo1));
        }
        return sumOut ;
    }



    private float getProgress(){
        try {
            if (getSaldo().amount().doubleValue() == 0){
                return 0;
            }else
                return (float) ((getSaldo().add(gatewayLogicDB.getTodaysIncome())).divideByNumber(Max_am.amount().doubleValue())).amount().floatValue()*100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void refash(){
        try {
            saldo = Max_am.subtract(getSaldo()).add(gatewayLogicDB.getTodaysIncome()).add(saldo_db_before);
        } catch (Exception e) {
            e.printStackTrace();
        }

        expensive.setText(gatewayLogicDB.getOutcomesFromMonth().toFormattedString());
        incomes.setText(gatewayLogicDB.getIncomesFromMonth().toFormattedString());
        sum_to_spend.setText(saldo.toFormattedString());
    }

}
