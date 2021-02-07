package com.akar.aakar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.akar.aakar.display.ShowOrdersSR;
import com.akar.aakar.display.activityAllServiceProviders;

public class MainActivityReceiver extends AppCompatActivity {

    AlertDialog.Builder builder;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_forrecever);
        sp = getSharedPreferences("NurseApp",MODE_PRIVATE);
        LinearLayout serviceProvides = findViewById(R.id.main_allServiceProvidersSR);
        LinearLayout notificationSR = findViewById(R.id.main_notificationSR);
        LinearLayout ordersSR = findViewById(R.id.main_ordersSR);
        LinearLayout profileSR = findViewById(R.id.main_profileSR);
        LinearLayout aboutUsSR = findViewById(R.id.aboutusSR);
        LinearLayout shareSR = findViewById(R.id.llout_shareSR);
        LinearLayout termsSR = findViewById(R.id.llout_termsSR);

        Button logout = findViewById(R.id.main_logoutSR);


        shareSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityReceiver.this,activityShareReceiver.class);
                startActivity(i);
                finish();
            }
        });
        ordersSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityReceiver.this, ShowOrdersSR.class);
                startActivity(i);
                finish();
            }
        });
        serviceProvides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityReceiver.this, activityAllServiceProviders.class);
                startActivity(i);
                finish();
            }
        });
        notificationSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityReceiver.this, activityNotifcationsSR .class);
                startActivity(i);
                finish();
            }
        });
        profileSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityReceiver.this, activityProfileSR.class);
                startActivity(i);
                finish();
            }
        });
        aboutUsSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityReceiver.this, activityAboutUsSR.class);
                startActivity(i);
                finish();
            }
        });
        termsSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityReceiver.this, activityTandCSR.class);
                startActivity(i);
                finish();
            }
        });

        builder = new AlertDialog.Builder(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Do you want logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sp.edit().clear().commit();
                                Intent i = new Intent(MainActivityReceiver.this,activityLoginOption.class);
                                Toast.makeText(getApplicationContext(), "Logout Successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Logout Not Successful",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Close?");
                alert.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        int backpress = 0;
        backpress = (backpress + 1);
        Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();
        if (backpress > 1) {
            this.finish();
        }
    }
}
