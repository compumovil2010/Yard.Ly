package com.domicilio.yardly;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Modelo.Usuario;


public class Chat extends AppCompatActivity {
    public static final String PATH_CHAT = "chat/";
    public static final String PATH_USERS = "users/";
    public static final String PATH_DOMICILIARIOS = "domiciliario/";
    DatabaseReference myRef , myRefMsj;
    DatabaseReference mRootReference;
    Button enviar;
    EditText mensaje;
    FirebaseDatabase database;
    ListView mensajesChatsList;
    String keyChat = new String();
    String userId;
    List<MensajeChat> mensajes = new ArrayList<>();
    Boolean nombreya=false;
    String nombre = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        enviar= findViewById(R.id.enviarmsj);
        mensaje = findViewById(R.id.campoMensaje);
        mensajesChatsList = findViewById(R.id.listaMensajes);
        database = FirebaseDatabase.getInstance();
        final Pedido pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        if(pedido != null){
            userId = pedido.getDomi();
            keyChat = pedido.getIdChat();
            mostrarMsjs(pedido);
        }
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msjtext = mensaje.getText().toString();
                String fech = "";
                if(!(msjtext.trim().equals(""))){
                    MensajeChat msjChat = new MensajeChat();
                    fech = LocalDateTime.now().toString();
                    msjChat = new MensajeChat();
                    msjChat.setFechayhora(fech);
                    msjChat.setTexto(msjtext);
                    msjChat.setUsuario(nombre);
                    myRef =database.getReference(PATH_CHAT+keyChat);
                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(msjChat);
                    mensaje.setText("");
                }
            }
        });
    }


    private void mostrarMsjs(Pedido pedido){
        final String idchat = pedido.getIdChat();
        Toast.makeText(getBaseContext(),"entroooo"+idchat,Toast.LENGTH_LONG).show();
        mRootReference = FirebaseDatabase.getInstance().getReference(PATH_CHAT+idchat);
        mRootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    mensajes = new ArrayList<>();
                    for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                    {
                        if( singleSnap.exists() )
                        {
                            MensajeChat msj = singleSnap.getValue(MensajeChat.class);
                            if(msj != null) {
                                mensajes.add(msj);
                            }
                        }
                    }
                }
                buscar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeAdapter() {
        Log.i("mnioooooooo","taaaaaaaaaaam: " + mensajes.size());
        if(mensajes.size()>0) {
            ChatAdapter opAdapter = new ChatAdapter(getBaseContext(), R.layout.mensaje_chat_adapter, mensajes);
            mensajesChatsList.setAdapter(opAdapter);
        }
    }

    private void buscar() {
        myRef = database.getReference( PATH_DOMICILIARIOS+userId );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    Usuario usu=dataSnapshot.getValue(Usuario.class);
                    if (usu != null){
                        nombre = usu.getNombre();
                    }
                }
                initializeAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
