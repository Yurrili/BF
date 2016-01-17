package com.uj.yuri.budgetflow.m_activity;


import android.app.Activity;
import android.app.Dialog;
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
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;

import java.util.ArrayList;

public class FragmentMain extends Fragment {
    private View myFragmentView;
    private static final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private DateBaseHelper_ db;
    private Double Max_am;
    TextView expensive;
    TextView incomes;
    Double saldo;
    CircularProgressBar circularProgressBar;
    TextView sum_to_spend;
    public FragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_two_main_activity, container, false);
        preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        db = new DateBaseHelper(getActivity());
        setLay();

        setValuses();
        setOnClicks();


        circularProgressBar.setProgressWithAnimation(getProgress(), 2500); // Default duration = 1500ms

        return myFragmentView;
    }

    private void setLay(){
        sum_to_spend = (TextView)myFragmentView.findViewById(R.id.sum_to_spend);
        circularProgressBar = (CircularProgressBar)myFragmentView.findViewById(R.id.yourCircularProgressbar);
        expensive= (TextView)myFragmentView.findViewById(R.id.month_outcomes);
        incomes = (TextView)myFragmentView.findViewById(R.id.month_incomes);

    }

    private void setValuses(){
        Max_am = Double.parseDouble(preferences.getString("Max", "20"));
        saldo = Max_am - getSaldo();
        expensive.setText(Utility.getOutcomesFromMonth(db.selectAllOutcomes())+ Utility.moneyVal);
        incomes.setText(Utility.getIncomesFromMonth(db.selectAllIncomes())+ Utility.moneyVal);
        sum_to_spend.setText(saldo + Utility.moneyVal);
    }

    private void setOnClicks(){
        sum_to_spend.setOnClickListener(new View.OnClickListener() {
            EditText max ;
            TextView text;
            Button dialogButton ;
            TextInputLayout inputLayoutMax ;

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle(getString(R.string.inf_dial2));

                max = (EditText) dialog.findViewById(R.id.max);
                text = (TextView) dialog.findViewById(R.id.text_dial);
                dialogButton = (Button) dialog.findViewById(R.id.add_button);
                inputLayoutMax = (TextInputLayout)  dialog.findViewById(R.id.input_max_limit);

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
                            saldo = Max_am - getSaldo();
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

    private double getSaldo(){
        ArrayList<Double> l_out = db.selectAllOutcomesToday();

        if( l_out.isEmpty()){
            return 0;
        }
        double sum_out = 0;
        for(Double d : l_out)
            sum_out += d;

        if ( Max_am - sum_out  >=0){
            sum_to_spend.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            circularProgressBar.setColor(ContextCompat.getColor(getContext(), R.color.greeno));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.greeno1));
        }else {
            sum_to_spend.setTextColor(ContextCompat.getColor(getContext(), R.color.redo));
            circularProgressBar.setColor(ContextCompat.getColor(getContext(), R.color.redo));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.redo1));
        }
        return sum_out;
    }

    private float getProgress(){
        if (getSaldo() == 0){
            return 0;
        }else
            return (float) (getSaldo()/Max_am)*100;
    }


}
