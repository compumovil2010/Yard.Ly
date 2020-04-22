package com.example.yardly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPass extends AppCompatActivity {
    private EditText email;
    private TextView cancelarForg;
    private Button recuperar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        email = findViewById(R.id.fpss_email);
        cancelarForg = findViewById(R.id.cacelarForgot);
        recuperar = findViewById(R.id.recuperarPass);
        auth = FirebaseAuth.getInstance();
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                if (TextUtils.isEmpty(mail) || !mail.contains("@") || !mail.contains(".")) {
                    Toast.makeText(getApplication(), "El mail no es válido", Toast.LENGTH_SHORT).show();
                }
                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplication(), "Se han enviado instrucciones para cambiar la contraseña", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplication(), "No se ha podido enviar al correo ingresado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        cancelarForg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getBaseContext(), logActivity.class);
                startActivity(inte);
            }
        });
    }
}
