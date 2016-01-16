package com.uj.yuri.budgetflow;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class NewIncomeOutcome_OutcomeFragment extends Fragment {
    private View myFragmentView;

    public DateBaseHelper_ helper;
    private HashMap<String, Category> hashCat;
    private TextInputLayout inputLayoutAmount;
    public static EditText date_place;
    private Spinner spinner;
    public EditText amount;
    public EditText note;




    public NewIncomeOutcome_OutcomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_new_outcome, container, false);
        this.helper = new DateBaseHelper(getActivity());
        hashCat = helper.selectAllCategories();
        date_place =  (EditText) myFragmentView.findViewById(R.id.date_place);

        note =  (EditText) myFragmentView.findViewById(R.id.note_place);
        amount = (EditText) myFragmentView.findViewById(R.id.amount_place);

        inputLayoutAmount = (TextInputLayout) myFragmentView.findViewById(R.id.input_amount_place);
        amount.addTextChangedListener(new MyTextWatcher(amount));

        Button btn = (Button) myFragmentView.findViewById(R.id.add_button);
        date_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        setDateTime();
        spinner = (Spinner) myFragmentView.findViewById(R.id.spinner_cat);
        setSpinnerAdapter();

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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
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

        date_place.setText(d + "-" + md + "-" + year);

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



    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }
        helper.insertOutcome(new Outcome(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place.getText().toString(), true, getCategoryFromForm(), 2));

        Toast.makeText(getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(getActivity());
    }

    private String getCategoryFromForm() {
        String text = ((Category)spinner.getSelectedItem()).getId();
        return text;
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


}
