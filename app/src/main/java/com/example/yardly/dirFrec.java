package com.example.yardly;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modelo.Usuario;

public class dirFrec extends AppCompatActivity {
    ArrayList<String> mProjection= new ArrayList<>();
    ListView mlista;
    ImageButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir_frec);
        mlista=findViewById(R.id.listaDir);
        add=findViewById(R.id.imageView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

                alert.setTitle("Añadir Nueva Direccion");
                alert.setMessage("Escriba la Direccion");

// Set an EditText view to get user input
                final EditText input = new EditText(v.getContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid());
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Usuario u=dataSnapshot.getValue(Usuario.class);
                                if(u.getDirecciones()==null)
                                {
                                    u.setDirecciones(mProjection);
                                }
                                ArrayList<String>aux=u.getDirecciones();
                                if(!input.getText().toString().isEmpty())
                                {
                                    aux.add(input.getText().toString());
                                    u.setDirecciones(aux);
                                    dataSnapshot.getRef().setValue(u);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario u=dataSnapshot.getValue(Usuario.class);
                if(u.getDirecciones()==null)
                {
                    u.setDirecciones(mProjection);
                }
                if(u.getDirecciones().size()==0)
                {
                    dataSnapshot.getRef().setValue(u);
                }
                mProjection=u.getDirecciones();
                DireccionesAdapter dAdapter = new DireccionesAdapter(getBaseContext(), R.layout.verdire,mProjection);
                mlista.setAdapter(dAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
