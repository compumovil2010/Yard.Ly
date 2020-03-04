package com.example.yardly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AsginacionAdapter extends ArrayAdapter {
    private int LayoutUso;
    private Context context;
    private List<Pedido> ordenes;
    public AsginacionAdapter(Context context, int resource, List<Pedido> ordenes)
    {
        super(context,resource,ordenes);
        this.LayoutUso=resource;
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vie = convertView;
        if (vie==null){
            vie = LayoutInflater.from(getContext())
                    .inflate(LayoutUso, parent, false);
        }
        Pedido pro = (Pedido) getItem(position);
        if (pro!=null){
            TextView nombre = vie.findViewById(R.id.asNom);
            nombre.setText("Pedido");
        }
        return  vie;
    }
}
