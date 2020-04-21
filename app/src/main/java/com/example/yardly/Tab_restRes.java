package com.example.yardly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tab_restRes extends Fragment {
    RecyclerView rv;
    public List < Restaurant > rests;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public static final String PATH_RESTS = "restaurants/";


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View v = inflater.inflate( R.layout.tab_restres, container, false );
        database = FirebaseDatabase.getInstance();
        rv = v.findViewById( R.id.recyclerRests );
        rv.setHasFixedSize( true );
        LinearLayoutManager lm = new LinearLayoutManager( v.getContext() );
        rv.setLayoutManager( lm );
        rests = new ArrayList<>();
        Bundle b = getArguments();
        buscar( b );
        return v;
    }

    private void buscar( Bundle b ) {
        final String busqueda = b.getString( "searchKey" );
        myRef = database.getReference( PATH_RESTS );
        Log.i("Buscando el Restaurante", b.getString("searchKey"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                    {

                        if( singleSnap.exists() )
                        {
                            Restaurant r = singleSnap.getValue( Restaurant.class );
                            Log.i("Busqueda", "Busco un Restaurante con "+ busqueda + " y " + r.getNombreR().toLowerCase());
                            if( r.getNombreR().toLowerCase().contains( busqueda ) )
                            {
                                Log.i("Busqueda", "Encontré un Restaurante");
                                rests.add( r );
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

        Log.i("INICIALIZADA", "Inicie adapter  " + rests.size() );
        RestaurantAdapter pAdapter = new RestaurantAdapter( rests );
        rv.setAdapter( pAdapter );
    }
}
