package com.example.yardly;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import Modelo.Usuario;

public class RegistroFoto extends AppCompatActivity {
    private Button signup;
    private static FirebaseAuth authentication;
    private static FirebaseDatabase database;
    private static DatabaseReference reference;
    private StorageReference storage;
    private ImageButton foto ;
    private Usuario newUser;
    private TextView cancelar;
    private Bitmap fotoPerfil;
    private CheckBox tyc, pp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authentication = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_registro_foto);
        cancelar=findViewById(R.id.botonCancelar);
        signup=findViewById(R.id.botonRegistrarFoto);
        foto = findViewById(R.id.selecFotperf);
        tyc=findViewById(R.id.terminos);
        pp=findViewById(R.id.politica);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos.pedirPermisos(RegistroFoto.this,Manifest.permission.READ_EXTERNAL_STORAGE, "Es necesario para seleccionar la foto",permisos.PERMISSION_STORAGE_ID.ordinal());
            }
        });
        final Bundle datosUs = this.getIntent().getBundleExtra("datosUs");
        newUser = new Usuario();
        newUser.setNombre(datosUs.getString("nombre"));
        newUser.setMail(datosUs.getString("mail"));
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registerUser(datosUs);

            }
        });
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Tomar Foto", "Escoger de la galería","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Escoge tu foto de perfil");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Tomar Foto")) {

                    permisos.pedirPermisos(RegistroFoto.this,Manifest.permission.CAMERA, "Es necesario para tomar la foto",permisos.PERMISSION_CAMERA_ID.ordinal());

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

        if(requestCode==permisos.PERMISSION_STORAGE_ID.ordinal()){
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage(this);
                }
                else {
                    Toast t = Toast.makeText(this, "No es posible acceder a las fotos", Toast.LENGTH_LONG);
                    t.show();
                }
            }
            if(requestCode==permisos.PERMISSION_CAMERA_ID.ordinal()){
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else {
                    Toast t = Toast.makeText(this, "No es posible acceder a las fotos", Toast.LENGTH_LONG);
                    t.show();
                }
            }
    }

    @Override
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

    private void registerUser(Bundle datosUs){
        String s= null;
        if(!tyc.isChecked()&& !pp.isChecked())
            s="Acepte los terminos y condiciones, y las politicas de privacidad";
        else if (!tyc.isChecked())
            s="Acepte los terminos y condiciones";
        else if (!pp.isChecked())
            s="Acepte las politicas de privacidad";
        if(s!=null)
        {
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
            return;
        }
            authentication.createUserWithEmailAndPassword(datosUs.getString("mail"), datosUs.getString("contrasena")).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = authentication.getCurrentUser();
                        if(user!=null){ //Update user Info
                            if(fotoPerfil != null)
                                uploadImageandSaveUri(fotoPerfil);
                            reference = database.getReference(Usuario.PATH_USERS+ user.getUid());
                            reference.setValue(newUser);
                            actualizarUI(user);
                        }
                    }
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegistroFoto.this, "Authenticación falló"+ task.getException().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void uploadImageandSaveUri(Bitmap fotoSubir){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FirebaseUser user = authentication.getCurrentUser();
        storage = FirebaseStorage.getInstance().getReference()
                .child(Usuario.PATH_PORFILE_PHOTO).child(user.getUid()+".png");
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
                newUser.setFotoPerfil(uri);
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
        FirebaseUser user = authentication.getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(), "Imagen subida",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void actualizarUI(FirebaseUser usuario){
        if(usuario != null){
            Intent ingreso = new Intent(getBaseContext(),Principal.class);
            ingreso.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ingreso.putExtra("user", usuario.getEmail());
            startActivity(ingreso);
        }
    }
}
