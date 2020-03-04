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

public class PedidosAdapter extends ArrayAdapter {
    private int LayoutUso;
    private Context context;
    private List<Pedido> ordenes;
    public PedidosAdapter(Context context, int resource, List<Pedido> ordenes)
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
            TextView nombre = vie.findViewById(R.id.nombreProducto);
            TextView fecha = vie.findViewById(R.id.fechaPedido);
            TextView precio = vie.findViewById(R.id.precio);
            Button btn = vie.findViewById(R.id.button2);
            Button btn1 = vie.findViewById(R.id.button3);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registro = new Intent(v.getContext(),Producto.class);
                    v.getContext().startActivity(registro);
                }
            });
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registro = new Intent(v.getContext(),Calificar.class);
                    v.getContext().startActivity(registro);
                }
            });
            nombre.setText(pro.getNombreProducto());
            fecha.setText(pro.getFecha());
            precio.setText(String.valueOf(pro.getPrecio()));
        }
        return  vie;
    }
}
