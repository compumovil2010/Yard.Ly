package com.example.yardly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modelo.Usuario;

public class DireccionesAdapter extends ArrayAdapter<String> {
    private int LayoutUso;
    private Context contex;
    public DireccionesAdapter (Context context, int resource, List<String> lugares){
        super(context,resource,lugares);
        this.LayoutUso=resource;
        this.contex=context;
        setNotifyOnChange(true);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vie = convertView;
        if (vie==null){
            vie = LayoutInflater.from(getContex())
                    .inflate(getLayoutUso(), parent, false);
        }
        final String val = getItem(position);
        if (val!=null){
            TextView asunto = vie.findViewById(R.id.Direccion);
            asunto.setText(val);

            ImageButton img = vie.findViewById(R.id.elimdir);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(Usuario.PATH_USERS + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Usuario u=dataSnapshot.getValue(Usuario.class);
                            List<String> mProjection = u.getDirecciones();
                            if(mProjection!=null && val!=null )
                            {
                                mProjection.remove(val);
                            }
                            u.setDirecciones((ArrayList<String>) mProjection);
                            dataSnapshot.getRef().setValue(u);
                            notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
        return vie;
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

