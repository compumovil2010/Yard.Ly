package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Registro extends AppCompatActivity {
    Button registroFot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        registroFot=findViewById(R.id.registrarForm);
        registroFot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registroFot = new Intent(getBaseContext(),RegistroFoto.class);
                startActivity(registroFot);
            }
        });
    }
}
