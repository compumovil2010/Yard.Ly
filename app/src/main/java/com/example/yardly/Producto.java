package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Producto extends AppCompatActivity {
    List<String> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        mlista=findViewById(R.id.opiniones);
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        mProjection.add("Asunto");
        mProjection.add("Opinion");
        OpinionesAdapter opAdapter = new OpinionesAdapter(this, R.layout.opiniones,mProjection);
        mlista.setAdapter(opAdapter);
    }
}
