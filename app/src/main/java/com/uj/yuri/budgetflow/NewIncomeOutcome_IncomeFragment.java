package com.uj.yuri.budgetflow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewIncomeOutcome_IncomeFragment extends Fragment {
    private View myFragmentView;

    public NewIncomeOutcome_IncomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_new_income, container, false);

        return myFragmentView;
    }

}
