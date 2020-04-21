package com.example.yardly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tab_productRes extends Fragment {


    RecyclerView rv;
    public List< Product > products;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public static final String PATH_PRODUCTS = "products/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_prodres, container, false);
        database = FirebaseDatabase.getInstance();
        rv = v.findViewById( R.id.recyclerProducts );
        rv.setHasFixedSize( true );
        LinearLayoutManager ll = new LinearLayoutManager( v.getContext() );//Si no funciona, quitar el v.
        rv.setLayoutManager( ll );
        products = new ArrayList<>();
        Bundle b = getArguments();

        buscar( b );

        return v;
    }

    private void buscar( Bundle b ) {
        final String busqueda = b.getString( "searchKey" );
        myRef = database.getReference( PATH_PRODUCTS );
        Log.i("Buscando el producto", b.getString("searchKey"));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                {
                    Product p = singleSnap.getValue( Product.class );
                    Log.i("Busqueda", "Busco un producto con "+ busqueda + " y " + p.getNomProducto().toLowerCase());
                    if( p.getNomProducto().toLowerCase().contains( busqueda ) )
                    {
                        Log.i("Busqueda", "Encontré un producto");
                        products.add( p );
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

        Log.i("INICIALIZADA", "Inicie adapter");
        ProductAdapter pAdapter = new ProductAdapter( products );
        rv.setAdapter( pAdapter );
    }

}
