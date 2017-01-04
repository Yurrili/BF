package com.uj.yuri.budgetflow.NActivity;


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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.DataManagment.TableModule.IncomeManagment;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Income;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class FragmentIncomeNew extends Fragment {
    private View myFragmentView;
    public static EditText date_place;
    public static EditText date_placeTo;
    public EditText amount;
    public TextView date_place2_txt;
    public TextView date_place_txt;
    public ImageView date_place2_img;
    public RadioGroup gr_radio_times;
    public RadioButton radio_btn1;
    public RadioButton radio_btn2;
    public RadioButton radio_btn3;
    private TextInputLayout inputLayoutAmount;
    public CheckBox infinity;
    public Button btn;
    private EditText note ;

    public FragmentIncomeNew() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_new_income, container, false);
        setLays();
        setListeners();
        setRadioListeners();


        setDateTime(date_place);
        setDateTime(date_placeTo);

        return myFragmentView;
    }

    private void setLays(){
        date_place =  (EditText) myFragmentView.findViewById(R.id.date_place1);
        date_placeTo =  (EditText) myFragmentView.findViewById(R.id.date_place2);
        amount = (EditText) myFragmentView.findViewById(R.id.amount_place);
        gr_radio_times = (RadioGroup) myFragmentView.findViewById(R.id.radiogr);
        radio_btn1 = (RadioButton) myFragmentView.findViewById(R.id.radioButton1);
        radio_btn2 = (RadioButton) myFragmentView.findViewById(R.id.radioButton2);
        radio_btn3 = (RadioButton) myFragmentView.findViewById(R.id.radioButton3);
        date_place_txt =  (TextView) myFragmentView.findViewById(R.id.date_place_text1);
        date_place2_txt =  (TextView) myFragmentView.findViewById(R.id.date_place2_text);
        date_place2_img =  (ImageView) myFragmentView.findViewById(R.id.date_place2_img);
        inputLayoutAmount = (TextInputLayout) myFragmentView.findViewById(R.id.input_amount_place1);
        infinity =  (CheckBox) myFragmentView.findViewById(R.id.infinity);
        btn = (Button) myFragmentView.findViewById(R.id.add_button);
        note = (EditText) myFragmentView.findViewById(R.id.note_place);
    }

    private void setRadioListeners() {
        radio_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_place2_txt.setVisibility(View.VISIBLE);
                date_place_txt.setText("From");
                date_place2_img.setVisibility(View.VISIBLE);
                date_placeTo.setVisibility(View.VISIBLE);
                infinity.setVisibility(View.VISIBLE);
            }
        });

        radio_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_place_txt.setText("Date");
                infinity.setVisibility(View.INVISIBLE);
                infinity.setChecked(true);
                date_placeTo.setVisibility(View.INVISIBLE);
                date_place2_txt.setVisibility(View.INVISIBLE);
                date_place2_img.setVisibility(View.INVISIBLE);
            }
        });
        radio_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infinity.setVisibility(View.VISIBLE);
                date_placeTo.setVisibility(View.VISIBLE);
                date_place2_txt.setVisibility(View.VISIBLE);
                date_place2_img.setVisibility(View.VISIBLE);
                date_place_txt.setText("From");
            }
        });
    }

    private void setListeners(){
        amount.addTextChangedListener(new MyTextWatcher(myFragmentView));

        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkFocus(hasFocus);
            }
        });
        infinity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfinity();
            }
        });
        date_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        date_placeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogTo(v);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void checkFocus(boolean hasFocus){
        if (!hasFocus) {
            if (!amount.getText().toString().equals("")) {
                DecimalFormat format = new DecimalFormat("0.00");
                amount.setText(format.format(Double.parseDouble(amount.getText().toString())));
            }
        }
    }

    private void checkInfinity(){
        if (infinity.isChecked()) {
            date_placeTo.setEnabled(false);
        } else {
            date_placeTo.setEnabled(true);
        }
    }

    private int getFreq(){
        if(radio_btn1.isChecked()){
            return 1;
        } else if ( radio_btn2.isChecked()){
            return 2;
        } else {
            return 0;
        }
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateDate()) {
            return;
        }

        final Calendar dd = Calendar.getInstance();
        String[] date_split = date_place.getText().toString().split("-");

        Date past = new Date(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));
        Date today = new Date(dd.get(Calendar.YEAR), dd.get(Calendar.MONTH),dd.get(Calendar.DAY_OF_MONTH));

        IncomeManagment inter = new IncomeManagment(getContext());

        if( radio_btn1.isChecked()){
            inter.DailySet(date_split, dd, past, today, infinity, createPreparedOneNotInf(), createPreparedOneInf());
        } else if (radio_btn2.isChecked()) {
            inter.MontlySet(date_split, dd, past, today, infinity, createPreparedOneNotInf(), createPreparedOneInf());
        } else {
            System.err.println(date_place.getText().toString());
            inter.getIncomeGateway().insert(new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_placeTo.getText().toString(), true, getFreq(), "", 2));
        }

        NavUtils.navigateUpFromSameTask(getActivity());
    }

    private Income createPreparedOneNotInf(){
        return new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_placeTo.getText().toString(), true, getFreq(), "", 2);
    }

    private Income createPreparedOneInf(){
        return new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place.getText().toString(), true, getFreq(), "", 2);
    }


    public void setDateTime(EditText date_place){
        date_place.setText(Utility.getToday());
    }

    private boolean validateDate() {

        if (!infinity.isChecked() && !radio_btn3.isChecked()) {
                try {
                Date date1 = Utility.formatData.parse(date_place.getText().toString());
                Date date2 = Utility.formatData.parse(date_placeTo.getText().toString());

                if (date1.after(date2)) {
                    return false;
                }

                if (date1.before(date2)) {
                    return true;
                }

                if (date1.equals(date2)) {
                    return false;
                }

            }catch(ParseException ex){
                ex.printStackTrace();
                    return false;
            }
        } else {
            return true;
        }
        return false;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        public MyTextWatcher(View view) {
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

    private boolean validateName() {
        if (amount.getText().toString().trim().isEmpty()) {
            inputLayoutAmount.setError(getContext().getString(R.string.err_msg_name));
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


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickers.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void showDatePickerDialogTo(View v) {
        DialogFragment newFragment = new DatePickers.DatePickerFragmentTo();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
