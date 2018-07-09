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

import com.sunbi.e_college.Fragment.bba_question.quest_eigthsemFragment;
import com.sunbi.e_college.Fragment.bba_question.quest_fifthsemFragment;
import com.sunbi.e_college.Fragment.bba_question.quest_firstsemFragment;
import com.sunbi.e_college.Fragment.bba_question.quest_fourthsemFragment;
import com.sunbi.e_college.Fragment.bba_question.quest_secondsemFragment;
import com.sunbi.e_college.Fragment.bba_question.quest_seventhsemFragment;
import com.sunbi.e_college.Fragment.bba_question.quest_sixthsemFragment;
import com.sunbi.e_college.Fragment.bba_question.quest_thirdsemFragment;
import com.sunbi.e_college.R;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        final ViewPager viewPager =(ViewPager) findViewById(R.id.viewpagerforquestiondetail);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_questionlayout);

        tabLayout.addTab(tabLayout.newTab().setText("1st Sem"));
        tabLayout.addTab(tabLayout.newTab().setText("2nd Sem"));
        tabLayout.addTab(tabLayout.newTab().setText("3rd Sem"));
        tabLayout.addTab(tabLayout.newTab().setText("4th Sem"));
        tabLayout.addTab(tabLayout.newTab().setText("5th Sem"));
        tabLayout.addTab(tabLayout.newTab().setText("6th Sem"));
        tabLayout.addTab(tabLayout.newTab().setText("7th Sem"));
        tabLayout.addTab(tabLayout.newTab().setText("8th Sem"));


        ShopsPagerAdapter shopsPagerAdapter  = new ShopsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

                    return new quest_firstsemFragment();
                case 1:

                    return new quest_secondsemFragment();
                case 2:

                    return new quest_thirdsemFragment();

                case 3:

                    return new quest_fourthsemFragment();

                case 4:

                    return new quest_fifthsemFragment();

                case 5:

                    return new quest_sixthsemFragment();

                case 6:

                    return new quest_seventhsemFragment();

                case 7:

                    return new quest_eigthsemFragment();


                default:
                    return null;
            }

        }
        public int getCount() {
            return noOfTabs;
        }
    }



    public void onBackPressed(){
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

}
