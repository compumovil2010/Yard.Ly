package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab_home extends Fragment {

    Button btn_comida,btn_ropa;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home, container, false);
        btn_comida = v.findViewById( R.id.btn_comida );
        btn_ropa = v.findViewById(R.id.btn_ropa);
        btn_comida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registro = new Intent( v.getContext(),omgreen.class );
                startActivity( registro );

            }
        });
        btn_ropa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ropa = new Intent( v.getContext(),RestaurantProfile.class );
                startActivity(ropa);
            }
        });


        return v;
    }
}
