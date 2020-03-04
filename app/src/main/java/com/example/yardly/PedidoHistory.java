package com.example.yardly;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PedidoHistory extends AppCompatActivity {

    List<Pedido> mProjection= new ArrayList<>();
    ListView mlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        mlista=findViewById(R.id.orders);
        Pedido o = new Pedido();
        o.setFecha("04/03/20202");
        o.setNombreProducto("Bowl gigante");
        o.setPrecio(30500);
        mProjection.add(o);
        mProjection.add(o);
        mProjection.add(o);
        mProjection.add(o);
        PedidosAdapter orAdapter = new PedidosAdapter(this, R.layout.itemcompra ,mProjection);
        mlista.setAdapter(orAdapter);

    }
}
