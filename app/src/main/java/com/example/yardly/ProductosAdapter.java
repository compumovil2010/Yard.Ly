package com.example.yardly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ProductosAdapter extends ArrayAdapter<Product> {
    private int LayoutUso;
    private Context contex;
    private Product pro;
    public ProductosAdapter (Context context, int resource, List<Product> products){
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
        pro = getItem(position);
        if (pro!=null){
            TextView nombre = vie.findViewById(R.id.nomProd);
            TextView Descrip = vie.findViewById(R.id.desProd);
            TextView precio = vie.findViewById(R.id.precProd);
            Button b= vie.findViewById(R.id.agregarProd);
            nombre.setText(pro.getNomProducto());
            Descrip.setText(pro.getDescripcion());
            precio.setText(String.valueOf(pro.getPrecio()));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registro = new Intent(v.getContext(),Producto.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("producto",pro);
                    registro.putExtra("Bproducto",bundle);
                    v.getContext().startActivity(registro);
                }
            });
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