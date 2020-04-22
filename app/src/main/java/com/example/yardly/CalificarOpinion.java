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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_opinion);
        calif = getIntent().getFloatExtra("calificacion",0);
        Toast.makeText(getBaseContext(),String.valueOf(calif),Toast.LENGTH_SHORT).show();
        comentario = findViewById(R.id.ComentarioText);
        addComment = findViewById(R.id.addCommentFinal);
        cancelComment = findViewById(R.id.cancelComment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comentario.getText().toString().equals(" ")){
                    Intent inte = new Intent(getBaseContext(),Calificar.class);
                    inte.putExtra("calificacion",calif);
                    inte.putExtra("comentario",comentario.getText().toString());
                    startActivity(inte);
                }
            }
        });
        cancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getBaseContext(),Calificar.class);
                inte.putExtra("calificacion",calif);
                startActivity(inte);
            }
        });
    }
}
