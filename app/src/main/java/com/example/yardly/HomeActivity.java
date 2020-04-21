package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modelo.Usuario;

public class HomeActivity extends ActividadBaseU {

    FirebaseUser user ;
    Button b1;
    Button b2;
    TextView kmR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        kmR=findViewById(R.id.tv_kmsnum);
        if(user == null){
            Intent inte = new Intent(this, logActivity.class);
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inte);
        }
        setSupportActionBar(myToolbar);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + user.getUid());
        //kmR.setText();
        b1=findViewById(R.id.btComida);
        b2=findViewById(R.id.btRopa);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Comida = new Intent(getBaseContext(),omgreen.class);
                startActivity(Comida);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ropa = new Intent(getBaseContext(),omgreen.class);
                startActivity(ropa);
            }
        });
    }
}
