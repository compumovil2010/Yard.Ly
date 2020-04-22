package com.example.yardly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yardly.R;

import java.util.List;

public class OpinionesAdapter extends ArrayAdapter<Resena> {
    private int LayoutUso;
    private Context contex;
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
            usuarioComentario.setText(val.getUsuario());
            asunto.setText(val.getPuntaje());
            comentario.setText(val.getOpinion());
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
