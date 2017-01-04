package com.uj.yuri.budgetflow.MActivity;

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

import com.uj.yuri.budgetflow.EActivity.EditExpense;
import com.uj.yuri.budgetflow.EActivity.EditIncomes;
import com.uj.yuri.budgetflow.DataManagment.GatewayLogicDB;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;
import com.uj.yuri.budgetflow.NActivity.InOutActivity;
import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Income;
import com.uj.yuri.budgetflow.MActivity.ListViewAdapter.EmptyElement;
import com.uj.yuri.budgetflow.MActivity.ListViewAdapter.EntryElement;
import com.uj.yuri.budgetflow.MActivity.ListViewAdapter.HeaderElement;
import com.uj.yuri.budgetflow.MActivity.ListViewAdapter.MyAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentSpending extends Fragment {

    public static MyAdapter adapter;
    public static Context ctx;
    private ImageButton FAB;
    private static ListView list;
    public View myFragmentView;

    private GatewayLogicDB gatewayLogicDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_one_main_activity, container, false);
        gatewayLogicDB = GatewayLogicDB.getInstance(getContext());

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

    private ArrayList<EntryElement> createList(){
        ArrayList<EntryElement> array = new ArrayList<>();
        array.addAll(gatewayLogicDB.selectAllOutcomes());
        array.addAll(gatewayLogicDB.selectAllIncomes());

        ArrayList<EntryElement> list = new ArrayList<>();

        if(array.isEmpty()) {
            list.add(new EmptyElement());
            return list;
        }

        Collections.sort(array, Utility.comparator_entries );

        try {
            String date = Utility.formatData.parse(array.get(0).getStartTime()).toString();
            list.add(new HeaderElement(array.get(0)));
            list.add(array.get(0));

            for( int i = 1; i < array.size(); i++){
                if(!(Utility.formatData.parse(array.get(i).getStartTime()).toString()).equals(date)){
                    list.add(new HeaderElement(array.get(i)));
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
    static ArrayList<EntryElement> gg_KD;

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
                gatewayLogicDB.remove((Income)gg_KD.get(currentposition));
                gg_KD.remove(currentposition);
            } else if (gg_KD.get(currentposition) instanceof Outcome) {
                gatewayLogicDB.remove((Outcome)gg_KD.get(currentposition));
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
            adapter.notifyDataSetChanged();
            return true;
        }
        return true;
    }

    public void refrash(){
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
