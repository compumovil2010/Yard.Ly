package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Tab_shop extends Fragment {

    EditText et_buscador;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_shop, container, false);

        et_buscador = v.findViewById( R.id.et_buscador );

        et_buscador.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    Intent int_res = new Intent( v.getContext(), Search_results.class );
                    startActivity( int_res );
                    return true;
                }

                return false;
            }
        });



        return v;
    }
}
