package com.akar.aakar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akar.aakar.display.activityAllServiceProviders;

public class BookSP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_s_p);

        TextView addressprofileBook = findViewById(R.id.tv_profile_addressBook);
        TextView nameprofileBook = findViewById(R.id.tv_profile_nameBook);
        TextView phnnoprofileBook = findViewById(R.id.tv_profile_phnnoBook);
        TextView emailprofileBook = findViewById(R.id.tv_profile_emailBook);
        TextView ordersprofileBook = findViewById(R.id.tv_profile_ordersBook);
        ImageView profilephotoBook  =findViewById(R.id.iv_profilePhotoBook);

        Button acceptBooking = findViewById(R.id.btn_acceptBook);
        Button declineBooking = findViewById(R.id.btn_declineBook);
        Button complete = findViewById(R.id.complete_payment);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookSP.this , TransactionSuccessfulActivity.class);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(BookSP.this, activityAllServiceProviders.class);
        startActivity(i);
        finish();

    }



}
