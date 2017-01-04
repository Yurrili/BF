package com.uj.yuri.budgetflow.NActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.uj.yuri.budgetflow.EActivity.EditIncomes;

import java.util.Calendar;

/**
 * Created by Yuri on 2016-01-18.
 */
public class DatePickers {

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            FragmentIncomeNew.date_place.setText(setDAtePick(year, month, day));
        }
    }

    public static String setDAtePick(int year, int month, int day){
        String d,m;

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
        return d + "-" + m +  "-" + year;
    }

    public static class DatePickerFragmentTo extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            FragmentIncomeNew.date_placeTo.setText(setDAtePick(year, month, day));
        }
    }

    public static class DatePickerFragmentE extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            EditIncomes.date_place1.setText(setDAtePick(year, month, day));
        }
    }


    public static class DatePickerFragmentToE extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditIncomes.date_placeTo1.setText(setDAtePick(year, month, day));
        }
    }
}
