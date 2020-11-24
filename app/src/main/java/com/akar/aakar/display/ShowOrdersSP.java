package com.akar.aakar.display;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.akar.aakar.MainActivity;
import com.akar.aakar.R;
import com.akar.aakar.adapters.orderSRAdapter;
import com.akar.aakar.models.ordersSR;
import com.akar.aakar.utils.Constants;
import com.akar.aakar.utils.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class ShowOrdersSP extends AppCompatActivity {
    ProgressBar progressBar;
    SharedHelper _appPrefs;
    public String package_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_s_r);
        _appPrefs = new SharedHelper(getApplicationContext());
        Log.e("id is: ", _appPrefs.getuserId().toString());

        onStart();
        ImageView btnback = findViewById(R.id.backbuttonorder);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowOrdersSP.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String URL = Constants.base_url + "getSpOrders";
        final RecyclerView recyclerView = findViewById(R.id.myOrdersRvSr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressBar = findViewById(R.id.srorderLoader);
                progressBar.setVisibility(View.GONE);
                Log.d("tag", String.valueOf(response));

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ordersSR[] orderSPApis = gson.fromJson(response, ordersSR[].class);

                recyclerView.setAdapter(new orderSRAdapter(ShowOrdersSP.this, orderSPApis));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("srid", _appPrefs.getuserId().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ShowOrdersSP.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}