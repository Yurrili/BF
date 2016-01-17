package com.uj.yuri.budgetflow;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;

import java.util.ArrayList;

public class MainActivity_Two_Fragment extends Fragment {
    private View myFragmentView;
    private static final String PREFERENCES_NAME = "myPreferences";
    private SharedPreferences preferences;
    private DateBaseHelper_ db;
    private Double Max_am;
    CircularProgressBar circularProgressBar;
    TextView sum_to_spend;
    public MainActivity_Two_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_two_main_activity, container, false);
        preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        circularProgressBar = (CircularProgressBar)myFragmentView.findViewById(R.id.yourCircularProgressbar);
        circularProgressBar.setColor(ContextCompat.getColor(getContext(), R.color.redo));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.redo1));
        //circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        //circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        sum_to_spend = (TextView)myFragmentView.findViewById(R.id.sum_to_spend);
        db = new DateBaseHelper(getActivity());
        Max_am = Double.parseDouble(preferences.getString("Max", "20"));
        Double saldo = Max_am - getSaldo();


        sum_to_spend.setText(saldo + " PLN");

        sum_to_spend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Set daily limit");
                EditText max = (EditText) dialog.findViewById(R.id.max);
                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text_dial);
                text.setText("Choose you limit:");

                max.setHint("Most recent limit :" +preferences.getString("Max", "20"));
                Button dialogButton = (Button) dialog.findViewById(R.id.add_button);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        if (validateLimit()) {
                            EditText max = (EditText) dialog.findViewById(R.id.max);
                            dialog.dismiss();
                            Max_am = Double.parseDouble(max.getText().toString());
                            SharedPreferences.Editor preferencesEditor = preferences.edit();
                            preferencesEditor.putString("Max", Max_am.toString());
                            preferencesEditor.apply();
                            Double saldo = Max_am - getSaldo();
                            TextView sum_to_spend = (TextView) myFragmentView.findViewById(R.id.sum_to_spend);
                            sum_to_spend.setText(saldo + " PLN");
                            circularProgressBar.setProgressWithAnimation(getProgress(), 2500); // Default duration = 1500ms
                        }
                    }

                    private boolean validateLimit() {
                        EditText max = (EditText) dialog.findViewById(R.id.max);
                        TextInputLayout inputLayoutMax = (TextInputLayout)  dialog.findViewById(R.id.input_max_limit);
                        if (max.getText().toString().isEmpty()) {
                            inputLayoutMax.setError("Enter correct limit");
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

        circularProgressBar.setProgressWithAnimation(getProgress(), animationDuration); // Default duration = 1500ms


        return myFragmentView;
    }

    private double getSaldo(){
        ArrayList<Double> l_out = db.selectAllOutcomesToday();
        //ArrayList<Double> l_in = db.selectAllIncomesToday();

        if( l_out.isEmpty()){
            return 0;
        }
        double sum_out = 0;
        for(Double d : l_out)
            sum_out += d;

//
//        double sum_in = 0;
//        for(Double d : l_in)
//            sum_in += d;

        if ( Max_am - sum_out  >=0){
            sum_to_spend.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }else {
            sum_to_spend.setTextColor(ContextCompat.getColor(getContext(), R.color.redo));
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
