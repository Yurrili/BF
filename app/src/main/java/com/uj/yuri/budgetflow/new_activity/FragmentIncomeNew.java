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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public DateBaseHelper_ helper;
    public CheckBox infinity;
    public Button btn;

    public FragmentIncomeNew() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.helper = new DateBaseHelper(getActivity());
        myFragmentView = inflater.inflate(R.layout.fragment_new_income, container, false);
        setLays();
        setListeners();
        setRadioListeners();

        setDateTime(date_place);
        setDateTime(date_placeTo);

        return myFragmentView;
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
            //validate date
            return;
        }

        if (!validateDate()) {
            //validate date
            return;
        }
        EditText note = (EditText) myFragmentView.findViewById(R.id.note_place);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");

        if( radio_btn1.isChecked()){

            if(infinity.isChecked()){
                Income prepared_one = new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place.getText().toString(), true, getFreq(), "", 2);
                helper.insertIncome(prepared_one);
                //teraz dodac wszystkie zeszle dzienne // i dodac z wartoscia endtiem == startime
                final Calendar dd = Calendar.getInstance();
                String[] date_split = prepared_one.getStartTime().split("-");
                dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));


                Date past = new Date(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0])); // June 20th, 2010
                Date today = new Date(year, month, day); // July 24th
                Days days = Days.daysBetween(new DateTime(past), new DateTime(today));
                int d = days.getDays();
                for( int j =0; j < d; j++) {
                    dd.add(Calendar.DATE, 1);
                    helper.insertIncome(new Income(prepared_one.getId(),
                            prepared_one.getName(),
                            prepared_one.getAmount(),
                            f.format(dd.getTime()),
                            f.format(dd.getTime()),
                            true,
                            prepared_one.getDescription(),
                             "4",
                            prepared_one.getDuration()));
                }

            } else {
                //teraz dodac wszystkie zeszle dzienne //edntime = place
                Income prepared_one = new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place2_txt.getText().toString(), true, getFreq(),"",  2);
                helper.insertIncome(prepared_one);

                final Calendar dd = Calendar.getInstance();
                String[] date_split = prepared_one.getStartTime().split("-");
                dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));


                Date past = new Date(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0])); // June 20th, 2010
                Date today = new Date(year, month, day); // July 24th
                Days days = Days.daysBetween(new DateTime(past), new DateTime(today));
                int d = days.getDays();
                for( int j =0; j < d; j++) {
                    dd.add(Calendar.DATE, 1);
                    helper.insertIncome(new Income(prepared_one.getId(),
                            prepared_one.getName(),
                            prepared_one.getAmount(),
                            f.format(dd.getTime()),
                            prepared_one.getEndTime(),
                            true,
                            "4",
                            prepared_one.getDescription(),
                            prepared_one.getDuration()));
                }
            }
        } else if (radio_btn2.isChecked()){
            if(infinity.isChecked()){
                //teraz dodac wszystkie zeszle miesieczne // i dodac z wartoscia endtiem == startime
                Income prepared_one = new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place.getText().toString(), true, getFreq(),"", 2);
                helper.insertIncome(prepared_one);
                //teraz dodac wszystkie zeszle dzienne // i dodac z wartoscia endtiem == startime
                final Calendar dd = Calendar.getInstance();
                String[] date_split = prepared_one.getStartTime().split("-");
                dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));


                Date past = new Date(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0])); // June 20th, 2010
                Date today = new Date(year, month, day); // July 24th
                Months days = Months.monthsBetween(new DateTime(past), new DateTime(today));
                int d = days.getMonths();
                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    helper.insertIncome(new Income(prepared_one.getId(),
                            prepared_one.getName(),
                            prepared_one.getAmount(),
                            f.format(dd.getTime()),
                            f.format(dd.getTime()),
                            true,
                            prepared_one.getDescription(),
                            "5",
                            prepared_one.getDuration()));
                }
            } else {
                //teraz dodac wszystkie zeszle miesieczne //edntime = place
                //teraz dodac wszystkie zeszle miesieczne // i dodac z wartoscia endtiem == startime
                Income prepared_one = new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_place2_txt.getText().toString(), true,getFreq(), "",  2);
                helper.insertIncome(prepared_one);
                //teraz dodac wszystkie zeszle dzienne // i dodac z wartoscia endtiem == startime
                final Calendar dd = Calendar.getInstance();
                String[] date_split = prepared_one.getStartTime().split("-");
                dd.set(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0]));


                Date past = new Date(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0])); // June 20th, 2010
                Date today = new Date(year, month, day); // July 24th
                Months days = Months.monthsBetween(new DateTime(past), new DateTime(today));
                int d = days.getMonths();
                for( int j =0; j < d; j++) {
                    dd.add(Calendar.MONTH, 1);
                    helper.insertIncome(new Income(prepared_one.getId(),
                            prepared_one.getName(),
                            prepared_one.getAmount(),
                            f.format(dd.getTime()),
                            prepared_one.getEndTime(),
                            true,
                            "5",
                            prepared_one.getDescription(),
                            prepared_one.getDuration()));
                }
            }
        } else {
            helper.insertIncome(new Income(note.getText().toString(), amount.getText().toString(), date_place.getText().toString(), date_placeTo.getText().toString(), true, getFreq(), "", 2));
            //String nameOfIncome, String amount, String startTime, String endTime, boolean active, String desciption, int frequency, int duration)

        }

        Toast.makeText(getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(getActivity());
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
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
            date_place.setText(d + "-" + m +  "-" + year);
        }
    }

    public static class DatePickerFragmentTo extends DialogFragment
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
            date_placeTo.setText(d + "-" + m +  "-" + year);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void showDatePickerDialogTo(View v) {
        DialogFragment newFragment = new DatePickerFragmentTo();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
