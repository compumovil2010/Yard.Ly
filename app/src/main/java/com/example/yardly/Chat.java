package com.example.yardly;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Modelo.Domiciliario;
import Modelo.Usuario;

public class Chat extends AppCompatActivity {
    public static final String PATH_CHAT = "chat/";
    public static final String PATH_USERS = "users/";
    DatabaseReference myRef;
    DatabaseReference mRootReference;
    Button enviar;
    EditText mensaje;
    FirebaseDatabase database;
    ListView mensajesChatsList;
    String keyChat = new String();
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        enviar= findViewById(R.id.enviarmsj);
        mensaje = findViewById(R.id.campoMensaje);
        mensajesChatsList = findViewById(R.id.listaMensajes);
        database = FirebaseDatabase.getInstance();
        buscar();
        Pedido pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        if(pedido != null){
            userId = pedido.getUsuPedido();
            keyChat = pedido.getIdChat();
        }

        mostrarMsjs(pedido);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUsr = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUsr.getUid();
                String msjtext = mensaje.getText().toString();
                Toast.makeText(v.getContext(),"entroooo" + msjtext,Toast.LENGTH_LONG).show();

                if(!(msjtext.trim().equals(""))){
                    myRef = database.getReference(PATH_CHAT);
                    MensajeChat msjChat = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        msjChat = new MensajeChat(msjtext,nombre, LocalDateTime.now().toString());
                    }
                    myRef.child(keyChat).setValue(msjChat);
                    mensaje.setText("");
                    Toast.makeText(v.getContext(),"entroooo",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    List<MensajeChat> mensajes = new ArrayList<>();

    private void mostrarMsjs(Pedido pedido){
        String idchat = pedido.getIdChat();
        Toast.makeText(getBaseContext(),"entroooo"+idchat,Toast.LENGTH_LONG).show();
        mRootReference = FirebaseDatabase.getInstance().getReference(PATH_CHAT+idchat);
        mRootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                    {
                        if( singleSnap.exists() )
                        {
                            MensajeChat msj = dataSnapshot.getValue(MensajeChat.class);
                            if(msj != null){
                                mensajes.add(msj);
                            }
                        }

                    }
                }
                initializeAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeAdapter() {
        ChatAdapter opAdapter = new ChatAdapter(this, R.layout.mensaje_chat_adapter,mensajes);
        mensajesChatsList.setAdapter(opAdapter);
    }
    String nombre = "";
    private void buscar() {
        myRef = database.getReference( PATH_USERS+userId );
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
