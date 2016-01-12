package com.uj.yuri.budgetflow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DataBaseHelper;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.Income_;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivity_One_Fragment extends Fragment {

    DateBaseHelper_ db;
    ListView list;
    View view;
    ArrayAdapter<String> adapter;
    ArrayList<Income_> list_of_incomes ;
    private View myFragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_one_main_activity, container, false);

        this.db = new DataBaseHelper(getActivity());

        list_of_incomes = db.selectAllIncomes();

        ListView list = (ListView) myFragmentView.findViewById(R.id.listView_One_Fragment);
        ArrayList<String> gg_K = new ArrayList<>();
        for( int i =0; i < list_of_incomes.size();i++){
            gg_K.add(list_of_incomes.get(i).getNameOfIncome());
        }

        adapter = new ArrayAdapter<>(getContext(),R.layout.row, gg_K);

        list.setAdapter(adapter);


        return myFragmentView;


    }
}
