package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras extends AppCompatActivity {
    List<Product> mProjection= new ArrayList<>();
    ListView mlista;
    Button b1;
    Product p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);
        mlista=findViewById(R.id.liscarrito);
        b1=findViewById(R.id.comfiCompra);
        p = (Product) getIntent().getSerializableExtra("producto");
        mProjection.add(p);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(v.getContext(),Calificar.class);
                registro.putExtra("producto",p);
                startActivity(registro);
            }
        });
        ProductosCarritoAdapter caAdapter = new ProductosCarritoAdapter(this, R.layout.carrito_adapter,mProjection);
        mlista.setAdapter(caAdapter);
    }
}
