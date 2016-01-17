package com.uj.yuri.budgetflow;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.AlertDialog;



import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;

import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.DialogFragment;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;
import com.uj.yuri.budgetflow.view_managment_listview.Utility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Yuri on 2016-01-17.
 */
public class DialogEditOutcomes extends DialogFragment implements Command {
    public View myDialogView;
    public DateBaseHelper_ helper;
    private HashMap<String, Category> hashCat;
    private TextInputLayout inputLayoutAmount;
    public static EditText date_place;
    private Spinner spinner;
    public EditText amount;
    public EditText note;
    FragmentManager cmd;

    public DialogEditOutcomes()
    {}

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        myDialogView = inflater.inflate(R.layout.dialog_edit_outcome, null);

        initLayout();

        setDateTime();
        spinner = (Spinner) myDialogView.findViewById(R.id.spinner_cat);
        setSpinnerAdapter();
        setAllListeners();
        
        builder.setView(myDialogView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;

    }

    private void initLayout()
    {
        helper = new DateBaseHelper(getActivity());
        hashCat = helper.selectAllCategories();
        date_place =  (EditText) myDialogView.findViewById(R.id.date_place);

        note =  (EditText) myDialogView.findViewById(R.id.note_place);
        amount = (EditText) myDialogView.findViewById(R.id.amount_place);

        inputLayoutAmount = (TextInputLayout) myDialogView.findViewById(R.id.input_amount_place);
        amount.addTextChangedListener(new MyTextWatcher(amount));
    }

    public void setDateTime(){

        date_place.setText(Utility.getToday());

    }

    void setSpinnerAdapter() {
        ArrayList<Category> cat = new ArrayList<>(hashCat.values());
        MySpinner adapter = new MySpinner(getActivity(),
                android.R.layout.simple_spinner_item, cat);
        spinner.setAdapter(adapter);
    }

    public void showDatePickerDialog(View v) {
        android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(cmd, "datePicker");
    }

    private void setAllListeners(){
        date_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (!amount.getText().toString().equals("")){
                        double number = Double.parseDouble(amount.getText().toString());
                        DecimalFormat format = new DecimalFormat("0.00");
                        String formatted = format.format(number);
                        amount.setText(formatted);
                    }

                }
            }
        });
    }



    private boolean validateName() {
        if (amount.getText().toString().trim().isEmpty()) {
            inputLayoutAmount.setError(getString(R.string.err_msg_name));
            requestFocus(amount);
            return false;
        } else {
            inputLayoutAmount.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void execute(android.app.FragmentManager cmd) {
        this.show(cmd, "");
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.amount_place:
                    validateName();
                    break;
            }
        }
    }

    public static class DatePickerFragment extends android.support.v4.app.DialogFragment
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

}
