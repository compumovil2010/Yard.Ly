package com.example.yardly;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PedidosActivos extends AppCompatActivity {
    List<Pedido> mProjection= new ArrayList<>();
    ListView mlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_activos);
        mlista=findViewById(R.id.lispedidos);
        Pedido p = new Pedido();
        p.setNombreProducto("Nombre");
        p.setFecha("12/12/12");
        p.setPrecio(123456);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        mProjection.add(p);
        PedidosActivosAdapter poAdapter = new PedidosActivosAdapter(this, R.layout.pedidos_activos_adapter,mProjection);
        mlista.setAdapter(poAdapter);
    }
}
