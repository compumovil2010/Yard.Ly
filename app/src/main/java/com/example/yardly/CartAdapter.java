package com.example.yardly;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class CartAdapter extends RecyclerView.Adapter< CartAdapter.CartViewHolder > {
    List<Product>productos;
    List<Integer>cantP;
    public static final String PATH_CARRITO = "carritos/";


    public List<Integer> getCantP() {
        return cantP;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardCarrito;
        TextView nomP;
        TextView precio;
        TextView tv_cant;
        Button mas;
        Button menos;

        public CartViewHolder( View v )
        {
            super( v );
            cardCarrito = v.findViewById( R.id.cardCarrito );
            nomP = v.findViewById( R.id.tv_nameProduct );
            precio = v.findViewById( R.id.tv_precio );
            tv_cant = v.findViewById( R.id.cantProduct );
            mas = v.findViewById( R.id.mas );
            menos = v.findViewById( R.id.menos );
        }
    }
    public CartAdapter(List<Product> pprod, List<Integer>pcant)
    {
        this.cantP = pcant;
        this.productos = pprod;
    }


    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.carrito_cardview, parent, false );
        CartViewHolder cvh = new CartViewHolder( v );
        return cvh;
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        holder.tv_cant.setText( String.valueOf(cantP.get( position )) );
        holder.precio.setText( productos.get( position ).getPrecio() );
        holder.nomP.setText( productos.get( position ).getNomProducto() );
        holder.mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  cant = Integer.parseInt( holder.tv_cant.getText().toString() );
                cant = cant + 1;
                holder.tv_cant.setText( String.valueOf( cant ));
                FirebaseUser currentUsr = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUsr.getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(PATH_CARRITO + uid + "/cantprod/"+ String.valueOf( position )+ "/");
                myRef.setValue( cant );
            }
        });
        holder.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  cant = Integer.parseInt( holder.tv_cant.getText().toString() );
                cant = cant - 1;
                holder.tv_cant.setText( String.valueOf( cant ));
                FirebaseUser currentUsr = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUsr.getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(PATH_CARRITO + uid + "/cantprod/"+ String.valueOf( position )+ "/");
                myRef.setValue( cant );
            }
        });

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}
