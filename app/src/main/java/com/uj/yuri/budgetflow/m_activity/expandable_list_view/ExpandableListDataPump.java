package com.uj.yuri.budgetflow.m_activity.expandable_list_view;



import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;

import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.Utility;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExpandableListDataPump {

    public static HashMap<Map.Entry<Category, String>, List<Outcome>> getData(DateBaseHelper_ db) {

        HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail = new HashMap<>();
        HashMap<String, Category> cat_hash = db.selectAllCategories();
        ArrayList<Outcome> out_list =  db.selectAllOutcomes();

        expandableListDetail = creatingEmptyThing (cat_hash, expandableListDetail );
        expandableListDetail = creatingListOf(expandableListDetail, out_list);
        expandableListDetail = removingEmpties(expandableListDetail);

        return expandableListDetail;
    }

    private static HashMap<Map.Entry<Category, String>, List<Outcome>> creatingEmptyThing(
            HashMap<String, Category> cat_hash,
            HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail)
    {
        Iterator<Map.Entry<String, Category>> it = cat_hash.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Category> pair = it.next();
            Map.Entry<Category, String> pairKey = new AbstractMap.SimpleEntry<Category, String>( pair.getValue(),"0");
            expandableListDetail.put(pairKey, new ArrayList<Outcome>());
        }
        return expandableListDetail;
    }

    private static HashMap<Map.Entry<Category, String>, List<Outcome>> creatingListOf(
            HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail,
            ArrayList<Outcome> out_list)
    {

        String data = Utility.getToday();

        for( int i =0; i < out_list.size(); i++){
            if(Utility.chechIfDates(out_list.get(i).getStartTime(), data)){
                Iterator<Map.Entry<Map.Entry<Category, String>, List<Outcome>>> iftg = expandableListDetail.entrySet().iterator();
                while (iftg.hasNext()) {
                    Map.Entry<Map.Entry<Category, String>, List<Outcome>> e = iftg.next();
                    Map.Entry<Category, String> key = e.getKey();
                    if (key.getKey().getId().equals(out_list.get(i).getCategoryId())) {
                        e.getValue().add(out_list.get(i));
                    }
                }

            }
        }
        return expandableListDetail;
    }

    private static HashMap<Map.Entry<Category, String>, List<Outcome>> removingEmpties(
            HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail)
    {
        Iterator<Map.Entry<Map.Entry<Category, String>, List<Outcome>>> ift = expandableListDetail.entrySet().iterator();
        while (ift.hasNext()) {
            Map.Entry<Map.Entry<Category, String>, List<Outcome>> e = ift.next();
            Map.Entry<Category, String> key = e.getKey();

            List<Outcome> value = e.getValue();
            if (value.isEmpty()) {
                ift.remove();
            } else {
                double sum_out = 0;
                for(Outcome d : value)
                    sum_out += Double.parseDouble(d.getAmount());
                key.setValue(sum_out+"");
            }
        }
        return expandableListDetail;
    }
}