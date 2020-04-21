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

import de.hdodenhof.circleimageview.CircleImageView;

public class infoPerfil extends AppCompatActivity {
    CircleImageView fotoP;
    TextView nom, km, info, infoest;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perfil);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fotoP=findViewById(R.id.perfil);
        nom=findViewById(R.id.nomp);
        km=findViewById(R.id.km);
        info=findViewById(R.id.info);
        infoest=findViewById(R.id.infoest);
        setSupportActionBar(myToolbar);
        if(user == null){
            Intent inte = new Intent(this, logActivity.class);
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inte);
        }


    }
}
