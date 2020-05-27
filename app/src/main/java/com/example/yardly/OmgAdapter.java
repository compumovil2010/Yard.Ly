package com.example.yardly;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OmgAdapter extends RecyclerView.Adapter<OmgAdapter.RestViewHolder>  {
    List<Product> prods;
    public static class RestViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardr;
        ImageView iv_pord;
        public RestViewHolder( View v )
        {
            super( v );
            cardr = v.findViewById( R.id.cardOmg );
            iv_pord = v.findViewById(R.id.imageView3 );
        }
    }

    public OmgAdapter( List< Product > pprods){ this.prods = pprods; }


    @Override
    public OmgAdapter.RestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cadr_omg, parent, false);
        RestViewHolder rvh = new RestViewHolder( v );

        return rvh;
    }

    @Override
    public void onBindViewHolder(OmgAdapter.RestViewHolder holder, final int position) {
        final OmgAdapter.RestViewHolder h  = holder;
        Log.i("En view holder agrego " , prods.get( position ).getNomProducto() );
        if(position % 3 == 0)
        {
            holder.iv_pord.setImageResource( R.drawable.plato2);
        }
        else if(position % 3 == 1)
        {
            holder.iv_pord.setImageResource( R.drawable.comida1);
        }
        else
        {
            holder.iv_pord.setImageResource( R.drawable.plato3);
        }
        holder.cardr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Producto.class);
                intent.putExtra("producto",prods.get(position));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return prods.size();
    }
}
