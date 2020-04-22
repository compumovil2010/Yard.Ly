package com.example.yardly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalificarOpinion extends AppCompatActivity {
    EditText comentario;
    Button addComment , cancelComment;
    float calif = 0;
    Resena resena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_opinion);
        resena = (Resena) getIntent().getSerializableExtra("resena");
        comentario = findViewById(R.id.ComentarioText);
        addComment = findViewById(R.id.addCommentFinal);
        cancelComment = findViewById(R.id.cancelComment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comentario.getText().toString().trim().equals("")){
                    Intent inte = new Intent(getBaseContext(),Calificar.class);
                    resena.setOpinion(comentario.getText().toString());
                    inte.putExtra("resena",resena);
                    startActivity(inte);
                }
            }
        });
        cancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getBaseContext(),Calificar.class);
                resena.setOpinion(" ");
                inte.putExtra("resena",resena);
                startActivity(inte);
            }
        });
    }
}
