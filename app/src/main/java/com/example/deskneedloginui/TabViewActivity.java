package com.example.deskneedloginui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TabViewActivity extends AppCompatActivity {
Toolbar toolbar;
TabLayout tabLayout;
ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);

        toolbar=findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new oneFragment(),"item1");
        viewPagerAdapter.addFragment(new twoFragment(),"item2");
        viewPagerAdapter.addFragment(new thirdFragments(),"item3");
        viewPagerAdapter.addFragment(new fourfragment(),"item4");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
