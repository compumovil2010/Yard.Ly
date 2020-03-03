package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Producto extends AppCompatActivity {
    String[] mProjection;
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        mlista=findViewById(R.id.opiniones);
        mProjection = new String[]{
            "asunto",
            "comentario"
        };
        OpinionesAdapter opAdapter = new OpinionesAdapter();
        mlista.setAdapter(opAdapter);
    }
}
