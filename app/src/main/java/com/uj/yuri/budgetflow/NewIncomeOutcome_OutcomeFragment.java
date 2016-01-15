package com.uj.yuri.budgetflow;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
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

    public NewIncomeOutcome_OutcomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_new_outcome, container, false);
        this.helper = new DateBaseHelper(getActivity());
        hashCat = helper.selectAllCategories();
        EditText date_place =  (EditText) myFragmentView.findViewById(R.id.date_place);

        date_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder dpb = new DatePickerBuilder()
                        .setFragmentManager(getChildFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment)
                        .setTargetFragment(NewIncomeOutcome_OutcomeFragment.this);
                dpb.show();
            }
        });

        EditText time_place =  (EditText) myFragmentView.findViewById(R.id.time_place);
        time_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder tpb = new TimePickerBuilder()
                        .setFragmentManager(getChildFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment);
                tpb.show();
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



}
