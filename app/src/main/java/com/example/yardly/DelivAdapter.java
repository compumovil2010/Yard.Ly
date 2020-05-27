package com.example.yardly;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Modelo.Usuario;


public class DelivAdapter extends RecyclerView.Adapter<DelivAdapter.DelivViewHolder> {
    List< Pedido > products;
    public static  class DelivViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardp;
        TextView nombreProducto;
        TextView precioP;
        Button btn_chat;
        public DelivViewHolder(View v)
        {
            super(v);
            cardp = v.findViewById( R.id.cardDelivs );
            nombreProducto = v.findViewById( R.id.tv_namePedido );
            precioP = v.findViewById( R.id.tv_precioP );
            btn_chat = v.findViewById( R.id.btn_chat );
        }

    }

    public DelivAdapter(List< Pedido > pproducts)
    {
        this.products = pproducts;
    }

    @Override
    public DelivAdapter.DelivViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.deliv_cardview, parent, false );
        DelivViewHolder pvh = new DelivViewHolder( v );
        return pvh;
    }

    @Override
    public void onBindViewHolder(DelivAdapter.DelivViewHolder holder, final int position) {
        final DelivAdapter.DelivViewHolder h = holder;
        holder.precioP.setText( ""+products.get( position ).getPrecio() );
        holder.cardp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioEntrega.class);
                intent.putExtra("pedido",products.get(position));
                v.getContext().startActivity(intent);
            }
        });
        holder.btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Chat.class);
                intent.putExtra("pedido",products.get(position));
                v.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return products.size();
    }
}
