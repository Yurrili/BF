package com.uj.yuri.budgetflow.m_activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.db_managment.BackUp.ExportDataBase;
import com.uj.yuri.budgetflow.db_managment.BackUp.ImpExpUses;
import com.uj.yuri.budgetflow.db_managment.BackUp.ImportDataBase;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;
import com.uj.yuri.budgetflow.m_activity.expandable_list_view.CustomExpandableListAdapter;
import com.uj.yuri.budgetflow.m_activity.expandable_list_view.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCategories extends Fragment {
    private View myFragmentView;
    static ExpandableListView expandableListView;
    static ExpandableListAdapter expandableListAdapter;
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

        expandableListDetail = ExpandableListDataPump.getData(new DateBaseHelper(getActivity()));
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        return myFragmentView;
    }

    public static void refrash(){
        expandableListDetail = ExpandableListDataPump.getData(new DateBaseHelper(act));
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(ctx, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }


}
