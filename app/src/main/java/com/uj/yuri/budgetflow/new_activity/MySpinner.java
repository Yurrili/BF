package com.uj.yuri.budgetflow.new_activity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;

import java.util.ArrayList;

public class MySpinner extends ArrayAdapter<Category> {

    Context context;
    TextView textView;
    //int textViewResourceId;
    ArrayList<Category> arrayList;
    ImageView imgView;
    
    public MySpinner(Context context, int textViewResourceId, ArrayList<Category> arrayList) {
        super(context, textViewResourceId, arrayList );
        this.context = context;
        //this.textViewResourceId = textViewResourceId;
        this.arrayList = arrayList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getViewsy(convertView,position);
    }

    public View getView (int position, View convertView, ViewGroup parent){
        return getViewsy(convertView,position);
    }

    private View getViewsy(View convertView, int position){
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.spinner_itemlist, null);
        }
        textView = (TextView) convertView.findViewById(R.id.text_spinner);
        imgView = (ImageView) convertView.findViewById(R.id.circle_spinner);
        textView.setText(arrayList.get(position).getName());
        textView.setTextColor(getContext().getResources().getColor(R.color.material_grey_600));
        Utility.getColorBall(arrayList.get(position).getColor(), imgView,getContext());

        return  convertView;
    }


}
