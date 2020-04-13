package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;

public class Search_results extends AppCompatActivity {


    private TabLayout tab_sr;
    private EditText et_search;
    private ViewPager vp_tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        tab_sr = findViewById( R.id.tb_searchRes );
        et_search = findViewById( R.id.et_buscador );
        vp_tabs = findViewById( R.id.vp_results );

        tab_sr.setupWithViewPager( vp_tabs );
        setUpViewPager( vp_tabs );
    }

    private void setUpViewPager(ViewPager vp_tabs) {

        TabViewPagerAdapter tvpa = new TabViewPagerAdapter( getSupportFragmentManager() );

        tvpa.addFragment( new Tab_productRes(), "Productos" );

        vp_tabs.setAdapter( tvpa );


    }
}
