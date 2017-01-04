package com.uj.yuri.budgetflow.MActivity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;
import com.uj.yuri.budgetflow.MActivity.ExpandableListViewAdapter.CustomExpandableListAdapter;
import com.uj.yuri.budgetflow.MActivity.ExpandableListViewAdapter.ExpandableListDataPump;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCategories extends Fragment {
    private View myFragmentView;
    static ExpandableListView expandableListView;
    public static ExpandableListAdapter expandableListAdapter;
    static List<Map.Entry<Category, String>> expandableListTitle;
    static HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail;
    private static Activity act;
    private static Context ctx;
    public FragmentCategories() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_three_main_activity, container, false);
        act = getActivity();
        ctx = getContext();
        expandableListView = (ExpandableListView) myFragmentView.findViewById(R.id.expandableListView);

        expandableListDetail = ExpandableListDataPump.getData(getActivity());
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        if( expandableListTitle.isEmpty()){
            List<Map.Entry<Category, String>> temp = new ArrayList<>();
            temp.add(new AbstractMap.SimpleEntry<>(new Category("", "Empty List", "1"), ""));
            expandableListAdapter = new CustomExpandableListAdapter(getContext(), temp, new HashMap<Map.Entry<Category, String>, List<Outcome>>());
        } else {
            expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        }
        expandableListView.setAdapter(expandableListAdapter);

        return myFragmentView;
    }

    public static void refrash(){
        expandableListDetail = ExpandableListDataPump.getData(act);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(ctx, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }


}
