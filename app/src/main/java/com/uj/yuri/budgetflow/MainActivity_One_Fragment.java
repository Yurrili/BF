package com.uj.yuri.budgetflow;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;
import com.uj.yuri.budgetflow.view_managment_listview.EmptyL;
import com.uj.yuri.budgetflow.view_managment_listview.Entries_list_;
import com.uj.yuri.budgetflow.view_managment_listview.HeaderFirstL;
import com.uj.yuri.budgetflow.view_managment_listview.MyAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivity_One_Fragment extends Fragment {

    DateBaseHelper_ db;
    MyAdapter adapter;
    ImageButton FAB;

    private View myFragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_one_main_activity, container, false);

        this.db = new DateBaseHelper(getActivity());

        ListView list = (ListView) myFragmentView.findViewById(R.id.list_view);

        ArrayList<Entries_list_> gg_K = new ArrayList<>();
        ArrayList<Income> list_of_incomes  = db.selectAllIncomes();
        ArrayList<Outcome> list_of_outcomes  = db.selectAllOutcomes();

        gg_K.addAll(list_of_outcomes);
        gg_K.addAll(list_of_incomes);


        ArrayList<Entries_list_> gg_KD = null;
        try {
            gg_KD = createList(gg_K);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        adapter = new MyAdapter(getContext(), R.layout.itemlists, gg_KD);

        list.setAdapter(adapter);
        FAB = (ImageButton) myFragmentView.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(getActivity(), "Hello World", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getActivity(), NewIncomeOutcome.class);

                getActivity().startActivity(myIntent);

            }
        });

        return myFragmentView;
    }



    private ArrayList<Entries_list_> createList(ArrayList<Entries_list_> array) throws ParseException {
        // Create list to such fancy list view as you see
        ArrayList<Entries_list_> list = new ArrayList<>();

        if(array.isEmpty()) {
            list.add(new EmptyL());
            return list;
        }else{
            ArrayList<Income> listOutDaily = new ArrayList<>();
            ArrayList<Income> listOutMontly = new ArrayList<>();
            ArrayList<Income> listOutYearly = new ArrayList<>();

            for(int i=0; i < array.size(); i++) {
                if(array.get(i).getFrequency() == 1){
                    listOutDaily.add((Income)array.get(i));
                    array.remove(i);
                    i--;
                } else if(array.get(i).getFrequency() == 2){
                    listOutMontly.add((Income)array.get(i));
                    array.remove(i);
                    i--;
                } else if(array.get(i).getFrequency() == 3){
                    listOutYearly.add((Income)array.get(i));
                    array.remove(i);
                    i--;
                }
            }
        }

        Collections.sort(array, new Comparator<Entries_list_>() {
            SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            @Override
            public int compare(Entries_list_ o1, Entries_list_ o2) {
                try {
                    return f.parse(o2.getStartTime()).compareTo(f.parse(o1.getStartTime()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = parseFormat.parse(array.get(0).getStartTime()).toString();
        list.add(new HeaderFirstL(array.get(0)));
        list.add(array.get(0));
        //list.add(new first_empty_pause_TimeEntry(array.get(0)));
        // HeaderFirst - first item on list ( Without circle )
        // Header - item with header and day of the week and the dot which one end last item above
        // TimeEntry - item list simple custom layout
        for( int i = 1; i < array.size(); i++){
            if(!(parseFormat.parse(array.get(i).getStartTime()).toString()).equals(date)){
                list.add(new HeaderFirstL(array.get(i)));
                list.add(array.get(i));
               // list.add(new first_empty_pause_TimeEntry(array.get(i)));
            } else {
                list.add(array.get(i));
            }
            date = parseFormat.parse(array.get(i).getStartTime()).toString();
        }

        return list;
    }



}
