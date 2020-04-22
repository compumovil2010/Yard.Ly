package com.example.yardly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class CatalogoAdapter extends ArrayAdapter {
    private int LayoutUso;
    private Context contex;
    public CatalogoAdapter (Context context, int resource, List<Product> lugares){
        super(context,resource,lugares);
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
        Product val = (Product) getItem(position);
        if (val!=null){
            TextView nimble = vie.findViewById(R.id.nombreProCatalogo);
            TextView descripcion = vie.findViewById(R.id.descripcionCatalogo);
            nimble.setText(val.getNomProducto());
            descripcion.setText(val.getDescripcion());
            //ImageButton img = vie.findViewById(R.id.elimdir);
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
