package com.example.yardly;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class VerInformacionEstablecimiento extends AppCompatActivity {
    List<Product> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_informacion_establecimiento);
        mlista=findViewById(R.id.lisProduc);
        Product p = new Product("Nombre","123456","Descripcion","sitio vejetariano");
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        ProductosAdapter poAdapter = new ProductosAdapter(this, R.layout.productos_adapter,mProjection);
        mlista.setAdapter(poAdapter);
    }
}
