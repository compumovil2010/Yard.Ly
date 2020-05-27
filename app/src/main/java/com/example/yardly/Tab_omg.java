package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab_omg extends Fragment {

    public static final String PATH_PROD = "products/";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public ArrayList< Product > prods;
    private RecyclerView rv;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_omg, container, false);
        prods = new ArrayList<>();
        rv = v.findViewById( R.id.recyclerRests );
        rv.setHasFixedSize( true );
        LinearLayoutManager lm = new LinearLayoutManager( v.getContext() );
        rv.setLayoutManager( lm );
        obtenerRestaurantes();

        return v;
    }

    private void obtenerRestaurantes() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference( PATH_PROD );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if( dataSnapshot.exists() )
                {
                    for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                    {
                        if(singleSnap.exists())
                        {
                            Product p = singleSnap.getValue( Product.class );
                            prods.add( p );
                        }
                    }
                    
                    actualizarOMG();
                    
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void actualizarOMG()
    {
        Log.i("INICIALIZADA", "Inicie adapter  " + prods.size() );
        OmgAdapter pAdapter = new OmgAdapter( prods );
        rv.setAdapter( pAdapter );

    }
}
