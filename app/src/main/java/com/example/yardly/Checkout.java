package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Checkout extends AppCompatActivity {

    Button comprar;
    TextView direccion;
    TextView tiempo;
    TextView costoTotal;
    TextView costoEnvio;
    TextView subtotal;
    int cantidad = 0;
    Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        direccion = findViewById(R.id.direccion);
        tiempo = findViewById(R.id.tiempo);
        costoTotal = findViewById(R.id.costoTotal);
        costoEnvio = findViewById(R.id.costoEnvio);
        subtotal = findViewById(R.id.subtotal);

        pedido = (Pedido) getIntent().getSerializableExtra("Pedido");

        if (pedido != null) {
            for (int i=0; i<pedido.getProductos().size(); i++) {
                cantidad += pedido.getCantprod().get(i);
            }

            direccion.setText(pedido.getDirUsu());
            tiempo.setText(cantidad*25 + " minutos");
            subtotal.setText("$ "+ pedido.getPrecio());
            costoEnvio.setText("$ "+ ((pedido.getPrecio()*1.1)-pedido.getPrecio()));
            costoTotal.setText("$ " + (pedido.getPrecio()+((pedido.getPrecio()*1.1)-pedido.getPrecio())));

            comprar = findViewById(R.id.comprar);
            comprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Checkout.this, UsuarioEntrega.class);
                    intent.putExtra("pedido", pedido);
                    startActivity(intent);
                }
            });
        }

    }
}
