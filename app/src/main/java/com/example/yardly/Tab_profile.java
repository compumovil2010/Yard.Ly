package com.example.yardly;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        //llenar cosas

        return v;
    }
}
