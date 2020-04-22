package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Producto extends AppCompatActivity {
    private String nombre;
    private int precio =0;
    private String Descripcion;
    private boolean habilitado;
    //tipo resena
    private List<String> calificaciones;
    private List<String> tags;
    private int cantidadNum;
    private TextView nombreTextView, descripcionTextView, precioTextView, cantidad, total, storeName, ratingValue;
    private Button mas, menos, comentarios;
    private FirebaseDatabase database;
    public static final String PATH_RESENA = "resena/";
    private DatabaseReference myRef;
    String fin;
    Product pro;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        database = FirebaseDatabase.getInstance();
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
        b=findViewById(R.id.aggCarrit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),CarritoCompras.class);
                registro.putExtra("producto",pro);
                startActivity(registro);
            }
        });
        precio  = Integer.parseInt(precioTextView.getText().toString());
        int cant = Integer.parseInt(cantidad.getText().toString());
        total.setText(String.valueOf(precio*cant));
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
                Toast.makeText(getBaseContext(), fin,Toast.LENGTH_LONG).show();
                ratingValue.setText(fin);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}