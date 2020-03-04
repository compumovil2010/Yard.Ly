package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CaltalogoEmpresa extends AppCompatActivity {
    List<Producto> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caltalogo_empresa);
        mlista = findViewById(R.id.productosCatalogo);
        Producto p = new Producto();
        p.setNombre("Nombre");
        p.setDescripcion("Descripcion");
        p.setPrecio(123456);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        CatalogoAdapter poAdapter = new CatalogoAdapter(this, R.layout.producto_catalogo,mProjection);
        mlista.setAdapter(poAdapter);
    }
}
