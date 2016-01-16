package com.uj.yuri.budgetflow.view_managment_listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;


public class MyAdapter extends ArrayAdapter<Entries_list_> {

    public DateBaseHelper_ helper;
    private HashMap<String, Category> hashCat;
    private LayoutInflater vi;
    private Entries_list_ entry;
    private View v;
    private TextView amount;
    private TextView name_of;
    private TextView category;
    private TextView time_hours;
    private TextView day_of_week ;
    private ImageView vie_circle;
    private ImageView circle_im_cat;
    private ImageView note_img;



    public MyAdapter(Context context, int resource, List<Entries_list_> items) {
        super(context, resource, items);
            helper = new DateBaseHelper(getContext());
            hashCat = helper.selectAllCategories();
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {

        if (getItem(position) instanceof HeaderFirstL)
            return 0;
        else {
            if (getItem(position) instanceof Income)
                return 1;
            else if ( getItem(position) instanceof Outcome)
                    return 2;
                else
                    return 3;

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        v = convertView;
        entry = getItem(position);
        vi = LayoutInflater.from(getContext());

        if (entry != null)
        {
            if ( !(entry instanceof EmptyL) && !(entry instanceof HeaderFirstL) ) {
                IfNormal();
            }

            if ( entry instanceof HeaderFirstL) {
                IfHeader();
            }

        }
        return v;
    }

    private void IfNormal(){

        if (v == null) {
            v = vi.inflate(R.layout.itemlists, null);
        }

        findViewsNormal();
        setDataToElementOfList();
    }

    private void IfHeader(){
        // Checking if this entry is first of whole list
        if( v == null){
            v =  vi.inflate(R.layout.itemlist_first_header, null);
            v.setClickable(false);
            v.setEnabled(false);
        }

        findViewsHeader();
        time_hours.setText(entry.getStartTime());
        setDay_of_week();
    }


    private void findViewsNormal(){
        amount = (TextView) v.findViewById(R.id.amount);
        name_of = (TextView) v.findViewById(R.id.name_of);
        time_hours = (TextView) v.findViewById(R.id.time_hours);
        category = (TextView) v.findViewById(R.id.categorie);
        circle_im_cat = (ImageView) v.findViewById(R.id.circle_im_cat);
        note_img = (ImageView) v.findViewById(R.id.note_img);
    }

    private void findViewsHeader(){
        day_of_week = (TextView) v.findViewById(R.id.text_day_of_week);
        time_hours = (TextView) v.findViewById(R.id.time_hours);
        vie_circle = (ImageView) v.findViewById(R.id.circle_of_day);
    }


    private void setDataToElementOfList(){
        // Data set to simple item list


        if (amount != null)
            setAmount();


        if (name_of != null && entry.whatAmI()) {
            if( entry.getName().equals("")){
                note_img.setVisibility(View.INVISIBLE);
                name_of.setText(entry.getName());
            } else {
                note_img.setVisibility(View.VISIBLE);
                name_of.setText(entry.getName());
            }
        }



        if (category != null && entry.whatAmI()) {
            setCategoryBall();
            category.setText(hashCat.get(((Outcome) entry).getCategoryId()).getName());
        }

        if(time_hours != null && !entry.whatAmI()) {
            if (entry.getFrequency() == 0) {
                time_hours.setText("");
            } else if (entry.getFrequency() == 1) {
                time_hours.setText("daily");
            } else if (entry.getFrequency() == 2) {
                time_hours.setText("monthly");
            } else {
                time_hours.setText("yearly");
            }
        }else{
            time_hours.setText("");
        }

        if ( entry instanceof Income && !(entry.whatAmI())) {
            amount.setTextColor(getContext().getResources().getColor(R.color.greeno));
            circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_note));
            category.setText(entry.getName());
            name_of.setVisibility(View.INVISIBLE);
            note_img.setVisibility(View.INVISIBLE);
        }
    }

    private void setAmount(){
        if( entry.whatAmI())
            amount.setText(" - " + entry.getAmount() + "  PLN");
        else
            amount.setText( entry.getAmount() + "  PLN");
    }



    private void setDay_of_week(){
        String date = entry.getStartTime();
        String[] separated = date.split("-");

        Log.d("calendar", ""+ (Integer.parseInt(separated[2])) +" "+ (Integer.parseInt(separated[1]) - 1) +" "+  (Integer.parseInt(separated[0]) - 1));
        Calendar calendar = new GregorianCalendar(Integer.parseInt(separated[2]), Integer.parseInt(separated[1]) - 1, Integer.parseInt(separated[0]) - 1); // Note that Month value is 0-based. e.g., 0 for January.
        //year //month //day
       int reslut = calendar.get(Calendar.DAY_OF_WEEK);
        String day = getContext().getResources().getStringArray(R.array.weeksday)[reslut - 1];
        day_of_week.setText(day);

        switch (day) {
            case "Sunday":
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle1));
                break;
            case "Monday":
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle2));
                break;
            case "Tuesday":
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle3));
                break;
            case "Wednesday":
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle4));
                break;
            case "Thursday":
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle5));
                break;
            case "Friday":
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle6));
                break;
            case "Saturday":
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle7));
                break;
            default:
                vie_circle.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle5));
                break;
        }
    }


    private void setCategoryBall(){
        switch (hashCat.get(((Outcome) entry).getCategoryId()).getColor()) {
            case "0":
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle1));
                break;
            case "1":
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle2));
                break;
            case "2":
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle3));
                break;
            case "3":
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle4));
                break;
            case "4":
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle5));
                break;
            case "5":
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle6));
                break;
            case "6":
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle7));
                break;
            default:
                circle_im_cat.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle5));
                break;
        }
    }
}