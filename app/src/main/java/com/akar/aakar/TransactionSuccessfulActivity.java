package com.akar.aakar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akar.aakar.utils.SharedHelper;

public class TransactionSuccessfulActivity extends AppCompatActivity {

    Button complete;
    ImageView back;
    TextView amount;
    private SharedHelper _appPrefs;
    public static String get_price = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_successful);

        complete = findViewById(R.id.completeButton);
        amount = findViewById(R.id.amountPayment);
        back = findViewById(R.id.backbutton);

        _appPrefs = new SharedHelper(getApplicationContext());

        Log.e("price ",_appPrefs.getPayPrice() );
        amount.setText(get_price);

        Log.e("price ",_appPrefs.getPayPrice() );
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransactionSuccessfulActivity.this, MainActivityReceiver.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransactionSuccessfulActivity.this, MainActivityReceiver.class);
                startActivity(i);
                finish();
            }
        });
    }
}