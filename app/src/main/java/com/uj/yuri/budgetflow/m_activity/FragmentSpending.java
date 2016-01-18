package com.uj.yuri.budgetflow.m_activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.uj.yuri.budgetflow.EditExpense;
import com.uj.yuri.budgetflow.EditIncomes;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.new_activity.InOutActivity;
import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.EmptyL;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.Entries_list_;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.HeaderFirstL;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.MyAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentSpending extends Fragment {

    private static DateBaseHelper_ db;
    public static MyAdapter adapter;
    public static Context ctx;
    private ImageButton FAB;
    private static ListView list;
    public View myFragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_one_main_activity, container, false);
        db = new DateBaseHelper(getActivity());
        initLay();
        setOnLClicks();
        creatListAdapt();
        ctx = getContext();
        return myFragmentView;
    }

    private void initLay(){
        list = (ListView) myFragmentView.findViewById(R.id.list_view);
        FAB = (ImageButton) myFragmentView.findViewById(R.id.imageButton);
    }

    private void setOnLClicks(){
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), InOutActivity.class);
                getActivity().startActivity(myIntent);
            }
        });
    }

    private static ArrayList<Entries_list_> createList(){
        ArrayList<Entries_list_> array = new ArrayList<>();
        array.addAll(db.selectAllOutcomes());
        array.addAll(db.selectAllIncomes());

        ArrayList<Entries_list_> list = new ArrayList<>();

        if(array.isEmpty()) {
            list.add(new EmptyL());
            return list;
        }

        Collections.sort(array, Utility.comparator_entries );

        try {
            String date = Utility.formatData.parse(array.get(0).getStartTime()).toString();
            list.add(new HeaderFirstL(array.get(0)));
            list.add(array.get(0));

            for( int i = 1; i < array.size(); i++){
                if(!(Utility.formatData.parse(array.get(i).getStartTime()).toString()).equals(date)){
                    list.add(new HeaderFirstL(array.get(i)));
                    list.add(array.get(i));
                } else {
                    list.add(array.get(i));
                }
                date = Utility.formatData.parse(array.get(i).getStartTime()).toString();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return list;
    }
    static int currentposition;
    static ArrayList<Entries_list_> gg_KD;

    public void creatListAdapt(){
        gg_KD = createList();

        adapter = new MyAdapter(getContext(), R.layout.itemlists, gg_KD);
        list.setAdapter(adapter);

        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                // TODO Auto-generated method stub
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                currentposition = info.position;
                menu.setHeaderTitle("Choose");
                menu.add(0, v.getId(), 0, "Delete ");
                menu.add(0, v.getId(), 0, "Edit ");
            }

        });

    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        if (item.getTitle() == "Delete ") {

            if (gg_KD.get(currentposition) instanceof Income){
                db.removeIncome((Income)gg_KD.get(currentposition));
                gg_KD.remove(currentposition);
            } else if (gg_KD.get(currentposition) instanceof Outcome) {
                db.removeOutcome((Outcome)gg_KD.get(currentposition));
                gg_KD.remove(currentposition);
            }

            creatListAdapt();
            MainActivity.ref();
        } else {
            if (gg_KD.get(currentposition) instanceof Income){
                Intent myIntent = new Intent(getActivity(), EditIncomes.class);
                myIntent.putExtra("income",gg_KD.get(currentposition).getId());
                getActivity().startActivity(myIntent);

            } else if (gg_KD.get(currentposition) instanceof Outcome) {
               // db.removeOutcome((Outcome)gg_KD.get(currentposition));
                Intent myIntent = new Intent(getActivity(), EditExpense.class);
                myIntent.putExtra("outcome",gg_KD.get(currentposition).getId());
                getActivity().startActivity(myIntent);
            }
            //gg_KD.remove(currentposition);
            creatListAdapt();
            MainActivity.ref();
            return true;
        }
        return true;
    }

    public static void refrash(){
        gg_KD = createList();
        adapter = new MyAdapter(ctx, R.layout.itemlists, gg_KD);
        list.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                currentposition = info.position;
                menu.setHeaderTitle("Choose");
                menu.add(0, v.getId(), 0, "Delete ");
                menu.add(0, v.getId(), 0, "Edit ");
            }

        });
    }


}
