package com.uj.yuri.budgetflow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.uj.yuri.budgetflow.db_managment.DateBaseHelper_;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Category;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Income;
import com.uj.yuri.budgetflow.db_managment.db_helper_objects.Outcome;
import com.uj.yuri.budgetflow.db_managment.db_main_classes.DateBaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivityBudgetFlow extends AppCompatActivity{
    private static final String PREFERENCES_NAME = "myPreferences";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    DateBaseHelper_ db;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_cup,
            R.drawable.ic_bag,
            R.drawable.ic_cat
    };
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_budget_flow);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        db = new DateBaseHelper(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        addingRegularsIncomesEveryDay();
    }

    public void addingRegularsIncomesEveryDay(){
        String textFromPreferences = preferences.getString("newDayData", "");
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(textFromPreferences.isEmpty() || textFromPreferences.equals("")) {
            SharedPreferences.Editor preferencesEditor = preferences.edit();

            preferencesEditor.putString("newDayData", day + "-" + month + "-" + year);
            preferencesEditor.apply();

        } else if (!textFromPreferences.equals(day + "-" + month + "-" + year)){
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putString("newDayData", day + "-" + month + "-" + year);
            preferencesEditor.apply();

            //dodaje wszystkie incomesy
            ArrayList<Income> list_in_d = db.selectDailyIncomes();
            ArrayList<Income> list_in_m = db.selectMontlyIncomes();

            for( int i =0; i < list_in_d.size(); i++){
                Income prepared_one = list_in_d.get(i);

                    db.insertIncome(new Income(prepared_one.getId(),
                            prepared_one.getName(),
                            prepared_one.getAmount(),
                            getToday(),
                            prepared_one.getEndTime(),
                            true,
                            "4",
                            prepared_one.getDescription(),
                            prepared_one.getDuration()));
            }

            for( int i =0; i < list_in_m.size(); i++){
                Income prepared_one = list_in_m.get(i);

                db.insertIncome(new Income(prepared_one.getId(),
                        prepared_one.getName(),
                        prepared_one.getAmount(),
                        getToday(),
                        prepared_one.getEndTime(),
                        true,
                        "4",
                        prepared_one.getDescription(),
                        prepared_one.getDuration()));
            }
        }

    }

    private String getToday() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String d, m;
        if( day < 10){
            d = "0" + day;
        } else {
            d = day + "";
        }

        if( month < 10){
            m = "0" + month;
        } else {
            m = month +"";
        }

        return d+"-"+m+"-"+year;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_budget_flow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MainActivity_Two_Fragment(), "Main");
        adapter.addFrag(new MainActivity_One_Fragment(), "Your spending");
        adapter.addFrag(new MainActivity_Three_Fragment(), "Categories");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
