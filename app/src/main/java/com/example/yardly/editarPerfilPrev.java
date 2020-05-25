package com.example.yardly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class editarPerfilPrev extends AppCompatActivity {
    Button guardar,cancelar;
    ImageButton foto;
    EditText passw;
    boolean pasa=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_prev);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        passw=findViewById(R.id.pass);

        guardar=findViewById(R.id.siguien);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
                if(passw.getText().toString().isEmpty())
                {
                    Toast.makeText(v.getContext(),"Ingrese una contraseña",Toast.LENGTH_LONG).show();
                }
                else
                {
                    AuthCredential credential = EmailAuthProvider.getCredential(user2.getEmail(), passw.getText().toString());
                    user2.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.i("REAUTH", "User re-authenticated.");
                        }
                    });
                    if(FirebaseAuth.getInstance().getCurrentUser()==null)
                    {
                        cancelar.setEnabled(false);
                        Toast.makeText(v.getContext(), "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                        pasa=false;
                    }
                    else{
                        pasa=true;
                        cancelar.setEnabled(true);
                        Intent i=new Intent(getBaseContext(),editarPerfilU.class);
                        startActivity(i);
                    }
                }

            }
        });
        cancelar=findViewById(R.id.cancel);
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                devolver();
            }
        });
    }

    private void devolver()
    {
        if(!pasa)
        {
            this.onBackPressed();

        }
    }
}
