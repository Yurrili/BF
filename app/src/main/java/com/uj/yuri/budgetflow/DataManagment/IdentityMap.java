package com.uj.yuri.budgetflow.DataManagment;

import android.annotation.SuppressLint;

import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Category;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Income;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Outcome;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuri on 04.01.2017.
 */

@SuppressLint("UseSparseArrays")
public class IdentityMap {

    public static class OutcomesIdentityMap{
        private static Map<Integer, Outcome> hash = new HashMap<>();

        public OutcomesIdentityMap () {
        }

        public static Outcome isInto(String key) throws Exception {
            // could return a NULL element
            return hash.get(Integer.valueOf(key));
        }

        public static void add(Outcome cat) {
            hash.put(Integer.valueOf(cat.getId()), cat);
        }

        public static void remove(Outcome cat){
            hash.remove(Integer.valueOf(cat.getId()));
        }
    }


    public static class IncomesIdentityMap{
        private static Map<Integer, Income> hash = new HashMap<>();

        public IncomesIdentityMap () {
        }

        public static Income isInto(String key) throws Exception {
            // could return a NULL element
            return hash.get(Integer.valueOf(key));
        }

        public static void add(Income cat) {
            hash.put(Integer.valueOf(cat.getId()), cat);
        }

        public static void remove(Income cat){
            hash.remove(Integer.valueOf(cat.getId()));
        }
    }

    public static class CategoryIdentityMap{
        private static Map<Integer, Category> hash = new HashMap<>();

        public CategoryIdentityMap () {
        }

        public static Category isInto(String key) throws Exception {
            // could return a NULL element
            return hash.get(Integer.valueOf(key));
        }

        public static void add(Category cat) {
            int id = Integer.valueOf(cat.getId());
            hash.put(id, cat);
        }

        public static Map<Integer, Category> getHash() {
            return hash;
        }
    }



}
