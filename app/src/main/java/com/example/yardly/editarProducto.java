package com.example.yardly;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class editarProducto extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        spinner = (Spinner) findViewById(R.id.tipo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipoProducto, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
