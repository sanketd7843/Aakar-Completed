package com.akar.aakar.display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.akar.aakar.MainActivityReceiver;
import com.akar.aakar.R;
import com.akar.aakar.adapters.orderSRAdapter;
import com.akar.aakar.models.CityApi;
import com.akar.aakar.models.ordersSR;
import com.akar.aakar.utils.Constants;
import com.akar.aakar.utils.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowOrdersSR extends AppCompatActivity {
    ProgressBar progressBar;
    SharedHelper _appPrefs;
    String city;
    String price = "100000";

    Spinner citySpinner;

    CityApi[] data;
    List<String> cities = new ArrayList<>();

    String selectedCity = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        citySpinner = findViewById(R.id.spn_city11);
        progressBar = findViewById(R.id.srorderLoader);
        setContentView(R.layout.activity_my_orders_s_r);
        _appPrefs = new SharedHelper(getApplicationContext());
        onStart();
        ImageView btnback = findViewById(R.id.backbuttonorder);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShowOrdersSR.this, MainActivityReceiver.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String URL = Constants.base_url + "getSrOrders";
        final RecyclerView recyclerView = findViewById(R.id.myOrdersRvSr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("tag", String.valueOf(response));
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ordersSR[] orderSRApis = gson.fromJson(response, ordersSR[].class);

                recyclerView.setAdapter(new orderSRAdapter(ShowOrdersSR.this, orderSRApis));

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
        Intent i = new Intent(ShowOrdersSR.this, MainActivityReceiver.class);
        startActivity(i);
        finish();

    }

}