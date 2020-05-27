package com.example.yardly;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Modelo.Domiciliario;
import Modelo.Usuario;

public class Chat extends AppCompatActivity {
    public static final String PATH_CHAT = "chat/";
    public static final String PATH_USERS = "users/";
    DatabaseReference myRef , myRefMsj;
    DatabaseReference mRootReference;
    Button enviar;
    EditText mensaje;
    FirebaseDatabase database;
    TextView nombreDomiciliario;
    ListView mensajesChatsList;
    ImageView fotoDomi;
    String keyChat = new String();
    String userId;
    Pedido pedidoaux;
    public int notificacionLlegoMjs = 1;
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
        nombreDomiciliario = findViewById(R.id.nombreDomiciliario);
        fotoDomi = findViewById(R.id.fotoDomiciliario);
        database = FirebaseDatabase.getInstance();
        final Pedido pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        if(pedido != null){
            pedidoaux = pedido;
            userId = pedido.getUsuPedido();
            keyChat = pedido.getIdChat();

            try {
                llenarGUI(pedido.getDomi());
            } catch (IOException e) {
                e.printStackTrace();
            }
            getIdDomiciliario(pedido.getDomi());

            if(pedido.getDomi()==null || pedido.getDomi().isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Aun no se ha asignado Domiciliario",Toast.LENGTH_SHORT);
                Intent i= new Intent(this,Principal.class);
                startActivity(i);
                finish();
            }
            mostrarMsjs(pedido);
        }
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msjtext = mensaje.getText().toString();
                String fech = "";
                if(!(msjtext.trim().equals(""))){
                    MensajeChat msjChat = new MensajeChat();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        fech = LocalDateTime.now().toString();
                    }
                    msjChat = new MensajeChat();
                    msjChat.setFechayhora(fech);
                    msjChat.setTexto(msjtext);
                    msjChat.setUsuario(nombre);
                    msjChat.setIdUsuario(userId);
                    myRef =database.getReference(PATH_CHAT+keyChat);
                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(msjChat);
                    mensaje.setText("");
                }
            }
        });
    }
    private void llenarGUI(String id) throws IOException {
        final File localFile = File.createTempFile("images", "jpg");
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(Usuario.PATH_PORFILE_PHOTO+"/"+id+".png");

        imageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        fotoDomi.setImageBitmap(myBitmap);
                        Log.i("IMG", "succesfully downloaded");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("IMG", "No hay Imagen");
            }
        });

    }
    private void getIdDomiciliario(String id){
        database=FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Domiciliario.PATH_DOM +id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String aux = dataSnapshot.child("nombre").getValue(String.class);
                nombreDomiciliario.setText(aux);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void mostrarMsjs(Pedido pedido){
        final String idchat = pedido.getIdChat();
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
        Boolean noMas = false;
        if(mensajes.size()>0) {
            ChatAdapter opAdapter = new ChatAdapter(getBaseContext(), R.layout.mensaje_chat_adapter, mensajes);
            for (int i = mensajes.size() - 1; i >= 0 && !noMas; i--){
                if(mensajes.get(i)!=null){
                    if(!(mensajes.get(i).getIdUsuario().equals(userId))){
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(),Principal.CHANNEL_ID_MSJ);
                        mBuilder.setSmallIcon(R.mipmap.ly);
                        String title = getString(R.string.DescripcionMensajeNot);
                        String content = mensajes.get(i).getTexto();
                        mBuilder.setContentTitle(title);
                        mBuilder.setContentText(content);
                        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                        Intent intNotific = new Intent(getBaseContext(), Chat.class);
                        intNotific.putExtra("pedido",pedidoaux);
                        intNotific.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intNotific,0);
                        mBuilder.setContentIntent(pendingIntent);
                        mBuilder.setAutoCancel(true);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                        notificationManager.notify(notificacionLlegoMjs, mBuilder.build());
                    }
                    else {
                        noMas = true;
                    }
                }
            }
            mensajesChatsList.setAdapter(opAdapter);
        }
    }

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
                Log.i("mnioooooooo","taaaaaaaaaaam: " + mensajes.size());
                initializeAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
