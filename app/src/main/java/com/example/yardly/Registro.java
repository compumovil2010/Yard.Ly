package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {
    Button registroFot;
    EditText nombreUs;
    EditText apellidoUs;
    EditText email;
    EditText contrasena;
    TextView cancelar;

    @Override
    public void onBackPressed() {

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreUs = findViewById(R.id.et_nombreUsRe);
        apellidoUs = findViewById(R.id.apellidoUsRe);
        email = findViewById(R.id.mailUsRe);
        contrasena = findViewById(R.id.passUsRe);
        registroFot = findViewById(R.id.registrarForm);
        cancelar = findViewById( R.id.cancelarRegistro );


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(), logActivity.class ) );
            }
        });
        registroFot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registroFot = new Intent(getBaseContext(),RegistroFoto.class);
                Bundle datosUs = new Bundle();
                datosUs.putString("nombre", nombreUs.getText().toString() + " " + apellidoUs.getText().toString());
                datosUs.putString("mail", email.getText().toString());
                datosUs.putString("contrasena", contrasena.getText().toString());
                registroFot.putExtra("datosUs", datosUs);
                startActivity(registroFot);

            }
        });
    }
}
