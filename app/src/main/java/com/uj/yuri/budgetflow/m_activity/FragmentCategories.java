package com.uj.yuri.budgetflow.m_activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;
import com.uj.yuri.budgetflow.m_activity.expandable_list_view.CustomExpandableListAdapter;
import com.uj.yuri.budgetflow.m_activity.expandable_list_view.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCategories extends Fragment {
    private View myFragmentView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<Map.Entry<Category, String>> expandableListTitle;
    HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail;
    public FragmentCategories() {
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
