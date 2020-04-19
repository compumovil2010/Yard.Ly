package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class logActivity extends AppCompatActivity {
    TextView registro;
    Button ingreso;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        registro = findViewById(R.id.register);
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
                Intent ingreso = new Intent(getBaseContext(),Principal.class);
                startActivity(ingreso);
            }
        });
    }
}
