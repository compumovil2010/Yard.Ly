package com.example.yardly;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
                /*Intent registroFot = new Intent(getBaseContext(),CarritoCompras.class);
                startActivity(registroFot);*/
            }
        });
    }
}
