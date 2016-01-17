package com.uj.yuri.budgetflow.view_managment_listview;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Entries;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Yuri on 2016-01-15.
 */
public class Utility {

    public static String getToday() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String d, m;
        if (day < 10) {
            d = "0" + day;
        } else {
            d = day + "";
        }

        if (month < 10) {
            m = "0" + month;
        } else {
            m = month + "";
        }

        return d + "-" + m + "-" + year;
    }

    public static boolean chechIfDates(String date_1_f, String date_2_f) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(date_1_f);
            Date date2 = sdf.parse(date_2_f);

            if (date1.after(date2)) {
                return false;
            } else if (date1.before(date2)) {


                return chechTimeBetween(date_1_f.split("-"), date_2_f.split("-"));
            } else if (date1.equals(date2)) {
                return true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    private static boolean chechTimeBetween(String[] date_split, String[] date_split2){
        Date past = new Date(Integer.parseInt(date_split[2]), Integer.parseInt(date_split[1])-1, Integer.parseInt(date_split[0])); // June 20th, 2010
        Date today = new Date(Integer.parseInt(date_split2[2]), Integer.parseInt(date_split2[1])-1, Integer.parseInt(date_split2[0])); // June 20th, 2010
        Days days = Days.daysBetween(new DateTime(past), new DateTime(today));
        int d = days.getDays();

        if( d <= 30){
            return true;
        }
        return false;
    }

    public static void setCategoryBall(Context ctx, ImageView circle_im_cat, Outcome entry,HashMap<String, Category> hashCat){
        getColorBall(hashCat.get(((Outcome) entry).getCategoryId()).getColor(), circle_im_cat, ctx);
    }

    public static void getColorBall(String color_id, ImageView circle_im_cat, Context ctx){
        switch (color_id) {
            case "0":
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle1));
                break;
            case "1":
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle2));
                break;
            case "2":
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle3));
                break;
            case "3":
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle4));
                break;
            case "4":
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle5));
                break;
            case "5":
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle6));
                break;
            case "6":
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle7));
                break;
            default:
                circle_im_cat.setBackground(ctx.getResources().getDrawable(R.drawable.ic_circle5));
                break;
        }
    }

    public static double getOutcomesFromMonth(ArrayList<Outcome> out_list) {
        ArrayList<Double> o_list = new ArrayList<>();
        if( out_list.isEmpty()){
            return 0;
        }

        for (int i = 0; i < out_list.size(); i++) {
            if (Utility.chechIfDates(out_list.get(i).getStartTime(), getToday())) {
                o_list.add(Double.parseDouble(out_list.get(i).getAmount()));
            }
        }

        if( o_list.isEmpty()){
            return 0;
        }

        double sum_out = 0;
        for(Double d : o_list)
            sum_out += d;

        return sum_out;
    }

    public static double getIncomesFromMonth(ArrayList<Income> out_list) {
        ArrayList<Double> o_list = new ArrayList<>();
        if( out_list.isEmpty()){
            return 0;
        }

        for (int i = 0; i < out_list.size(); i++) {
            if (Utility.chechIfDates(out_list.get(i).getStartTime(), getToday())) {
                o_list.add(Double.parseDouble(out_list.get(i).getAmount()));
            }
        }

        if( o_list.isEmpty()){
            return 0;
        }

        double sum_out = 0;
        for(Double d : o_list)
            sum_out += d;

        return sum_out;
    }
}
