package com.akar.aakar;

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

import com.akar.aakar.utils.Constants;

public class activityAboutUs extends AppCompatActivity {

    WebView aboutweb;
    private ProgressDialog progressBar;
    String TAG = "testing";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        aboutweb = findViewById(R.id.webViewAboutUS);

        ImageView back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityAboutUs.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        WebSettings settings = aboutweb.getSettings();
        settings.setJavaScriptEnabled(true);
        aboutweb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(activityAboutUs.this, "About Us", "Loading...");

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
                Toast.makeText(activityAboutUs.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        aboutweb.loadUrl(Constants.base_url_about);
    }

    public void onBackPressed(){
        Integer backpress = 0;
        backpress = (backpress + 1);
        Intent i = new Intent(activityAboutUs.this, MainActivity.class);
        startActivity(i);
        finish();

    }


}
