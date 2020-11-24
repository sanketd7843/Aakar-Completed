package com.akar.aakar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activityLoginOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);

        TextView jumpTosp = findViewById(R.id.loginAsSP);
        TextView jumpTosr = findViewById(R.id.loginAsSR);

        jumpTosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activityLoginOption.this, activityLoginSP.class);
                startActivity(i);
                finish();
            }
        });
        jumpTosr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activityLoginOption.this, activityLoginSR.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void onBackPressed(){
        finish();

    }

}
