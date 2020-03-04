package com.example.yardly;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.yardly.R;

import java.util.List;

public class OpinionesAdapter extends ArrayAdapter<String> {
    private int LayoutUso;
    private Context contex;
    public OpinionesAdapter (Context context, int resource, List<String> opinions){
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
        String val = getItem(position);
        if (val!=null){
            TextView asunto = vie.findViewById(R.id.asunto);
            asunto.setText("Asunto");
        }
        String val1 = getItem(position);
        if (val!=null){
            TextView comentario = vie.findViewById(R.id.comentario);
            comentario.setText("Comentarios");
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
