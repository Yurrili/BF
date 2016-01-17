package com.uj.yuri.budgetflow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;
import com.uj.yuri.budgetflow.view_managment_listview.CustomExpandableListAdapter;
import com.uj.yuri.budgetflow.view_managment_listview.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_Three_Fragment extends Fragment {
    private View myFragmentView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<Map.Entry<Category, String>> expandableListTitle;
    HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail;
    public MainActivity_Three_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_three_main_activity, container, false);
        expandableListView = (ExpandableListView) myFragmentView.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(new DateBaseHelper(getActivity()));
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        return myFragmentView;
    }

}
