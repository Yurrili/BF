package com.uj.yuri.budgetflow.MActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.uj.yuri.budgetflow.R;
import com.uj.yuri.budgetflow.Utility;
import com.uj.yuri.budgetflow.DataManagment.BackUp.ExportDataBase;
import com.uj.yuri.budgetflow.DataManagment.BackUp.ImpExpUses;
import com.uj.yuri.budgetflow.DataManagment.BackUp.ImportDataBase;
import com.uj.yuri.budgetflow.DataManagment.GatewayLogicDB;
import com.uj.yuri.budgetflow.DataManagment.ObjectsDO.Income;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "myPreferences";
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_cup,
            R.drawable.ic_bag,
            R.drawable.ic_cat
    };
    private SharedPreferences preferences;

    private GatewayLogicDB gatewayLogicDB;
    private ActionBarDrawerToggle drawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_budget_flow);
        JodaTimeAndroid.init(this);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        gatewayLogicDB = GatewayLogicDB.getInstance(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        addingRegularsIncomesEveryDay();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);
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
            ArrayList<Income> list_in_d = gatewayLogicDB.selectDailyIncomes();
            ArrayList<Income> list_in_m = gatewayLogicDB.selectMontlyIncomes();

            for( int i =0; i < list_in_d.size(); i++){
                Income prepared_one = list_in_d.get(i);

                    gatewayLogicDB.insert(new Income(prepared_one.getId(),
                            prepared_one.getName(),
                            String.valueOf(prepared_one.getAmount().amount().doubleValue()),
                            getToday(),
                            prepared_one.getEndTime(),
                            true,
                            "4",
                            prepared_one.getDescription(),
                            prepared_one.getDuration()));
            }

            for( int i =0; i < list_in_m.size(); i++){
                Income prepared_one = list_in_m.get(i);

                gatewayLogicDB.insert(new Income(prepared_one.getId(),
                        prepared_one.getName(),
                        String.valueOf(prepared_one.getAmount().amount().doubleValue()),
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
        return Utility.getToday();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity_budget_flow, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        ImpExpUses impexpus = new ImpExpUses();
        switch(menuItem.getItemId()) {
            case R.id.import_db:
                impexpus.setImpExpWayStr(new ImportDataBase());
                Toast.makeText(getApplicationContext(),"DB imported", Toast.LENGTH_SHORT).show();
                break;
            case R.id.export_db:
                impexpus.setImpExpWayStr(new ExportDataBase());
                Toast.makeText(getApplicationContext(),"DB exported", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_new_expense_income:
                break;
            default:
                break;
        }

        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    public static void ref(){
//        FragmentSpending.refrash();
//        FragmentCategories.refrash();
//        FragmentMain.refash();
    }



    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentMain(), "Main");
        adapter.addFrag(new FragmentSpending(), "Your spending");
        adapter.addFrag(new FragmentCategories(), "Categories");
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
