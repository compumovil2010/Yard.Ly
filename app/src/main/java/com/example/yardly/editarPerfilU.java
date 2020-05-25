package com.example.yardly;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import Modelo.Usuario;

public class editarPerfilU extends AppCompatActivity {

    Button guardar,cancelar;
    ImageButton foto;
    EditText nomb, email;
    public static final int MY_PERMISSION_READ_STROAGE = 32;
    public static final int MY_PERMISSION_CAMERA = 33;
    private Bitmap fotoPerfil=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_u);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        nomb=findViewById(R.id.nombreUsRe);
        email=findViewById(R.id.mailUsRe);
        foto=findViewById(R.id.selecFotperf);
        guardar=findViewById(R.id.btnGuardarP);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
                           }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v.getContext());
            }
        });
        cancelar=findViewById(R.id.btnCancP);
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                devolver();
            }
        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + user.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario u=dataSnapshot.getValue(Usuario.class);
                final File localFile;
                try {
                    localFile = File.createTempFile("images", "jpg");
                    StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(Usuario.PATH_PORFILE_PHOTO+"/"+user.getUid()+".png");

                    imageRef.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    foto.setImageBitmap(myBitmap);
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
    }

    private void updateUser() {
        if(fotoPerfil!=null){
            uploadImageandSaveUri(fotoPerfil);
        }
        if(!nomb.getText().toString().isEmpty())
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/nombre");
            ref.setValue(nomb.getText().toString());
        }
        if(!email.getText().toString().isEmpty())
        {
            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/mail");
            ref2.setValue(email.getText().toString());
            Log.i("UPDT", "Entra "+email.getText().toString());
            FirebaseAuth.getInstance().getCurrentUser().updateEmail(email.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("UPDT", "User email address updated.");
                            }
                        }
                    });
        }
        Intent i= new Intent(this,Principal.class);
        startActivity(i);

    }
    private void uploadImageandSaveUri(Bitmap fotoSubir){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final StorageReference storage = FirebaseStorage.getInstance().getReference()
                .child(Usuario.PATH_PORFILE_PHOTO).child(user.getUid() + ".png");
        fotoSubir.compress(Bitmap.CompressFormat.PNG,100,baos);
        storage.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDowloadUri(storage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Upload","Failure", e.getCause());
            }
        });
    }
    public  void  getDowloadUri(StorageReference storage){
        storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                updateUriFirebase(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Upload","Failure", e.getCause());
            }
        });
    }
    public void updateUriFirebase(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(), "Imagen subida",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void devolver()
    {
        this.onBackPressed();
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Tomar Foto", "Escoger de la galería","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Escoge tu foto de perfil");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Tomar Foto")) {

                    pedirPermiso(Manifest.permission.CAMERA, "Es necesario para tomar la foto");

                } else if (options[item].equals("Escoger de la galería")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSION_READ_STROAGE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage(this);
                }
                else {
                    Toast t = Toast.makeText(this, "No es posible acceder a las fotos", Toast.LENGTH_LONG);
                    t.show();
                }
                return;
            }
            case MY_PERMISSION_CAMERA:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else {
                    Toast t = Toast.makeText(this, "No es posible acceder a las fotos", Toast.LENGTH_LONG);
                    t.show();
                }
                return;
            }

        }
    }@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        foto.setImageBitmap(selectedImage);
                        fotoPerfil = selectedImage;
                        //newUser.setFotoPerfil(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        try {
                            Uri imageUri = data.getData();
                            InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            foto.setImageBitmap(selectedImage);
                            fotoPerfil = selectedImage;
                            //newUser.setFotoPerfil(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
    }
    public void pedirPermiso(String permiso, String justificacion)
    {
        if(ContextCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permiso)){
                Toast ration = Toast.makeText(this, justificacion, Toast.LENGTH_LONG);
                ration.show();
            }
            switch (permiso){
                case Manifest.permission.READ_EXTERNAL_STORAGE:{
                    ActivityCompat.requestPermissions(this, new String[]{permiso}, MY_PERMISSION_READ_STROAGE);
                    return;
                }
                case Manifest.permission.CAMERA:{
                    ActivityCompat.requestPermissions(this, new String[]{permiso}, MY_PERMISSION_CAMERA);
                    return;
                }
            }
        }
        else{
            selectImage(this);
        }
    }
}
