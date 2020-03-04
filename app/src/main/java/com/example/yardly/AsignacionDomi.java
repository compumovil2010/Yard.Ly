package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AsignacionDomi extends ActividadBaseE {
    List<Pedido> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_domi);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbare);
        setSupportActionBar(myToolbar);
        mlista=findViewById(R.id.listPD);
        Pedido p = new Pedido();
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        AsginacionAdapter adAdapter = new AsginacionAdapter(this, R.layout.asignacionadap,mProjection);
        mlista.setAdapter(adAdapter);
    }
}
