package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import Modelo.Usuario;

public class Tab_home extends Fragment {
    Button btn_comida,btn_ropa;
    TextView km,arboles;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home, container, false);
        btn_comida = v.findViewById( R.id.btn_comida );
        btn_ropa = v.findViewById(R.id.btn_ropa);
        km = v.findViewById(R.id.tv_kmsnum);
        arboles = v.findViewById(R.id.total_arboles);
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent i=new Intent(getContext(), logActivity.class);
            startActivity(i);
        }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid());
       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Usuario u=dataSnapshot.getValue(Usuario.class);
               DecimalFormat formatter = new DecimalFormat("#0.00");
               float kmm = u.getKmRecorridos();
               km.setText(formatter.format(kmm));
               String s= "Arboles Sembrados: "+Math.floor(kmm/10);
               arboles.setText(s);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        btn_comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registro = new Intent( v.getContext(),mapaComida.class );
                startActivity( registro );

            }
        });
        btn_ropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"EN DESARROLLO...",Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}
