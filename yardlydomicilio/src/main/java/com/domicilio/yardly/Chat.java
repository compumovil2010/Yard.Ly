package com.domicilio.yardly;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    public static final String PATH_DOMICILIARIOS = "domiciliario/";
    public static final String CHANNEL_ID_MSJ = "llegoMsj";

    DatabaseReference myRef , myRefMsj;
    DatabaseReference mRootReference;
    Button enviar;
    EditText mensaje;
    FirebaseDatabase database;
    ListView mensajesChatsList;
    String keyChat = new String();
    ImageView perfilDom;
    String userId;
    TextView nombreDomiciliario;
    List<MensajeChat> mensajes = new ArrayList<>();
    Boolean nombreya=false;
    String nombre = "";
    Pedido pedidoaux;
    public int notificacionLlegoMjs = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        enviar= findViewById(R.id.enviarmsj);
        mensaje = findViewById(R.id.campoMensaje);
        mensajesChatsList = findViewById(R.id.listaMensajes);
        perfilDom = findViewById(R.id.fotoDomiciliario);
        database = FirebaseDatabase.getInstance();
        nombreDomiciliario = findViewById(R.id.nombreDomiciliario);
        final Pedido pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        if(pedido != null){
            userId = pedido.getDomi();
            keyChat = pedido.getIdChat();
            mostrarMsjs(pedido);
            try {
                llenarGUI(pedido.getUsuPedido());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    msjChat.setUsuario(userId);
                    myRef =database.getReference(PATH_CHAT+keyChat);
                    String key = myRef.push().getKey();
                    myRef.child(key).setValue(msjChat);
                    mensaje.setText("");
                }
            }
        });
    }
    private void getIdUsuario(String id){
        database=FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Domiciliario.PATH_DOM +id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String aux = dataSnapshot.child("nombre").getValue(String.class) ;
                nombreDomiciliario.setText(aux);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                        perfilDom.setImageBitmap(myBitmap);
                        Log.i("IMG", "succesfully downloaded");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("IMG", "No hay Imagen");
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
    private void createNotificationChannels() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        //SECOND CHANNEL
        CharSequence name2 = getString(R.string.DescripcionMensaje);
        String description2 = getString(R.string.Mensajenuevo);
        int importance2 = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_MSJ, name2, importance2);
        channel2.setDescription(description2);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager2 = getSystemService(NotificationManager.class);
        assert notificationManager2 != null;
        notificationManager2.createNotificationChannel(channel2);
    }
        private void initializeAdapter() {
        Log.i("mnioooooooo","taaaaaaaaaaam: " + mensajes.size());
        Boolean noMas = false;
        if(mensajes.size()>0) {
            ChatAdapter opAdapter = new ChatAdapter(getBaseContext(), R.layout.mensaje_chat_adapter, mensajes);
            for (int i = mensajes.size() - 1; i >= 0 && !noMas; i--){
                if(mensajes.get(i)!=null){
                    if(!(mensajes.get(i).getIdUsuario().equals(userId))){
                        createNotificationChannels();
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(),CHANNEL_ID_MSJ);
                        mBuilder.setSmallIcon(R.mipmap.ly);
                        String title = getString(R.string.DescripcionMensajeNot);
                        String content = mensajes.get(i).getTexto();
                        mBuilder.setContentTitle(title);
                        mBuilder.setContentText(content);
                        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                        notificationManager.notify(notificacionLlegoMjs, mBuilder.build());
                        Intent intNotific = new Intent(getBaseContext(), Chat.class);
                        intNotific.putExtra("pedido",pedidoaux);
                        intNotific.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intNotific,0);
                        mBuilder.setContentIntent(pendingIntent);
                        mBuilder.setAutoCancel(true);
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
        myRef = database.getReference( PATH_DOMICILIARIOS+userId );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    Usuario usu=dataSnapshot.getValue(Usuario.class);
                    if (usu != null){
                        nombre = usu.getNombre() ;
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
