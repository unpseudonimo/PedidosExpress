package com.example.pedidosexpress.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pedidosexpress.R;

public class FeedConsumidor extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_consumidor); // Establece el layout de la actividad

        TextView btnmapa = findViewById(R.id.btnmapa);

        btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedConsumidor.this, MapaActivity.class);
                startActivity(intent);
            }
        });
    }
}
