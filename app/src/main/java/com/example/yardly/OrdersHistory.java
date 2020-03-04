package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OrdersHistory extends AppCompatActivity {

    List<Orden> mProjection= new ArrayList<>();
    ListView mlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        mlista=findViewById(R.id.orders);
        Orden o = new Orden();
        o.setFecha("04/03/20202");
        o.setNombreProducto("Bowl gigante");
        o.setPrecio(30500);
        mProjection.add(o);
        mProjection.add(o);
        mProjection.add(o);
        mProjection.add(o);
        OrdenesAdapter orAdapter = new OrdenesAdapter(this, R.layout.itemcompra ,mProjection);
        mlista.setAdapter(orAdapter);

    }
}
