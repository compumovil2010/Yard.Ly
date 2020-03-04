package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class dirFrec extends AppCompatActivity {
    List<String> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir_frec);
        mlista=findViewById(R.id.listaDir);
        mProjection.add("Direccion");
        mProjection.add("Direccion");
        mProjection.add("Direccion");
        mProjection.add("Direccion");
        DireccionesAdapter dAdapter = new DireccionesAdapter(this, R.layout.verdire,mProjection);
        mlista.setAdapter(dAdapter);
    }
}
