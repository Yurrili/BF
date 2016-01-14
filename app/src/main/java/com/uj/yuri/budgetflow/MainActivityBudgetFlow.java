package com.uj.yuri.budgetflow;

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

import java.util.ArrayList;
import java.util.List;


public class MainActivityBudgetFlow extends AppCompatActivity{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_calendar,
            R.drawable.ic_champ,
            R.drawable.ic_gift
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_budget_flow);


        DateBaseHelper_ db = new DateBaseHelper(getApplicationContext());

//        db.insertCategory(new Category("Domowe"));
//        db.insertCategory(new Category("Dodatkowe"));
//        db.insertCategory(new Category("Przyjemnosci"));
//
//        db.insertOutcome(new Outcome("Na dom", "125690", "12-07-2015", "14-07-2015", true, "1", 1));
//        db.insertOutcome(new Outcome("Jedzenie", "1200", "12-07-2015", "14-07-2015", true, "2", 1));
////        db.insertOutcome(new Outcome("Druugs", "1", "13-07-2015", "14-07-2015", true, "2", 1));
////        db.insertOutcome(new Outcome("Kino", "2", "13-07-2015", "14-07-2015", true, "2", 1));
////        db.insertOutcome(new Outcome("Autobusy", "1", "12-07-2015", "14-07-2015", true, "1", 1));
////        db.insertOutcome(new Outcome("Silownia", "3","12-07-2015", "14-07-2015", true, "1", 1));
//
//        db.insertIncome(new Income("Na dom", "1400", "11-07-2015", "14-07-2015", true, "1",1, 1));
//        db.insertIncome(new Income("Jedzenie", "350", "16-07-2015", "14-07-2015", true, "1",1, 1));
//        db.insertIncome(new Income("Druugs", "15", "12-07-2015", "14-07-2015", true, "2",3, 1));
//        db.insertIncome(new Income("Kino", "70", "18-07-2015", "14-07-2015", true, "2",7, 1));
//        db.insertIncome(new Income("Autobusy", "35", "18-07-2015", "14-07-2015", true, "1",6, 1));
//        db.insertIncome(new Income("Silownia", "120","12-07-2015", "14-07-2015", true, "1",4, 1));
//
//        db.selectAllCategories();
//        db.selectAllOutcomes();
//        db.selectAllIncomes();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
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
        adapter.addFrag(new MainActivity_Three_Fragment(), "THREE");
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
