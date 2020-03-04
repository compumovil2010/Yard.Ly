package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

public class editarPerfilU extends ActividadBaseU {

    Button direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_u);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        direccion=findViewById(R.id.dirFrec);
        direccion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(),dirFrec.class);
                startActivity(i);
            }
        });
    }

}
