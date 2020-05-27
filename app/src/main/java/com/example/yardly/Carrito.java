package com.example.yardly;

import Modelo.CarritoCompras;
import Modelo.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Carrito extends AppCompatActivity {


    RecyclerView rv;
    Button btn_next;
    Toolbar tb_top;
    List<Product> products;
    List<String> pids;
    List<Integer> cantp;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Product pro;
    public  static  final String PATH_PEDIDO = "pedido/";
    public static final String PATH_PRODUCTS = "products/";
    public static final String PATH_CARRITO = "carritos/";
    public static final String PATH_CHAT = "chat/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        pro = (Product) getIntent().getSerializableExtra("producto");
        btn_next = findViewById(R.id.btn_gocheckout);
        database = FirebaseDatabase.getInstance();

        products = new ArrayList<>();
        cantp = new ArrayList<>();
        pids = new ArrayList<>();

        rv = findViewById( R.id.recyclerCarrito );
        rv.setHasFixedSize( true );
        LinearLayoutManager ll = new LinearLayoutManager( getApplicationContext() );//Si no funciona, quitar el v.
        rv.setLayoutManager( ll );

        tb_top = findViewById( R.id.toolbar );
        setSupportActionBar( tb_top );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //crearCarrito()
        DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/direcciones");
        myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                {
                    Toast.makeText(getBaseContext(),"Debes Seleccionar una Direccion",Toast.LENGTH_LONG);
                    btn_next.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        traerCarrito();


    }
/*
    private void crearCarrito() {
        myRef = database.getReference( PATH_CARRITO );
        String cartId = myRef.push().getKey();
        DatabaseReference mR2 = database.getReference( PATH_PRODUCTS );

        mR2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList< String >prods = new ArrayList<>();
                ArrayList< Integer > cantp = new ArrayList<>();

                for( DataSnapshot snapshot : dataSnapshot.getChildren() )
                {
                    prods.add( snapshot.getKey() );
                    cantp.add(2);
                }

                FirebaseUser currentUsr = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUsr.getUid();

                    CarritoCompras ccmp = new CarritoCompras(prods,cantp,uid);
                    myRef.child(uid).setValue( ccmp );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

 */

    public void traerCarrito()
    {
        final FirebaseUser currentUsr = FirebaseAuth.getInstance().getCurrentUser();

        myRef = database.getReference( PATH_CARRITO );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren() )
                {
                    Log.i("Carrito", "Ando BUSCANDO" + snap.getKey() + " y " + currentUsr.getUid() );
                    if(snap.getKey().equals(currentUsr.getUid()) )
                    {
                        Log.i("Carrito", "Encontre un carrito");
                        CarritoCompras c = snap.getValue(Modelo.CarritoCompras.class);
                        crearProductos( c );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void crearProductos(final CarritoCompras c)
    {
        myRef = database.getReference(PATH_PRODUCTS);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(c.getProductos()== null){
                    c.setProductos(new ArrayList<String>());
                }
                for(int i = 0 ; i < c.getProductos().size() ; i++)
                {
                    for(DataSnapshot snap : dataSnapshot.getChildren())
                    {
                        if( snap.getKey().equals(c.getProductos().get(i)) )
                        {
                            pids.add( snap.getKey() );
                            Product p = snap.getValue(Product.class);
                            products.add( p );
                        }

                    }
                }
                initializeAdapter( c );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void initializeAdapter(CarritoCompras c) {
        cantp = c.getCantprod();

        Log.i("INICIALIZADA", "Inicie adapter de CARRITOOOOOOOOOO");
        CartAdapter cAdapter = new CartAdapter( products, c.getCantprod() );
        rv.setAdapter( cAdapter );
        final CartAdapter ca = (CartAdapter) rv.getAdapter();
        //Log.i("CARTADAPTER CANTIDAD", String.valueOf(ca.getCantP().size()) + String.valueOf(ca.getCantP().get(2)) );
        iniButton();

    }

    private void iniButton() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_cho = new Intent( v.getContext(), Checkout.class );
                FirebaseUser currentUsr = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUsr.getUid();
                float price = 0;
                for( int i = 0 ; i < products.size(); i++)
                {
                    price = price + ( Float.parseFloat(products.get(i).getPrecio()) * cantp.get(i) );
                }
                myRef = database.getReference(PATH_CHAT);
                String key;
                key = myRef.push().getKey();
                myRef.child(key);
                Date c = Calendar.getInstance().getTime();
                Pedido p = new Pedido(c.toString(),"Pedido", price, (ArrayList)pids, (ArrayList)cantp, uid, "", "Cra 4 # 8-27", "TopWay", "");
                p.setIdChat(key);
                myRef = database.getReference(PATH_PEDIDO);
                String k = myRef.push().getKey();
                //myRef.child( k ).setValue(p);
                int_cho.putExtra("pid", k);
                int_cho.putExtra("pedido",p);

                //if(pro != null) {
                //    int_cho.putExtra("producto", pro);
                //    startActivity(int_cho);
                //}
                startActivity(int_cho);
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
}
