package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RestaurantProfile extends AppCompatActivity {
    Button productxrestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);
        productxrestaurant = findViewById(R.id.ProductsxRestaurante);
        productxrestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(v.getContext(),ProductoxRestaurante.class);
                startActivity(inte);
            }
        });
    }
}
