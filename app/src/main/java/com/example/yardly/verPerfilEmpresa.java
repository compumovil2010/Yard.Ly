package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class verPerfilEmpresa extends AppCompatActivity {

    Button edPerf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil_empresa);

        edPerf=findViewById(R.id.btEditarPerfilE);
        edPerf.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(),editarPerfilEmpresa.class);
                startActivity(i);
            }
        });
    }
}
