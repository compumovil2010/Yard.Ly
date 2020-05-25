package com.example.yardly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yardly.R;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<MensajeChat> {
    private int LayoutUso;
    private Context contex;
    public ChatAdapter(Context context, int resource, List<MensajeChat> msjs){
        super(context,resource,msjs);
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
        MensajeChat mensaje = getItem(position);
        if (mensaje!=null){
            TextView texto = vie.findViewById(R.id.mensajeInd);
            TextView hora = vie.findViewById(R.id.horamsj);
            texto.setText(mensaje.getTexto());
            hora.setText(mensaje.getFechayhora().toString());
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
