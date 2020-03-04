package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class editarPerfilU extends AppCompatActivity {

    Button direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_u);

        direccion=findViewById(R.id.dirFrec);
        direccion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(),dirFrec.class);
                startActivity(i);
            }
        });
    }
}
