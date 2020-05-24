package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import Modelo.Domiciliario;

public class Checkout extends AppCompatActivity {

    public  static  final String PATH_PEDIDO = "pedido/";
    Button comprar;
    TextView direccion;
    TextView tiempo;
    TextView costoTotal;
    TextView costoEnvio;
    TextView subtotal;
    String k;
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
        comprar = findViewById(R.id.comprar);
        k = getIntent().getStringExtra("pid");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PATH_PEDIDO);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren())
                {
                    if(snap.getKey().equals( k ))
                    {
                        pedido = snap.getValue(Pedido.class);
                    }
                }
                checkout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void checkout() {
        if (pedido != null) {
            for (int i=0; i<pedido.getProductos().size(); i++) {
                cantidad += pedido.getCantprod().get(i);
            }
            Log.i("Check", "Llenando datos");
            direccion.setText(pedido.getDirUsu());
            tiempo.setText(cantidad*25 + " minutos");
            subtotal.setText("$ "+ pedido.getPrecio());
            costoEnvio.setText("$ "+ ((pedido.getPrecio()*1.1)-pedido.getPrecio()));
            costoTotal.setText("$ " + (pedido.getPrecio()+((pedido.getPrecio()*1.1)-pedido.getPrecio())));
            String d="MWyVqHxzWBPuAs6EAAA1xrvb3mX2";
            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Domiciliario.PATH_DOM + d);
            DatabaseReference  myRef2 = FirebaseDatabase.getInstance().getReference(Domiciliario.PATH_DOM + d);
            myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Domiciliario dom= dataSnapshot.getValue(Domiciliario.class);
                    dom.setPedidoActual(k);
                    myRef.setValue(dom);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

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
