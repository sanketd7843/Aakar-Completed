package com.akar.aakar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akar.aakar.utils.SharedHelper;

public class activityProfile extends AppCompatActivity {
    private SharedHelper _appPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        _appPrefs = new SharedHelper(getApplicationContext());

        TextView nameprofile = findViewById(R.id.tv_profile_SP_name);
        TextView city = findViewById(R.id.tv_profile_SP_address);
        TextView phnnoprofile = findViewById(R.id.tv_profile_SP_phnno);
        TextView emailprofile = findViewById(R.id.tv_profile_SP_email);
        TextView updateProfile = findViewById(R.id.tv_profile_SP_updateProfile);
        TextView hourlyRate  =findViewById(R.id.tv_profile_SP_hourlyRate);
        TextView education  =findViewById(R.id.tv_profile_SP_education);
        TextView typeOfService  =findViewById(R.id.tv_profile_SP_typeOfService);

        TextView dailyRate  =findViewById(R.id.tv_profile_SP_dailyRate);
        TextView weeklyRate  =findViewById(R.id.tv_profile_SP_weeklyRate);
        TextView monthlyRate  =findViewById(R.id.tv_profile_SP_mothlyRate);

        nameprofile.setText(_appPrefs.getFullName());
        phnnoprofile.setText(_appPrefs.getMobileNumber());

        emailprofile.setText(_appPrefs.getEmail());
        phnnoprofile.setText(_appPrefs.getMobileNumber());
        hourlyRate.setText(_appPrefs.gethourlyPrice());
        dailyRate.setText(_appPrefs.getDailyRate());
        weeklyRate.setText(_appPrefs.getWeeklyRate());
        education.setText(_appPrefs.geteducation());
        typeOfService.setText(_appPrefs.gettypeOfService());
        monthlyRate.setText(_appPrefs.getMonthlyRate());

        city.setText(_appPrefs.getCity());



        ImageView btnback = findViewById(R.id.backbutton);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityProfile.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityProfile.this, activityEditProfileSP.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(activityProfile.this, MainActivity.class);
        startActivity(i);
        finish();

    }



}
