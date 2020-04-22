package com.example.yardly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductosCarritoAdapter extends ArrayAdapter<Product>{
    private int LayoutUso;
    private Context contex;
    public ProductosCarritoAdapter (Context context, int resource, List<Product> producs){
        super(context,resource,producs);
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
        Product pro = getItem(position);
        if (pro!=null){
            TextView nombre = vie.findViewById(R.id.nomprodCarr);
            TextView Descrip = vie.findViewById(R.id.DescripCarrito);
            TextView precio = vie.findViewById(R.id.precioCarrito);
            nombre.setText(pro.getNomProducto());
            Descrip.setText(pro.getDescripcion());
            precio.setText(String.valueOf(pro.getPrecio()));
        }
        return vie;
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



