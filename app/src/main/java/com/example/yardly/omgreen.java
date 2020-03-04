package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class omgreen extends AppCompatActivity {
    ImageButton b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omgreen);
        b1=findViewById(R.id.imageButton2);
        b2=findViewById(R.id.imageButton3);
        b3=findViewById(R.id.imageButton4);
        b4=findViewById(R.id.imageButton5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),VerInformacionEstablecimiento.class);
                startActivity(registro);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),VerInformacionEstablecimiento.class);
                startActivity(registro);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),VerInformacionEstablecimiento.class);
                startActivity(registro);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),VerInformacionEstablecimiento.class);
                startActivity(registro);
            }
        });

    }
}
