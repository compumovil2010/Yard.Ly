package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CaltalogoEmpresa extends AppCompatActivity {
    List<Product> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caltalogo_empresa);
        mlista = findViewById(R.id.productosCatalogo);
        Product p = new Product("Nombre","123456","Descripcion","sitio vejetariano");

        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        CatalogoAdapter poAdapter = new CatalogoAdapter(this, R.layout.producto_catalogo,mProjection);
        mlista.setAdapter(poAdapter);
    }
}
