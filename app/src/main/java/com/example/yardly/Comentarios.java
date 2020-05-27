package com.example.yardly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Comentarios extends AppCompatActivity {
    TextView productoComentario;
    ListView mlista;
    List<Resena> resenas= new ArrayList<>();
    private FirebaseDatabase database;
    public static final String PATH_RESENA = "resena/";
    private DatabaseReference myRef;
    Product producto = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        productoComentario = findViewById(R.id.ProductoComentarios);
        producto = (Product) Objects.requireNonNull(getIntent().getBundleExtra("productoBundle")).getSerializable("producto");
        if (producto != null){
            productoComentario.setText(producto.getNomProducto());
        }

        mlista=findViewById(R.id.opiniones);
        database = FirebaseDatabase.getInstance();
        buscar();
    }
    private void buscar() {
        myRef = database.getReference( PATH_RESENA );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                if(re.getNomProduct().equalsIgnoreCase(producto.getNomProducto()) )
                                {
                                    resenas.add(re);
                                }
                            }
                        }

                    }
                }
                initializeAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initializeAdapter() {
        OpinionesAdapter opAdapter = new OpinionesAdapter(this, R.layout.opiniones,resenas);
        mlista.setAdapter(opAdapter);
    }
}
