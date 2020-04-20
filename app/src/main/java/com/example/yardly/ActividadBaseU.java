package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class ActividadBaseU extends AppCompatActivity {
    private static FirebaseAuth authentication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        authentication = FirebaseAuth.getInstance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar_perfil:
                //TODO editar perfil
                return true;
            case R.id.report:
                //TODO Reporte
                return true;
            case R.id.adrs:
                //TODO direcciones
                return true;
            case R.id.salir:
                //TODO Cerrar Sesión
                authentication.signOut();
                Intent int_logout = new Intent( getApplicationContext(), logActivity.class );
                startActivity( int_logout );
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
