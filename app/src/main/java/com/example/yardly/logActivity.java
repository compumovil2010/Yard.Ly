package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class logActivity extends AppCompatActivity {
    Button registro;
    Button ingreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        registro = findViewById(R.id.botonRegistrar);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),Registro.class);
                startActivity(registro);
            }
        });

        ingreso = findViewById(R.id.botonIngresar);
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingreso = new Intent(getBaseContext(),infoPerfil.class);
                startActivity(ingreso);
            }
        });
    }
}
