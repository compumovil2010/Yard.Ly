package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {
    Button registroFot;
    EditText nombreUs;
    EditText apellidoUs;
    EditText email;
    EditText contrasena;
    EditText cContrasena;
    TextView cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombreUs = findViewById(R.id.et_nombreUsRe);
        apellidoUs = findViewById(R.id.apellidoUsRe);
        email = findViewById(R.id.mailUsRe);
        contrasena = findViewById(R.id.passUsRe);
        cContrasena = findViewById(R.id.cContrasena);
        registroFot = findViewById(R.id.registrarForm);
        cancelar = findViewById(R.id.cacelarRegistro);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getBaseContext(), logActivity.class);
                startActivity(inte);
            }
        });
        registroFot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarDatos()){
                    Intent registroFot = new Intent(getBaseContext(),RegistroFoto.class);
                    Bundle datosUs = new Bundle();
                    datosUs.putString("nombre", nombreUs.getText().toString() + " " + apellidoUs.getText().toString());
                    datosUs.putString("mail", email.getText().toString());
                    datosUs.putString("contrasena", contrasena.getText().toString());
                    registroFot.putExtra("datosUs", datosUs);
                    startActivity(registroFot);
                }

            }
        });
    }
    private boolean verificarDatos(){
        boolean pasa = true;
        String mail = email.getText().toString();
        if(nombreUs.getText().toString().length() < 4 || apellidoUs.getText().toString().length() < 4 ){
            Toast.makeText(this, "No es posible que exista un nombre o apellido tan corto",Toast.LENGTH_SHORT).show();
            nombreUs.setText("");
            apellidoUs.setText("");
            pasa = false;
        }
        if(!contrasena.getText().toString().equals(cContrasena.getText().toString()) || contrasena.getText().toString().isEmpty()){
            Toast.makeText(this, "Las contraseñas no corresponden",Toast.LENGTH_SHORT).show();
            contrasena.setText("");
            cContrasena.setText("");
            pasa = false;
        }
        if(TextUtils.isEmpty(mail) || !mail.contains("@") || !mail.contains(".") || mail.length() < 5){
            Toast.makeText(this, "No ingresaste un email válido",Toast.LENGTH_SHORT).show();
            email.setText("");
            pasa = false;
        }
        return  pasa;
    }
}
