package com.example.shivani.aspire1;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    String[] titles={"Camera Info","Two"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleMargin(20, 0, 0, 0);
        viewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        this.addPages();
        setTabTitle();
    }
    public void addPages(){
        MyPagerAdapter adapter=new MyPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(new TabOne());
        adapter.addFragment(new TabTwo());
        viewPager.setAdapter(adapter);
    }

    private void setTabTitle() {
        tabLayout.getTabAt(0).setText(titles[0]);
        tabLayout.getTabAt(1).setText(titles[1]);
    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }


}
