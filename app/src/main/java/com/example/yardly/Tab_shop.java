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
    public static final String PATH_RESTS = "restaurants/";


    EditText et_buscador;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private List < Restaurant > restRes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_shop, container, false);
        database = FirebaseDatabase.getInstance();
        et_buscador = v.findViewById( R.id.et_buscador );


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





        return v;
    }


}
