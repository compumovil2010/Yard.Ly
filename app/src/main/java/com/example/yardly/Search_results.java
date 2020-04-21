package com.example.yardly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;

public class Search_results extends AppCompatActivity {


    private TabLayout tab_sr;
    private EditText et_search;
    private ViewPager vp_tabs;
    private  Bundle bnd_results;
    private Toolbar tb_top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        tb_top = findViewById( R.id.toolbar );
        setSupportActionBar( tb_top );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bnd_searchKey = getIntent().getBundleExtra("busqueda");
        tab_sr = findViewById( R.id.tb_searchRes );
        et_search = findViewById( R.id.et_buscador );
        vp_tabs = findViewById( R.id.vp_results );
        et_search.setText( bnd_searchKey.getString("searchKey" ) );
        tab_sr.setupWithViewPager( vp_tabs );
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    String busqueda = et_search.getText().toString().trim().toLowerCase();
                    if( !busqueda.trim().equals("") )
                    {
                        Log.i("HEY ACA", busqueda);

                        bnd_results = new Bundle();
                        bnd_results.putString( "searchKey", busqueda );
                       // finish();
                        setUpViewPager2( vp_tabs, bnd_results);
                        return true;
                    }
                    return  true;


                }

                return false;
            }
        });

        setUpViewPager( vp_tabs, bnd_searchKey );
    }

    private void setUpViewPager(ViewPager vp_tabs, Bundle bnd_searchKey) {

        Log.i("Antes del view Pager", bnd_searchKey.getString("searchKey"));
        TabViewPagerAdapter tvpa = new TabViewPagerAdapter( getSupportFragmentManager(), bnd_searchKey );
        tvpa.addFragment( new Tab_productRes(), "Productos" );
        tvpa.addFragment( new Tab_restRes(), "Restaurantes");

        vp_tabs.setAdapter( tvpa );


    }
    private void setUpViewPager2(ViewPager vp_tabs, Bundle bnd_searchKey) {

        Log.i("Antes del view Pager", bnd_searchKey.getString("searchKey"));
        TabViewPagerAdapter tvpa = new TabViewPagerAdapter( getSupportFragmentManager(), bnd_searchKey );
        tvpa.addFragment( new Tab_productRes(), "Productos" );
        tvpa.addFragment( new Tab_restRes(), "Restaurantes");

        vp_tabs.setAdapter( tvpa );


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
}
