package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import Modelo.CarritoCompras;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Producto extends AppCompatActivity {

    public static final String PATH_RESENA = "resena/";
    public static final String PATH_CARRITO = "carritos/";
    public static final String PATH_PRODUCTS = "products/";
    private int precio =0;
    //tipo resena
    private List<String> calificaciones;
    private List<String> productos;
    private List<Integer> cantprod;
    private int cantidadNum;
    private TextView nombreTextView, descripcionTextView, precioTextView, cantidad, total, storeName, ratingValue;
    private Button mas, menos, comentarios;
    private FirebaseDatabase database;

    private DatabaseReference myRef;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    CarritoCompras ccmp;
    String llaveProd;
    String fin;
    Product pro;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pro = (Product) Objects.requireNonNull(getIntent().getSerializableExtra("producto"));
        nombreTextView = findViewById(R.id.nomProduct);
        descripcionTextView = findViewById(R.id.descripProduc);
        precioTextView = findViewById(R.id.precioProduct);
        storeName = findViewById(R.id.storeName);
        cantidad = findViewById(R.id.cantProduct);
        total = findViewById(R.id.total);
        ratingValue = findViewById(R.id.ratingValue);
        mas = findViewById(R.id.mas);
        calificaciones = new ArrayList<>();
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadNum = Integer.parseInt(cantidad.getText().toString());
                cantidadNum = cantidadNum + 1;
                precio  = Integer.parseInt(precioTextView.getText().toString());
                cantidad.setText(String.valueOf(cantidadNum));
                total.setText(String.valueOf(precio*cantidadNum));
            }
        });
        menos = findViewById(R.id.menos);
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidadNum = Integer.parseInt(cantidad.getText().toString());
                if(cantidadNum>1){
                    precio  = Integer.parseInt(precioTextView.getText().toString());
                    cantidadNum = cantidadNum - 1;
                    cantidad.setText(String.valueOf(cantidadNum));
                    total.setText(String.valueOf(precio*cantidadNum));
                }
            }
        });
        comentarios = findViewById(R.id.comentariosProd);
        comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),Comentarios.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("producto", pro);
                intent.putExtra("productoBundle",bundle);
                startActivity(intent);
            }
        });
        if (pro!=null){
            nombreTextView.setText(pro.getNomProducto());
            descripcionTextView.setText(pro.getDescripcion());
            precioTextView.setText(String.valueOf(pro.getPrecio()));
            storeName.setText(pro.getNomEstab());
            buscar();
        }
        precio  = Integer.parseInt(precioTextView.getText().toString());
        int cant = Integer.parseInt(cantidad.getText().toString());
        total.setText(String.valueOf(precio*cant));

        b=findViewById(R.id.aggCarrit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buscarCarroCompras();

            }
        });

    }

    private void buscarCarroCompras()
    {
        myRef = database.getReference(PATH_CARRITO);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                for(DataSnapshot snap :  dataSnapshot.getChildren())
                {
                    if(snap.exists() && (snap.getKey().equals( u.getUid() )))
                    {
                        CarritoCompras carrito = snap.getValue( CarritoCompras.class );
                        agregarProducto( carrito );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void agregarProducto(final CarritoCompras carrito) {
        myRef = database.getReference(PATH_PRODUCTS);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> prods = new ArrayList<>();
                List<Integer> cantp = new ArrayList<>();
                for(DataSnapshot snap : dataSnapshot.getChildren() )
                {
                    if(snap.exists())
                    {
                        Product p = snap.getValue( Product.class );
                        if( p.getNomProducto().equals(pro.getNomProducto() ) )
                        {
                            FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                            if(carrito.getProductos()==null ||carrito.getProductos().get(0).equals("vacio"))
                            {
                                prods.add(snap.getKey());
                                cantp.add(Integer.parseInt(cantidad.getText().toString()));
                                carrito.setCantprod( cantp );
                                carrito.setProductos( prods );
                                DatabaseReference mr2 = database.getReference(PATH_CARRITO);
                                mr2.child( u.getUid() ).setValue(carrito);
                                mandarIntent();
                            }
                            else
                            {
                                prods = carrito.getProductos();
                                cantp = carrito.getCantprod();
                                prods.add(snap.getKey());
                                cantp.add(Integer.parseInt(cantidad.getText().toString()));
                                carrito.setCantprod( cantp );
                                carrito.setProductos( prods );
                                DatabaseReference mr2 = database.getReference(PATH_CARRITO);
                                mr2.child( u.getUid() ).setValue(carrito);
                                mandarIntent();

                            }


                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String sacarPuntaje() {
        DecimalFormat value = new DecimalFormat("#.#");
        float aux =0;
        if(calificaciones.size()>0){
            for (String s:calificaciones){
                aux = aux + Float.parseFloat(s);
            }
            aux = aux / calificaciones.size();
            fin = String.valueOf(value.format(aux));
        }
        else{
            fin = "no hay calificaciones aun";
        }
        return fin;
    }

    private void buscar() {
        myRef = database.getReference( PATH_RESENA );
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                    {
                        if( singleSnap.exists() )
                        {
                            Resena re = singleSnap.getValue( Resena.class );
                            if(re != null){
                                if(re.getNomProduct().equalsIgnoreCase(pro.getNomProducto()) )
                                {
                                    calificaciones.add(re.getPuntaje());
                                }
                            }
                        }

                    }
                }
                fin = sacarPuntaje();
                ratingValue.setText(fin);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void mandarIntent() {
        Intent registro = new Intent(getBaseContext(),Carrito.class);
        registro.putExtra("producto",pro);
        startActivity(registro);
        finish();
    }
}