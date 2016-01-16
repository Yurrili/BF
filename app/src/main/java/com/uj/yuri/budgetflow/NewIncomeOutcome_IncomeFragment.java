package com.uj.yuri.budgetflow;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DecimalFormat;
import java.util.Calendar;

public class NewIncomeOutcome_IncomeFragment extends Fragment {
    private View myFragmentView;
    public static EditText date_place;
    public EditText amount;
    public RadioGroup gr_radio_times;
    public RadioButton radio_btn1;
    public RadioButton radio_btn2;
    public RadioButton radio_btn3;
    public RadioButton radio_btn4;

    public NewIncomeOutcome_IncomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_new_income, container, false);
        date_place =  (EditText) myFragmentView.findViewById(R.id.date_place1);
        amount = (EditText) myFragmentView.findViewById(R.id.amount_place);
        gr_radio_times = (RadioGroup) myFragmentView.findViewById(R.id.radiogr);
        radio_btn1 = (RadioButton) myFragmentView.findViewById(R.id.radioButton1);
        radio_btn2 = (RadioButton) myFragmentView.findViewById(R.id.radioButton2);
        radio_btn3 = (RadioButton) myFragmentView.findViewById(R.id.radioButton3);
        radio_btn4 = (RadioButton) myFragmentView.findViewById(R.id.radioButton4);

        setDateTime();
        date_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });


        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!amount.getText().toString().equals("")) {
                        double number = Double.parseDouble(amount.getText().toString());
                        DecimalFormat format = new DecimalFormat("0.00");
                        String formatted = format.format(number);
                        amount.setText(formatted);
                    }

                }
            }
        });

        radio_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date_place.setEnabled(false);
                date_place.setClickable(false);

            }
        });

        radio_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    date_place.setEnabled(true);
                    date_place.setClickable(true);

            }
        });
        radio_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    date_place.setEnabled(true);
                    date_place.setClickable(true);

            }
        });
        radio_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    date_place.setEnabled(true);
                    date_place.setClickable(true);

            }
        });
        return myFragmentView;
    }

    public void setDateTime(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String d,md;



        if( day < 10){
            d = "0"+day;

        }else {
            d = day +"";
        }
        month = month +1;
        if( month < 10){
            md = "0"+ month;

        }else {
            md = month +"";
        }

        date_place.setText(d+"-"+md+"-"+year);

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String d;
            String m;

            if( day < 10){
                d = "0"+day;

            }else {
                d = day +"";
            }
            month = month +1;
            if( month < 10){
                m = "0"+ month;

            }else {
                m = month +"";
            }
            date_place.setText(d + "-" + m +  "-" + year);
        }
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
