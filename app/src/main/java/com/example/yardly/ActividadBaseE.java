package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActividadBaseE extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbare);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity( new Intent(this, HomeActivity.class));
                return true;

            case R.id.busqueda:
                startActivity( new Intent(this, dirFrec.class));
                return true;

            case R.id.recientes:
                startActivity( new Intent(this, infoPedido.class));
                return true;
            case R.id.carrito:
                startActivity( new Intent(this, Registro.class));
                return true;
            case R.id.perfil:
                startActivity( new Intent(this, infoPerfil.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
