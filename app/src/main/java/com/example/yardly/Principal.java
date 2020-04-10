package com.example.yardly;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;





public class Principal extends AppCompatActivity {

    private TabLayout tab_navhome;
    private ViewPager vp_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        tab_navhome = findViewById(R.id.tab_navhome);
        vp_home = findViewById(R.id.vp_home);

        tab_navhome.setupWithViewPager( vp_home );
        setUpViewPager( vp_home );
    }

    private void setUpViewPager(ViewPager vp_home)
    {
        TabViewPagerAdapter tvpa = new TabViewPagerAdapter( getSupportFragmentManager() );
        tvpa.addFragment( new Tab_home(), "Home" );
        tvpa.addFragment( new Tab_omg(), "OMG!" );
        tvpa.addFragment( new Tab_profile(), "Perfil" );
        tvpa.addFragment( new Tab_deliv(), "Deliv" );
        tvpa.addFragment( new Tab_shop(), "Shop" );
        vp_home.setAdapter( tvpa );
    }

}