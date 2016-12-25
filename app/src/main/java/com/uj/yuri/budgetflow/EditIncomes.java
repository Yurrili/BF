package com.uj.yuri.budgetflow;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelperImpl;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.new_activity.DatePickers;
import com.uj.yuri.budgetflow.new_activity.SettingIncomesToDB;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yuri on 2016-01-18.
 */
public class EditIncomes extends AppCompatActivity {
    public static EditText date_place1;
    public static EditText date_placeTo1;
    public EditText amount;
    public TextView date_place2_txt;
    public TextView date_place_txt;
    public ImageView date_place2_img;
    public RadioGroup gr_radio_times;
    public RadioButton radio_btn1;
    public RadioButton radio_btn2;
    public RadioButton radio_btn3;
    private TextInputLayout inputLayoutAmount;
    public DateBaseHelper helper;
    public CheckBox infinity;
    public Button btn;
    private EditText note ;
    private Income ele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_edit_income);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new DateBaseHelperImpl(getApplicationContext());

        setLays();
        setListeners();
        setRadioListeners();


        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            DateBaseHelper db = new DateBaseHelperImpl(getApplicationContext());

            String out = extras.getString("income","");
            if (!out.equals("")) {
                //Outcomes
                ele = db.selectIncome(out);
                date_place1.setText(ele.getStartTime());

                date_placeTo1.setText(ele.getEndTime());
                amount.setText(ele.getAmount());
                note.setText(ele.getName());
                if( ele.getFrequency() == 1 || ele.getFrequency() == 4){
                    radio_btn1.setChecked(true);
                } else if( ele.getFrequency() == 0 || ele.getFrequency() == 5){
                    radio_btn3.setChecked(true);
                } else {
                    radio_btn2.setChecked(true);
                }



            }

        }
    }


    private void setLays(){
        date_place1 =  (EditText) findViewById(R.id.date_place1);
        date_placeTo1 =  (EditText) findViewById(R.id.date_place2);
        amount = (EditText) findViewById(R.id.amount_place);
        gr_radio_times = (RadioGroup) findViewById(R.id.radiogr);
        radio_btn1 = (RadioButton) findViewById(R.id.radioButton1);
        radio_btn2 = (RadioButton) findViewById(R.id.radioButton2);
        radio_btn3 = (RadioButton) findViewById(R.id.radioButton3);
        date_place_txt =  (TextView) findViewById(R.id.date_place_text1);
        date_place2_txt =  (TextView) findViewById(R.id.date_place2_text);
        date_place2_img =  (ImageView) findViewById(R.id.date_place2_img);
        inputLayoutAmount = (TextInputLayout) findViewById(R.id.input_amount_place1);
        infinity =  (CheckBox) findViewById(R.id.infinity);
        btn = (Button) findViewById(R.id.add_button);
        note = (EditText) findViewById(R.id.note_place);
    }

    private void setRadioListeners() {
        radio_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_place2_txt.setVisibility(View.VISIBLE);
                date_place_txt.setText("From");
                date_place2_img.setVisibility(View.VISIBLE);
                date_placeTo1.setVisibility(View.VISIBLE);
                infinity.setVisibility(View.VISIBLE);
            }
        });

        radio_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_place_txt.setText("Date");
                infinity.setVisibility(View.INVISIBLE);
                infinity.setChecked(true);
                date_placeTo1.setVisibility(View.INVISIBLE);
                date_place2_txt.setVisibility(View.INVISIBLE);
                date_place2_img.setVisibility(View.INVISIBLE);
            }
        });
        radio_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infinity.setVisibility(View.VISIBLE);
                date_placeTo1.setVisibility(View.VISIBLE);
                date_place2_txt.setVisibility(View.VISIBLE);
                date_place2_img.setVisibility(View.VISIBLE);
                date_place_txt.setText("From");
            }
        });
    }

    private void setListeners(){
        amount.addTextChangedListener(new MyTextWatcher(amount));

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

        date_place1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        date_placeTo1.setOnClickListener(new View.OnClickListener() {
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
            date_placeTo1.setEnabled(false);
        } else {
            date_placeTo1.setEnabled(true);
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
        String[] date_split = date_place1.getText().toString().split("-");

        Date past = new Date(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));
        Date today = new Date(dd.get(Calendar.YEAR), dd.get(Calendar.MONTH),dd.get(Calendar.DAY_OF_MONTH));

        SettingIncomesToDB inter = new SettingIncomesToDB(helper);

        if( radio_btn1.isChecked()){
            inter.DailySetUp(date_split, dd, past, today, infinity, createPreparedOneNotInf(), createPreparedOneInf());
        } else if (radio_btn2.isChecked()) {
           inter.MontlySetUp(date_split, dd, past, today, infinity, createPreparedOneNotInf(), createPreparedOneInf());
        } else {
            helper.updateIncome(new Income(ele.getId(), note.getText().toString(), amount.getText().toString(), date_place1.getText().toString(), date_placeTo1.getText().toString(), true, getFreq() + "", "", 2));
        }

        NavUtils.navigateUpFromSameTask(this);
    }

    private Income createPreparedOneNotInf(){
        return new Income(ele.getId(),note.getText().toString(), amount.getText().toString(), date_place1.getText().toString(), date_placeTo1.getText().toString(), true, getFreq()+"", "", 2);
    }

    private Income createPreparedOneInf(){
        return new Income(ele.getId(),note.getText().toString(), amount.getText().toString(), date_place1.getText().toString(), date_place1.getText().toString(), true, getFreq()+"", "", 2);
    }


    private boolean validateDate() {

        if (!infinity.isChecked() && !radio_btn3.isChecked()) {
            try {
                Date date1 = Utility.formatData.parse(date_place1.getText().toString());
                Date date2 = Utility.formatData.parse(date_placeTo1.getText().toString());

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
            inputLayoutAmount.setError(getApplicationContext().getString(R.string.err_msg_name));
            requestFocus(amount);
            return false;
        } else {
            inputLayoutAmount.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickers.DatePickerFragmentE();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void showDatePickerDialogTo(View v) {
        DialogFragment newFragment = new DatePickers.DatePickerFragmentToE();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}