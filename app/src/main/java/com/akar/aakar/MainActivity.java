package com.akar.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akar.aakar.display.ShowOrdersSP;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("NurseApp", MODE_PRIVATE);

        LinearLayout myorders = findViewById(R.id.main_orders);
        LinearLayout notification = findViewById(R.id.main_notification);
        LinearLayout share = findViewById(R.id.llout_share);
        LinearLayout aboutus = findViewById(R.id.main_au_sr);
        TextView tandc = findViewById(R.id.main_tc);
        LinearLayout profile = findViewById(R.id.main_profile);

        Button logout = findViewById(R.id.main_logout);

        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowOrdersSP.class);
                startActivity(i);
                finish();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, activityNotifcations.class);
                startActivity(i);
                finish();
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, activityAboutUs.class);
                startActivity(i);
                finish();
            }
        });
        tandc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, acctivityTandC.class);
                startActivity(i);
                finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, activityProfile.class);
                startActivity(i);
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, activityShare.class);
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
                                Toast.makeText(getApplicationContext(), "Logout Successful",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this, activityLoginOption.class);
                                startActivity(i);
                                finish();
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
