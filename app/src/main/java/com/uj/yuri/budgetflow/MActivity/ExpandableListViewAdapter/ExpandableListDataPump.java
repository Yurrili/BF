package com.uj.yuri.budgetflow.MActivity.ExpandableListViewAdapter;



import android.content.Context;

import com.uj.yuri.budgetflow.DataManagment.TableModule.CategoryManagment;
import com.uj.yuri.budgetflow.DataManagment.Money;
import com.uj.yuri.budgetflow.DataManagment.TableModule.OutcomeManagment;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;

import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;
import com.uj.yuri.budgetflow.Utility;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExpandableListDataPump {

    public static HashMap<Map.Entry<Category, String>, List<Outcome>> getData(Context ctx) {

        HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail = new HashMap<>();
        Map<Integer, Category> cat_hash = new CategoryManagment(ctx).selectAllCategories();
        ArrayList<Outcome> out_list =  new OutcomeManagment(ctx).selectAllOutcomes();

        expandableListDetail = creatingEmptyThing (cat_hash, expandableListDetail );
        expandableListDetail = creatingListOf(expandableListDetail, out_list);
        expandableListDetail = removingEmpties(expandableListDetail);

        return expandableListDetail;
    }

    private static HashMap<Map.Entry<Category, String>, List<Outcome>> creatingEmptyThing(
            Map<Integer, Category> cat_hash,
            HashMap<Map.Entry<Category, String>, List<Outcome>> expandableListDetail)
    {
        Iterator<Map.Entry<Integer, Category>> it = cat_hash.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Category> pair = it.next();
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
                Double sum_out = 0.0;
                for(Outcome d : value)
                    sum_out += d.getAmount().amount().doubleValue();
                key.setValue(new Money(sum_out, Currency.getInstance(Locale.getDefault())).toFormattedString());
            }
        }
        return expandableListDetail;
    }
}