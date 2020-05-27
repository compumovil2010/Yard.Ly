package com.example.yardly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yardly.R;
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
import java.util.List;

import Modelo.Domiciliario;
import Modelo.Usuario;

public class OpinionesAdapter extends ArrayAdapter<Resena> {
    private int LayoutUso;
    private Context contex;
    private String idXUsu;
    ImageView fotoComentario;
    public OpinionesAdapter (Context context, int resource, List<Resena> opinions){
        super(context,resource,opinions);
        this.LayoutUso=resource;
        this.contex=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vie = convertView;
        if (vie==null){
            vie = LayoutInflater.from(getContex())
                    .inflate(getLayoutUso(), parent, false);
        }
        Resena val = getItem(position);
        if (val!=null){
            TextView asunto = vie.findViewById(R.id.asunto);
            TextView comentario = vie.findViewById(R.id.comentario);
            TextView usuarioComentario = vie.findViewById(R.id.UsuarioComentario);
            fotoComentario= vie.findViewById(R.id.fotoComentario);
            usuarioComentario.setText(val.getUsuario());
            asunto.setText(val.getPuntaje());
            comentario.setText(val.getOpinion());
            idPorUsuario(val.getUsuario());
        }
        return vie;
    }
    public void idPorUsuario(final String correo){
        DatabaseReference mRootReference2 = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS);
        mRootReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() )
                {
                    for( DataSnapshot singleSnap : dataSnapshot.getChildren() )
                    {
                        if( singleSnap.exists() )
                        {
                            Usuario usu = singleSnap.getValue( Usuario.class );
                            if(usu != null){
                                if(usu.getMail().equalsIgnoreCase(correo) )
                                {
                                    idXUsu = singleSnap.getKey();
                                    try {
                                        llenarGUI(idXUsu);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                    }
                }

            }
            private void llenarGUI(String id) throws IOException {
                final File localFile = File.createTempFile("images", "jpg");
                StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(Usuario.PATH_PORFILE_PHOTO+"/"+id+".png");

                imageRef.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                fotoComentario.setImageBitmap(myBitmap);
                                Log.i("IMG", "succesfully downloaded");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.i("IMG", "No hay Imagen");
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public int getLayoutUso() {
        return LayoutUso;
    }

    public void setLayoutUso(int layoutUso) {
        LayoutUso = layoutUso;
    }

    public Context getContex() {
        return contex;
    }

    public void setContex(Context contex) {
        this.contex = contex;
    }
}
