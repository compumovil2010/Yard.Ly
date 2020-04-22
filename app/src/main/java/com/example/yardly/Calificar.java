package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.Inet4Address;

public class Calificar extends AppCompatActivity {
    RatingBar ratingBar;
    Button calificar, addcomment;
    private FirebaseDatabase database;
    public static final String PATH_RESENA = "resena/";
    private DatabaseReference myRef;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private TextView nombreProductCalificar;
    float cantidadEstrellas = 0;
    private Resena resena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar);
        calificar=findViewById(R.id.calificarProductoBoton);
        ratingBar = findViewById(R.id.ratingBar);
        addcomment = findViewById(R.id.addComment);
        database = FirebaseDatabase.getInstance();
        nombreProductCalificar = findViewById(R.id.NombreProductCalificar);
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference(PATH_RESENA);
        user = mAuth.getCurrentUser();
        resena = new Resena();
        if(getIntent()!=null){
            if(getIntent().getSerializableExtra("producto") != null){
                Product pro= (Product)getIntent().getSerializableExtra("producto");
                resena.setUsuario(user.getEmail());
                nombreProductCalificar.setText(pro.getNomProducto());
                resena.setNomProduct(pro.getNomProducto());
            }
            if(getIntent().getSerializableExtra("resena") != null){
                resena = (Resena) getIntent().getSerializableExtra("resena");
                nombreProductCalificar.setText(resena.getNomProduct());
                ratingBar.setRating(Float.parseFloat(resena.getPuntaje()));
            }

        }
        calificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resena.setPuntaje(String.valueOf(ratingBar.getRating()));
                if(resena.getOpinion().trim().equals("")){
                    resena.setOpinion(" ");
                }
                String key = myRef.push().getKey();
                myRef = database.getReference(PATH_RESENA+key);
                myRef.setValue(resena);
                Intent registro = new Intent(getBaseContext(),Principal.class);
                startActivity(registro);
            }
        });
        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resena.setPuntaje(String.valueOf(ratingBar.getRating()));
                Intent registro = new Intent(getBaseContext(),CalificarOpinion.class);
                registro.putExtra("resena",resena);
                startActivity(registro);
            }
        });
    }
}
