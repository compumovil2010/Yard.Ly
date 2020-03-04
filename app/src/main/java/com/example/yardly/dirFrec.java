package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class dirFrec extends AppCompatActivity {
    List<LugarEntrega> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir_frec);
        mlista=findViewById(R.id.listaDir);
        LugarEntrega l;
        for(int i=0; i<5;i++)
        {
            l=new LugarEntrega();
            l.setDireccion("Cra X # Y Z");
            mProjection.add(l);
        }
        DireccionesAdapter dAdapter = new DireccionesAdapter(this, R.layout.verdire,mProjection);
        mlista.setAdapter(dAdapter);
    }
}
