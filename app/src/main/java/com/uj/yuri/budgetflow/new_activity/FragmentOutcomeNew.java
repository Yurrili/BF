package com.uj.yuri.budgetflow.new_activity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FragmentOutcomeNew extends Fragment {
    private View myFragmentView;

    public DateBaseHelper_ helper;
    private HashMap<String, Category> hashCat;
    private TextInputLayout inputLayoutAmount;
    public static EditText date_place;
    private Spinner spinner;
    public EditText amount;
    public EditText note;
    private Button btn;


    public FragmentOutcomeNew() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_new_outcome, container, false);
        helper = new DateBaseHelper(getActivity());

        initLayout();
        setListeners();
        if ( !InOutActivity.edit_mode && InOutActivity.ele == null) {
            setDateTime();
        } else {
            if (InOutActivity.ele != null) {
                if (InOutActivity.ele instanceof Outcome){
                    Outcome a = (Outcome) InOutActivity.ele;
                    date_place.setText(a.getStartTime());
                    amount.setText(a.getAmount());
                    note.setText(a.getName());

                    spinner.setSelection(Integer.parseInt(a.getCategoryId()) - 1);
                }

            }
        }

        setSpinnerAdapter();

        return myFragmentView;
    }

    private void initLayout(){

        hashCat = helper.selectAllCategories();
        date_place =  (EditText) myFragmentView.findViewById(R.id.date_place);
        spinner = (Spinner) myFragmentView.findViewById(R.id.spinner_cat);
        note =  (EditText) myFragmentView.findViewById(R.id.note_place);
        amount = (EditText) myFragmentView.findViewById(R.id.amount_place);
        btn = (Button) myFragmentView.findViewById(R.id.add_button);
        inputLayoutAmount = (TextInputLayout) myFragmentView.findViewById(R.id.input_amount_place);
    }

    private void setListeners(){
        amount.addTextChangedListener(new MyTextWatcher(amount));

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
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
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    private void submitForm() {
        if (!validateName()) {
            return;
        }
        helper.insertOutcome(new Outcome(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place.getText().toString(), true, getCategoryFromForm(), 2));

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
