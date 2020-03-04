package com.example.yardly;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PedidosActivos extends AppCompatActivity {
    List<Producto> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_activos);
        mlista=findViewById(R.id.lisProduc);
        Producto p = new Producto();
        p.setNombre("Nombre");
        p.setDescripcion("Descripcion");
        p.setPrecio(123456);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        ProductosAdapter poAdapter = new ProductosAdapter(this, R.layout.pedidos_activos_adapter,mProjection);
        mlista.setAdapter(poAdapter);
    }
}
