package com.example.yardly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tab_productRes extends Fragment {


    RecyclerView rv;
    public List< Product > products;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_prodres, container, false);

        rv = v.findViewById( R.id.recyclerProducts );
        rv.setHasFixedSize( true );
        LinearLayoutManager ll = new LinearLayoutManager( v.getContext() );//Si no funciona, quitar el v.
        rv.setLayoutManager( ll );
        products = new ArrayList<>();
        //Recibir una lista de los resultados
        //La busqueda se hace en la pantalla shop antes de iniciar la actividad de resultados


        //Mientras tanto se usa el método auxiliar
        crearProductosFalsos();
        initializeAdapter();
        return v;
    }

    private void crearProductosFalsos() {
        for( int i = 0 ; i < 7 ; i++ )
        {
            Product p = new Product("Ramen","34000","ala, muy sabroso","ItachiRamen con Sabor");
            products.add( p );
        }
    }

    private void initializeAdapter() {
        ProductAdapter pAdapter = new ProductAdapter( products );
        rv.setAdapter( pAdapter );
    }

}
