package com.example.aakar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aakar.utils.SharedHelper;

public class activityProfileSR extends AppCompatActivity {
    private SharedHelper _appPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_sr);

        _appPrefs = new SharedHelper(getApplicationContext());
        TextView city = findViewById(R.id.tv_profile_address);
        TextView nameprofile = findViewById(R.id.tv_profile_name);
        TextView phnnoprofile = findViewById(R.id.tv_profile_phnno);
        TextView emailprofile = findViewById(R.id.tv_profile_email);
        TextView ordersprofile = findViewById(R.id.tv_profile_orders);
        TextView updateProfile = findViewById(R.id.tv_update_profile);

        nameprofile.setText(_appPrefs.getFullName());
        phnnoprofile.setText(_appPrefs.getMobileNumber());

        emailprofile.setText(_appPrefs.getEmail());
        city.setText(_appPrefs.getCity());
        phnnoprofile.setText(_appPrefs.getMobileNumber());

        ImageView btnback = findViewById(R.id.backbutton);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityProfileSR.this, MainActivityReceiver.class);
                startActivity(i);
                finish();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityProfileSR.this, activityEditProfileSR.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(activityProfileSR.this, MainActivityReceiver.class);
        startActivity(i);
        finish();

    }
}
