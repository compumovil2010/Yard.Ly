package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Tab_shop extends Fragment {



    EditText et_buscador;
    FloatingActionButton fbtn_carrito;
    private List < Restaurant > restRes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_shop, container, false);

        et_buscador = v.findViewById( R.id.et_buscador );

        fbtn_carrito = v.findViewById( R.id.fbtn_cart );

        et_buscador.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    String busqueda = et_buscador.getText().toString().trim().toLowerCase();
                    if( !busqueda.trim().equals("") )
                    {
                        Log.i("HEY ACA", busqueda);

                        Intent int_res = new Intent( v.getContext(), Search_results.class );

                        Bundle bnd_results = new Bundle();
                        bnd_results.putString( "searchKey", busqueda );
                        int_res.putExtra( "busqueda", bnd_results);

                        startActivity( int_res );
                        return true;
                    }
                    return  true;


                }

                return false;
            }
        });

        fbtn_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_carrito = new Intent( v.getContext(), Carrito.class );
                startActivity( int_carrito );
            }
        });





        return v;
    }


}
