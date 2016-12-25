package com.uj.yuri.budgetflow.m_activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.BackUp.ExportDataBase;
import com.uj.yuri.budgetflow.db_managment.BackUp.ImpExpUses;
import com.uj.yuri.budgetflow.db_managment.BackUp.ImportDataBase;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Saldo;

import java.util.ArrayList;

public class FragmentMain extends Fragment {
    private View myFragmentView;
    private static final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private static DateBaseHelper_ db;
    static private Double Max_am;
    static TextView expensive;
    static TextView incomes;
    static Double saldo;
    static Double saldo_db_before;

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
        db = new DateBaseHelper(getActivity());
        ctx = getContext();
        setLay();
        saldo_db_before = 0.0;
        CheckIfDates();
        setValuses();

        setOnClicks();



        circularProgressBar.setProgressWithAnimation(getProgress(), 2500); // Default duration = 1500ms

        return myFragmentView;
    }

    private void CheckIfDates(){
        String date = Utility.getDayBeforeDayBeforeToday();
        Saldo date_saldo = db.selectSaldo(saldo.toString());
        if ( date.equals(date_saldo.getData())){
            db.insertSaldo(saldo.toString());
        }

        saldo_db_before = db.selectLastSaldo().getAmount();
    }

    private void setLay(){
        sum_to_spend = (TextView)myFragmentView.findViewById(R.id.sum_to_spend);
        circularProgressBar = (CircularProgressBar)myFragmentView.findViewById(R.id.yourCircularProgressbar);
        expensive= (TextView)myFragmentView.findViewById(R.id.month_outcomes);
        incomes = (TextView)myFragmentView.findViewById(R.id.month_incomes);

    }

    private void setValuses(){
        Max_am = Double.parseDouble(preferences.getString("Max", "20"));

        saldo =  Max_am - getSaldo() + getTodaysIncomes() + saldo_db_before;
        expensive.setText(Utility.getOutcomesFromMonth(db.selectAllOutcomes())+ Utility.moneyVal);
        incomes.setText(Utility.getIncomesFromMonth(db.selectAllIncomes())+ Utility.moneyVal);
        sum_to_spend.setText(saldo + Utility.moneyVal);
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
                max.setHint(getString(R.string.inf_dial3) + preferences.getString("Max", "20"));


                dialogButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        if (validateLimit()) {
                            dialog.dismiss();
                            Max_am = Double.parseDouble(max.getText().toString());
                            SharedPreferences.Editor preferencesEditor = preferences.edit();
                            preferencesEditor.putString("Max", Max_am.toString());

                            preferencesEditor.apply();
                            saldo = Max_am - getSaldo() + getTodaysIncomes() + saldo_db_before;
                            sum_to_spend.setText(saldo + Utility.moneyVal);
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

    private static double getSaldo(){
        ArrayList<Double> l_out = db.selectAllOutcomesToday();

        if( l_out.isEmpty()){
            return 0;
        }
        double sum_out = 0;
        for(Double d : l_out)
            sum_out += d;


        if ( Max_am - sum_out + getTodaysIncomes() + saldo_db_before > 0){
            sum_to_spend.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary));
            circularProgressBar.setColor(ContextCompat.getColor(ctx, R.color.greeno));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.greeno1));
        } else {
            sum_to_spend.setTextColor(ContextCompat.getColor(ctx, R.color.redo));
            circularProgressBar.setColor(ContextCompat.getColor(ctx, R.color.redo));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.redo1));
        }
        return sum_out ;
    }

    private static double getTodaysIncomes(){
        ArrayList<Double> l_in = db.selectAllIncomesToday();
        double sum_in = 0;
        for(Double d : l_in) {
            sum_in += d;
        }
        return sum_in;
    }

    private float getProgress(){
        if (getSaldo() == 0){
            return 0;
        }else
            return (float) (( getSaldo() + getTodaysIncomes() )/Max_am)*100;
    }

    public static void refash(){
        saldo = Max_am - getSaldo() + getTodaysIncomes() + saldo_db_before;
        expensive.setText(Utility.getOutcomesFromMonth(db.selectAllOutcomes())+ Utility.moneyVal);
        incomes.setText(Utility.getIncomesFromMonth(db.selectAllIncomes())+ Utility.moneyVal);
        sum_to_spend.setText(saldo + Utility.moneyVal);
    }

}
