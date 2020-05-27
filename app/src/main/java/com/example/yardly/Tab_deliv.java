package com.example.yardly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modelo.Usuario;

public class Tab_deliv extends Fragment {
    RecyclerView rv;
    public List< Pedido > delivs;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private  int cuenta;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_deliv, container, false);
        database = FirebaseDatabase.getInstance();
        rv = v.findViewById( R.id.recyclerDelivs );
        rv.setHasFixedSize( true );
        LinearLayoutManager lm = new LinearLayoutManager( v.getContext() );
        rv.setLayoutManager( lm );
        delivs = new ArrayList<>();
        Bundle b = getArguments();
        buscar( b );
        return v;
          }


    private void buscar( Bundle b ) {
        myRef = database.getReference( Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario u = dataSnapshot.getValue(Usuario.class);
                if(u.getPedidos()!=null)
                {
                    cuenta=u.getPedidos().size();
                    for(String s: u.getPedidos())
                    {
                        DatabaseReference myRefAux = database.getReference(Pedido.PATH_PEDIDO + s);
                        myRefAux.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                delivs.add(dataSnapshot.getValue(Pedido.class));
                                initializeAdapter();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initializeAdapter() {
        cuenta--;
        if(cuenta==0)
        {
            DelivAdapter pAdapter = new DelivAdapter( delivs );
            rv.setAdapter( pAdapter );
        }

    }
}
