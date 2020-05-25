package com.example.yardly;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;





public class Principal extends ActividadBaseU {

    private TabLayout tab_navhome;
    private ViewPager vp_home;
    private Toolbar tb_opciones;
    public static String CHANNEL_ID= "llegoDom";
    @Override
    public void onBackPressed() {
    }

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent inte = new Intent(this, logActivity.class);
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inte);
        }
        createNotificationChannels();
        tb_opciones = findViewById( R.id.toolbar );
        tab_navhome = findViewById(R.id.tab_navhome);
        vp_home = findViewById(R.id.vp_home);
        setSupportActionBar( tb_opciones );
        tab_navhome.setupWithViewPager( vp_home );
        setUpViewPager( vp_home );
    }

    private void createNotificationChannels() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.llegoDomicilioC);
            String description = getString(R.string.DescripcionDomicilioC);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
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