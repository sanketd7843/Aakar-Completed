package com.akar.aakar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.akar.aakar.adapters.notificationAdapter;
import com.akar.aakar.models.NotificationModel;
import com.akar.aakar.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class activityNotifcations extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ImageView btnback = findViewById(R.id.backbutton);
        progressBar = findViewById(R.id.notificationPB);
        onStart();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityNotifcations.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(activityNotifcations.this, MainActivity.class);
        startActivity(i);
        finish();

    }    @Override
    protected void onStart() {
        super.onStart();
        String URL = Constants.base_url+"getNotification";
        final RecyclerView recyclerView = findViewById(R.id.notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                Log.d("tag",String.valueOf(response) );

                GsonBuilder gsonBuilder =new GsonBuilder();
                Gson gson = gsonBuilder.create();
                NotificationModel[] notificationModels =  gson.fromJson(response, NotificationModel[].class);

                recyclerView.setAdapter(new notificationAdapter(activityNotifcations.this, notificationModels));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("srid", "69");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }

}
