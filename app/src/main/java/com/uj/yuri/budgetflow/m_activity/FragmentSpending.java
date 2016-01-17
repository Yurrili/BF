package com.uj.yuri.budgetflow.m_activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.uj.yuri.budgetflow.new_activity.InOutActivity;
import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.EmptyL;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.Entries_list_;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.HeaderFirstL;
import com.uj.yuri.budgetflow.m_activity.view_managment_listview.MyAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentSpending extends Fragment {

    private DateBaseHelper_ db;
    private MyAdapter adapter;
    private ImageButton FAB;
    private ListView list;
    public View myFragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_one_main_activity, container, false);
        db = new DateBaseHelper(getActivity());
        initLay();
        setOnLClicks();
        creatListAdapt();
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

    private ArrayList<Entries_list_> createList(){
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

    private void creatListAdapt(){
        final ArrayList<Entries_list_> gg_KD = createList();

        adapter = new MyAdapter(getContext(), R.layout.itemlists, gg_KD);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

                final Dialog dialog = new Dialog(getContext());
                gg_KD.get(i);
                // Tutaj musze coś wymyśleć w kwestii dialogów
                if ( gg_KD.get(i) instanceof Income){
                    Toast.makeText(getActivity(), "myPos " + i + "INCOME", Toast.LENGTH_LONG).show();
                    dialog.setContentView(R.layout.dialog_edit_income);
                } else {
                    Toast.makeText(getActivity(), "myPos " + i + "OUTCOME", Toast.LENGTH_LONG).show();
                    dialog.setContentView(R.layout.dialog_edit_outcome);
                }

                dialog.setTitle("Edit you data:");

                dialog.show();

            }
        });
    }
}
