package com.example.yardly;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PedidosActivosAdapter extends ArrayAdapter<Pedido>{
    private int LayoutUso;
    private Context contex;
    public PedidosActivosAdapter (Context context, int resource, List<Pedido> pedids){
        super(context,resource,pedids);
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
        Pedido pro = getItem(position);
        if (pro!=null){
            TextView nombre = vie.findViewById(R.id.nompedido);
            TextView Descrip = vie.findViewById(R.id.desPedido);
            TextView precio = vie.findViewById(R.id.fechaPedido);
            nombre.setText(pro.getNombreProducto());
            Descrip.setText(pro.getFecha());
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
