package com.example.yardly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import java.text.DecimalFormat;

import Modelo.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class Tab_profile extends Fragment {
    CircleImageView fotoP;
    TextView nom, km, info, infoest;
    private FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_profile, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fotoP=v.findViewById(R.id.perfil);
        nom=v.findViewById(R.id.nomp);
        km=v.findViewById(R.id.km);
        info=v.findViewById(R.id.info);
        infoest=v.findViewById(R.id.infoest);
        if(user == null){
            Intent inte = new Intent(v.getContext(), logActivity.class);
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inte);
        }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + user.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario u=dataSnapshot.getValue(Usuario.class);
                String s =u.getNombre();
                nom.setText(s);
                DecimalFormat formatter = new DecimalFormat("#0.00");
                float kmm = u.getKmRecorridos();
                km.setText(formatter.format(kmm)+" km");
                final File localFile;
                try {
                    localFile = File.createTempFile("images", "jpg");
                    StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(Usuario.PATH_PORFILE_PHOTO+"/"+user.getUid()+".png");

                    imageRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    fotoP.setImageBitmap(myBitmap);
                                    Log.i("IMG", "succesfully downloaded");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.i("IMG", "No hay Imagen");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
}
