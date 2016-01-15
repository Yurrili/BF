package com.uj.yuri.budgetflow;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;

import java.util.ArrayList;

public class MySpinner extends ArrayAdapter<Category> {

    Context context;
    int textViewResourceId;
    ArrayList<Category> arrayList;
    Typeface typeFaceReg;
    ImageView imgView;
    
    public MySpinner(Context context, int textViewResourceId, ArrayList<Category> arrayList) {
        super(context, textViewResourceId, arrayList );
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.arrayList = arrayList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.spinner_itemlist, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text_spinner);
        textView.setText(arrayList.get(position).getName());//after changing from ArrayList<String> to ArrayList<Object>
        imgView = (ImageView) convertView.findViewById(R.id.circle_spinner);
        setCategoryBall(arrayList.get(position).getColor());
        return convertView;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.spinner_itemlist, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text_spinner);
        textView.setText(arrayList.get(position).getName());//after changing from ArrayList<String> to ArrayList<Object>
        imgView = (ImageView) convertView.findViewById(R.id.circle_spinner);
        setCategoryBall(arrayList.get(position).getColor());
        return convertView;
    }

    private void setCategoryBall(String cat_col_id) {
        switch (cat_col_id) {
            case "0":
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle1));
                break;
            case "1":
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle2));
                break;
            case "2":
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle3));
                break;
            case "3":
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle4));
                break;
            case "4":
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle5));
                break;
            case "5":
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle6));
                break;
            case "6":
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle7));
                break;
            default:
                imgView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_circle5));
                break;
        }
    }

}
