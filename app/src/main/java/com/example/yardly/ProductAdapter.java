package com.example.yardly;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List< Product > products;
    public static  class ProductViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardp;
        TextView nombreProducto;
        TextView precioProducto;
        Button btn_agregar;
        public ProductViewHolder(View v)
        {
            super(v);
            cardp = v.findViewById( R.id.cardProduct );
            nombreProducto = v.findViewById( R.id.tv_nameProduct );
            precioProducto = v.findViewById( R.id.tv_precio );
            btn_agregar = v.findViewById( R.id.btn_agregar );
        }

    }

    public ProductAdapter( List< Product > pproducts)
    {
        this.products = pproducts;
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.product_cardview, parent, false );
        ProductViewHolder pvh = new ProductViewHolder( v );
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder,final int position) {
        final ProductAdapter.ProductViewHolder h = holder;
        holder.nombreProducto.setText( products.get( position ).getNomProducto() );
        holder.precioProducto.setText( products.get( position ).getPrecio() );
        holder.btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Producto.class);
                intent.putExtra("producto",products.get(position));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
