package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modelo.Domiciliario;
import Modelo.Usuario;

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
    private FirebaseUser user;
    private DatabaseReference myRef2;

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
         user = FirebaseAuth.getInstance().getCurrentUser();
         myRef2 = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + user.getUid());
        k = getIntent().getStringExtra("pid");
        pedido = (Pedido)getIntent().getSerializableExtra("pedido");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        checkout();



    }

    private void checkout() {
        if (pedido != null) {
            for (int i=0; i<pedido.getProductos().size(); i++) {
                cantidad += pedido.getCantprod().get(i);
            }
            Log.i("Check", "Llenando datos");
            direccion.setText(pedido.getDirUsu());
            tiempo.setText(cantidad*15 + " minutos");
            subtotal.setText("$ "+ pedido.getPrecio());
            costoEnvio.setText("$ "+ ((pedido.getPrecio()*1.1)-pedido.getPrecio()));
            costoTotal.setText("$ " + (pedido.getPrecio()+((pedido.getPrecio()*1.1)-pedido.getPrecio())));
            comprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(PATH_PEDIDO+k);
                    myRef.setValue(pedido);

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + user.getUid());

                                    myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Usuario us= dataSnapshot.getValue(Usuario.class);
                                            ArrayList<String> ped=null;
                                            if(us.getPedidos()==null)
                                            {
                                                ped= new ArrayList<>();
                                            }
                                            else
                                            {
                                                ped=us.getPedidos();
                                            }
                                            ped.add(k);
                                            us.setPedidos(ped);
                                            pedido.setDirUsu(us.getDireccionUso());
                                            DatabaseReference myRefx = FirebaseDatabase.getInstance().getReference(PATH_PEDIDO+k);
                                            myRefx.setValue(pedido);
                                            dataSnapshot.getRef().setValue(us);
                                            DatabaseReference myRef4 = FirebaseDatabase.getInstance().getReference(Restaurant.getPathRest());
                                            myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    for(DataSnapshot snap : dataSnapshot.getChildren())
                                                    {
                                                        Restaurant r= snap.getValue(Restaurant.class);
                                                        if(r.getNombreR().equalsIgnoreCase(pedido.getEmpresa()))
                                                        {
                                                            if(r.getPedidosSinD()==null)
                                                            {
                                                                r.setPedidosSinD(new ArrayList<String>());
                                                            }
                                                            r.getPedidosSinD().add(k);
                                                            snap.getRef().setValue(r);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    DatabaseReference myRefCar = FirebaseDatabase.getInstance().getReference(Carrito.PATH_CARRITO + user.getUid());
                    myRefCar.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Modelo.CarritoCompras c= dataSnapshot.getValue(Modelo.CarritoCompras.class);
                            c.getCantprod().clear();
                            c.getProductos().clear();
                            dataSnapshot.getRef().setValue(c);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Intent intent = new Intent(Checkout.this, UsuarioEntrega.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("pedido", pedido);
                    startActivity(intent);

                }
            });
        }
    }
}
