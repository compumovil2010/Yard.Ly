package com.example.yardly;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestViewHolder> {

    List<Restaurant> rests;
    public static class RestViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardr;
        TextView nombreRest;
        public RestViewHolder( View v )
        {
            super( v );
            cardr = v.findViewById( R.id.cardRest );
            nombreRest = v.findViewById( R.id.tv_nameRest );

        }
    }

    public RestaurantAdapter( List< Restaurant > prests){ this.rests = prests; }


    @Override
    public RestaurantAdapter.RestViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.rest_cardview, parent, false);
        RestViewHolder rvh = new RestViewHolder( v );

        return rvh;
    }

    @Override
    public void onBindViewHolder(RestViewHolder holder, int position) {
        final RestaurantAdapter.RestViewHolder h  = holder;
        Log.i("En view holder agrego " , rests.get( position ). getNombreR() );
        holder.nombreRest.setText( rests.get( position ).getNombreR() );

    }

    @Override
    public int getItemCount() {
        return rests.size();
    }
}

