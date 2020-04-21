package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.Inet4Address;

public class Calificar extends AppCompatActivity {
    RatingBar ratingBar;
    Button calificar, addcomment;
    float cantidadEstrellas = 0;
    private Resena resena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar);
        calificar=findViewById(R.id.calificarProductoBoton);
        ratingBar = findViewById(R.id.ratingBar);
        addcomment = findViewById(R.id.addComment);
        resena = new Resena();
        if(getIntent()!=null){
            if(getIntent().getStringExtra("comentario") != null){
                ratingBar.setRating(getIntent().getFloatExtra("calificacion",0));
                resena.setOpinion(getIntent().getStringExtra("comentario"));
                Toast.makeText(getBaseContext(),resena.getOpinion()+resena.getPuntaje(),Toast.LENGTH_SHORT).show();
            }
        }
        calificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadEstrellas = ratingBar.getRating();
                resena.setPuntaje((int)cantidadEstrellas);
                Intent registro = new Intent(getBaseContext(),Principal.class);
                startActivity(registro);
            }
        });
        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),CalificarOpinion.class);
                cantidadEstrellas = ratingBar.getRating();
                registro.putExtra("calificacion",cantidadEstrellas);
                startActivity(registro);
            }
        });
    }
}
