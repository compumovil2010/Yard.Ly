package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;

public class Search_results extends AppCompatActivity {


    private TabLayout tab_sr;
    private EditText et_search;
    private ViewPager vp_tabs;
    private Bundle bnd_searchKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        bnd_searchKey = getIntent().getBundleExtra("busqueda");
        tab_sr = findViewById( R.id.tb_searchRes );
        et_search = findViewById( R.id.et_buscador );
        vp_tabs = findViewById( R.id.vp_results );
        et_search.setText( bnd_searchKey.getString("searchKey" ) );
        tab_sr.setupWithViewPager( vp_tabs );
        setUpViewPager( vp_tabs );
    }

    private void setUpViewPager(ViewPager vp_tabs) {

        Log.i("Antes del view Pager", bnd_searchKey.getString("searchKey"));
        TabViewPagerAdapter tvpa = new TabViewPagerAdapter( getSupportFragmentManager(), bnd_searchKey );
        tvpa.addFragment( new Tab_productRes(), "Productos" );

        vp_tabs.setAdapter( tvpa );


    }
}
