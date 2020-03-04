package com.example.yardly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ProductosAdapter extends ArrayAdapter<Producto> {

    private int LayoutUso;
    private Context contex;
    public ProductosAdapter (Context context, int resource, List<Producto> products){
        super(context,resource,products);
        this.LayoutUso=resource;
        this.contex=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vie = convertView;
        if (vie==null){
            vie = LayoutInflater.from(getContex())
                    .inflate(getLayoutUso(), parent, false);
        }
        Producto pro = getItem(position);
        if (pro!=null){
            TextView nombre = vie.findViewById(R.id.nomProd);
            TextView Descrip = vie.findViewById(R.id.desProd);
            TextView precio = vie.findViewById(R.id.precProd);
            Button b= vie.findViewById(R.id.agregarProd);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registro = new Intent(v.getContext(),Producto.class);
                    v.getContext().startActivity(registro);
                }
            });

            nombre.setText(pro.getNombre());
            Descrip.setText(pro.getDescripcion());
            precio.setText(String.valueOf(pro.getPrecio()));
        }
        return  vie;
    }
    public int getLayoutUso() {
        return LayoutUso;
    }

    public void setLayoutUso(int layoutUso) {
        LayoutUso = layoutUso;
    }

    public Context getContex() {
        return contex;
    }

    public void setContex(Context contex) {
        this.contex = contex;
    }
}
