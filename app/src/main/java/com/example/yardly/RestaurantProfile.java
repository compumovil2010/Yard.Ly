package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RestaurantProfile extends AppCompatActivity {
    Button productxrestaurant;
    Restaurant restaurant;
    TextView nombreRestaurantePerfil,descripcionRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        productxrestaurant = findViewById(R.id.ProductsxRestaurante);
        descripcionRestaurant =findViewById(R.id.DescripcionRestaurant);
        nombreRestaurantePerfil = findViewById(R.id.NombreRestaurantePerfil);
        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurante");
        if (restaurant != null){
            nombreRestaurantePerfil.setText(restaurant.getNombreR());
            descripcionRestaurant.setText(restaurant.getDescripR());
        }
        productxrestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(v.getContext(),ProductoxRestaurante.class);
                if (restaurant != null){
                    inte.putExtra("nombreR",restaurant.getNombreR());
                    startActivity(inte);
                }
            }
        });
    }
}
