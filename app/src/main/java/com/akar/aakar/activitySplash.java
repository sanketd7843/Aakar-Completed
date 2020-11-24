package com.akar.aakar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class activitySplash extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp;
                sp = getSharedPreferences("NurseApp",MODE_PRIVATE);
                SharedPreferences.Editor ed;
                if(!sp.contains("logged")){
                    Intent i = new Intent(activitySplash.this, activityLoginOption.class);
                    startActivity(i);
                    finish();
                }
                else{
                    if(sp.getBoolean("isSp", false)){
                        Intent i = new Intent(activitySplash.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent i = new Intent(activitySplash.this, MainActivityReceiver.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        },3000);

    }
}
