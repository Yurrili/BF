package com.uj.yuri.budgetflow.EActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.DataManagment.GatewayLogicDB;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;
import com.uj.yuri.budgetflow.NActivity.DatePickers;
import com.uj.yuri.budgetflow.NActivity.MySpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class EditExpense extends AppCompatActivity {
    ArrayList<Category> cat;
    public GatewayLogicDB gatewayLogicDB;

    public Map<Integer, Category> hashCat;
    private TextInputLayout inputLayoutAmount;
    public static EditText date_place;
    private Spinner spinner;
    public EditText amount;
    public EditText note;
    private Button btn;
    private Outcome ele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_edit_outcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gatewayLogicDB = GatewayLogicDB.getInstance(getApplicationContext());
        initLayout();
        setListeners();

        setSpinnerAdapter();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            // POTENCIAL BUG
            gatewayLogicDB = new GatewayLogicDB(getApplicationContext());

            String out = extras.getString("outcome","");
            if (!out.equals("")) {
                //Outcomes
                ele = gatewayLogicDB.findOutcome(out);
                if( ele != null) {
                    date_place.setText(ele.getStartTime());
                    DecimalFormat format = new DecimalFormat("0.00");
                    String formatted = format.format(ele.getAmount().amount().doubleValue());
                    amount.setText(formatted);
                    note.setText(ele.getName());
                    //int id_cat = Integer.parseInt(ele.getCategoryId());
                    for (int i = 0; i < cat.size(); i++) {
                        if (cat.get(i).getId().equals(ele.getCategoryId())) {
                            spinner.setSelection(i);
                        }
                    }
                }



            }

        }
    }

    void setSpinnerAdapter() {
        cat = new ArrayList<>(hashCat.values());
        MySpinner adapter = new MySpinner(getApplicationContext(),
                android.R.layout.simple_spinner_item, cat);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initLayout(){

        hashCat = gatewayLogicDB.selectAllCategories();
        date_place =  (EditText) findViewById(R.id.date_place);
        spinner = (Spinner) findViewById(R.id.spinner_cat);
        note =  (EditText) findViewById(R.id.note_place);
        amount = (EditText) findViewById(R.id.amount_place);
        btn = (Button) findViewById(R.id.add_button);
        inputLayoutAmount = (TextInputLayout) findViewById(R.id.input_amount_place);
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    private void submitForm() {
        if (!validateName()) {
            return;
        }
        gatewayLogicDB.update(new Outcome(ele.getId(),note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place.getText().toString(), true, getCategoryFromForm(), 2));
        Bundle extras = getIntent().getExtras();
        extras.remove("outcome");
        NavUtils.navigateUpFromSameTask(this);
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
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
            final Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date_place.setText(DatePickers.setDAtePick(year, month, day));
        }
    }
}
