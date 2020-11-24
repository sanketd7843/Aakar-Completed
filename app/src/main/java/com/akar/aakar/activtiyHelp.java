package com.akar.aakar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class activtiyHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageView btnback = findViewById(R.id.backbutton);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activtiyHelp.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(activtiyHelp.this, MainActivityReceiver.class);
        startActivity(i);
        finish();

    }

}
