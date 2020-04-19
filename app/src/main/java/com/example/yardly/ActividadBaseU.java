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
            case R.id.home:
                startActivity( new Intent(this, HomeActivity.class));
                return true;
            case R.id.busqueda:
                startActivity( new Intent(this, Busqueda.class));
                return true;
            case R.id.recientes:
                startActivity( new Intent(this, PedidoHistory.class));
                return true;
            case R.id.carrito:
                startActivity( new Intent(this, CarritoCompras.class));
                return true;
            case R.id.MiPerfil:
                startActivity( new Intent(this, infoPerfil.class));
                return true;
            case R.id.CerrarSesionE:
                authentication.signOut();
                Intent inte = new Intent(getApplicationContext(), logActivity.class);
                //inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(inte);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
