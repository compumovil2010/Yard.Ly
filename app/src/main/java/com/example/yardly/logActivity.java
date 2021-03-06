package com.example.yardly;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logActivity extends AppCompatActivity {
    private static FirebaseAuth authentication;
    Button ingreso;
    EditText email;
    EditText password;
    TextView registro;
    TextView forgotPassword;
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        authentication = FirebaseAuth.getInstance();
        registro = findViewById(R.id.register);
        email = findViewById(R.id.et_user);
        password = findViewById(R.id.et_pword);
        forgotPassword = findViewById(R.id.tv_forgotpw);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),forgotPass.class);
                startActivity(registro);
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(getBaseContext(),Registro.class);
                startActivity(registro);
            }
        });

        ingreso = findViewById(R.id.botonIngresar);
        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singInUser(email.getText().toString(),password.getText().toString());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = authentication.getCurrentUser();
        actualizarUI(currentUser);
    }
    private void actualizarUI(FirebaseUser usuario){
        if(usuario != null){
            Intent ingreso = new Intent(getBaseContext(),Principal.class);
            ingreso.putExtra("user", usuario.getEmail());
            startActivity(ingreso);
        }else{
            email.setText("");
            password.setText("");
        }
    }
    private boolean validateUserPass(){
        boolean valid = true;
        String mail = email.getText().toString();
        if(TextUtils.isEmpty(mail) || !mail.contains("@") || !mail.contains(".") || mail.length() < 5){
            email.setError("Obligatorio");
            valid = false;
        }
        else{
            email.setError(null);
        }
        String pass = password.getText().toString();
        if(TextUtils.isEmpty(pass) || pass.length() < 8){
            password.setError("Obligatorio");
            valid = false;
        }
        return valid;
    }
    private void singInUser(String mail, String pass){
        if(validateUserPass()){
            authentication.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser user = authentication.getCurrentUser();
                        actualizarUI(user);
                    }else {
                        Toast.makeText(logActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        actualizarUI(null);
                    }

                }
            });
        }
    }
    public static void singOut()
    {
        authentication.signOut();
    }
}
