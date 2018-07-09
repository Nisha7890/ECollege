package com.sunbi.e_college.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sunbi.e_college.Fragment.BbaFragment;
import com.sunbi.e_college.Fragment.BimFragment;
import com.sunbi.e_college.R;

public class CourseActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);


       final ViewPager viewPager =(ViewPager) findViewById(R.id.viewpagerforteamdetail);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("BBA"));
        tabLayout.addTab(tabLayout.newTab().setText("BIM"));


        ShopsPagerAdapter shopsPagerAdapter = new ShopsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(shopsPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public class ShopsPagerAdapter extends FragmentPagerAdapter {
        int noOfTabs;


        public ShopsPagerAdapter(FragmentManager fm, int noOfTabs) {
            super(fm);
            this.noOfTabs = noOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return new BbaFragment();
                case 1:

                    return new BimFragment();



                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return noOfTabs;
        }
    }

    public  void onBackPressed(){
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

}
