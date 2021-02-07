package com.akar.aakar.display;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akar.aakar.MainActivity;
import com.akar.aakar.MainActivityReceiver;
import com.akar.aakar.R;
import com.akar.aakar.adapters.allSPAdapter;
import com.akar.aakar.models.CityApi;
import com.akar.aakar.models.getSP;
import com.akar.aakar.utils.Constants;
import com.akar.aakar.utils.SharedHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class activityAllServiceProviders extends AppCompatActivity {
    ProgressBar progressBar;
    SharedHelper _appPrefs;
    public String package_id;

    String city;
    String price = "100000";

    Spinner citySpinner;

    CityApi[] data;
    List<String> cities = new ArrayList<>();
    String selectedCity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsp_rv);
        citySpinner = findViewById(R.id.nice_spinner1sp);

        setupDefault2();
        setupDefault3();
        GetCitiesApi();

        onStart();

        ImageView btnback = findViewById(R.id.backbuttonorder);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activityAllServiceProviders.this, MainActivityReceiver.class);
                startActivity(i);
                finish();
            }
        });

        _appPrefs = new SharedHelper(getApplicationContext());
        city = _appPrefs.getCity();
    }

    @Override
    protected void onStart() {
        progressBar = findViewById(R.id.allspLoader);
        progressBar.setVisibility(View.VISIBLE);
        super.onStart();
        String URL = Constants.base_url + "getServiceProviders"; // myordersr = getSpOrders
        final RecyclerView recyclerView = findViewById(R.id.rvAllSP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                Log.d("tag", String.valueOf(response));
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                getSP[] getSPApis = gson.fromJson(response, getSP[].class);

                recyclerView.setAdapter(new allSPAdapter(activityAllServiceProviders.this, getSPApis));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("city", city);
                params.put("price", price);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(activityAllServiceProviders.this, MainActivityReceiver.class);
        startActivity(i);
        finish();

    }

    private void setupDefault2() {
        NiceSpinner spinner = findViewById(R.id.nice_spinner2sp);
        List<String> dataset = new LinkedList<>(Arrays.asList("0-500", "500-1000", "1000-1500", "1500+"));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(activityAllServiceProviders.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    price = "500";
                }
                if (position == 1) {
                    price = "1000";
                }
                if (position == 2) {
                    price = "1500";
                }
                if (position == 3) {
                    price = "5000";
                }
                onStart();
            }
        });
    }

    private void setupDefault3() {
        NiceSpinner spinner = findViewById(R.id.nice_spinner3sp);
        List<String> dataset = new LinkedList<>(Arrays.asList("Completed", "Pending", "Declined", "Payment Completed"));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(activityAllServiceProviders.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void GetCitiesApi() {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "getCity", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.i("hello", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                CityApi[] userList = mGson.fromJson(response, CityApi[].class);

                for (int i = 0; i < userList.length; ++i) {
                    cities.add(userList[i].getCity());
                    adapter.notifyDataSetChanged();
                    //Log.e("hey there",userList[i].getCity());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                selectedCity = cities.get(pos);
                //Log.e("selected", cities.get(pos));
                city = selectedCity;
                onStart();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

}
