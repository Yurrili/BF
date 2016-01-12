package com.uj.yuri.budgetflow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity_Three_Fragment extends Fragment {
    private View myFragmentView;

    public MainActivity_Three_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_three_main_activity, container, false);

        return myFragmentView;
    }

}
