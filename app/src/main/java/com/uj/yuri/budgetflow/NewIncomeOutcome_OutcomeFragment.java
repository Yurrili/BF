package com.uj.yuri.budgetflow;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class NewIncomeOutcome_OutcomeFragment extends Fragment {
    private View myFragmentView;
    private Spinner spinner;
    public DateBaseHelper_ helper;
    private HashMap<String, Category> hashCat;
    public static EditText date_place;
    public static EditText time_place;
    static final int DATE_DIALOG_ID = 999;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

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

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minutes = c.get(Calendar.MINUTE);


            // Create a new instance of DatePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minutes, true);
        }

        public void onTimeSet(TimePicker view, int hours, int minutes) {
            String h;
            String m;

            if( hours < 10){
                h = "0"+hours;

            }else {
                h = hours +"";
            }
            if( minutes < 10){
                m = "0"+ minutes;

            }else {
                m = minutes +"";
            }

            time_place.setText(h + ":" + m);
        }
    }


    public NewIncomeOutcome_OutcomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_new_outcome, container, false);
        this.helper = new DateBaseHelper(getActivity());
        hashCat = helper.selectAllCategories();
        date_place =  (EditText) myFragmentView.findViewById(R.id.date_place);
        time_place =  (EditText) myFragmentView.findViewById(R.id.time_place);



        date_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        time_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        spinner = (Spinner) myFragmentView.findViewById(R.id.spinner_cat);
        setSpinnerAdapter();
        return myFragmentView;
    }

    void setSpinnerAdapter() {
        ArrayList<Category> cat = new ArrayList<>(hashCat.values());
        MySpinner adapter = new MySpinner(getActivity(),
                android.R.layout.simple_spinner_item, cat);
        spinner.setAdapter(adapter);
    }

        public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

}
