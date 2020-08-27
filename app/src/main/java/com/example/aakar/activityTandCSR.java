package com.example.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aakar.utils.Constants;

public class activityTandCSR extends AppCompatActivity {
    WebView aboutweb;
    private ProgressDialog progressBar;
    String TAG = "testing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tand_c_s_r);
        ImageView back = findViewById(R.id.backbuttonsr);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityTandCSR.this, MainActivityReceiver.class);
                startActivity(i);
                finish();
            }
        });

        aboutweb = findViewById(R.id.tandc);

        WebSettings settings = aboutweb.getSettings();
        settings.setJavaScriptEnabled(true);
        aboutweb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(activityTandCSR.this, "Terms and condition", "Loading...");

        aboutweb.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(activityTandCSR.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        aboutweb.loadUrl(Constants.base_url_tc);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(activityTandCSR.this, MainActivityReceiver.class);
        startActivity(i);
        finish();

    }
}
