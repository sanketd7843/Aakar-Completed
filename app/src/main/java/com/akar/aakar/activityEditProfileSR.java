package com.akar.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.akar.aakar.models.CityApi;
import com.akar.aakar.models.Datum;
import com.akar.aakar.models.LoginApi;
import com.akar.aakar.utils.Constants;
import com.akar.aakar.utils.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activityEditProfileSR extends AppCompatActivity {
    private SharedHelper _appPrefs;

    EditText phnnoprofile, emailprofile, password;
    TextView nameprofile;
    ProgressBar progressBar;
    Spinner citySpinner;
    String city1;

    CityApi[] data;
    List<String> cities = new ArrayList<>();
    String selectedCity = "";

    Button updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_sr);
        _appPrefs = new SharedHelper(getApplicationContext());

        nameprofile = findViewById(R.id.tv_editprofile_SR_name);
        phnnoprofile = findViewById(R.id.tv_editprofile_SR_phnno);
        emailprofile = findViewById(R.id.tv_editprofile_SR_email);
        updateProfile = findViewById(R.id.tv_editprofile_SR_updateProfile);
        password = findViewById(R.id.tv_editprofile_SR_password);
        citySpinner = findViewById(R.id.tv_editprofile_SR_city);
        progressBar = findViewById(R.id.profileEditProgressBarSR);
        nameprofile.setText(_appPrefs.getFullName());

        emailprofile.setText(_appPrefs.getEmail());
        phnnoprofile.setText(_appPrefs.getMobileNumber());
        password.setText(_appPrefs.getPassword());

        GetCitiesApi();

        ImageView btnback = findViewById(R.id.backbutton);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent i = new Intent(activityEditProfileSR.this, activityProfile.class);
                startActivity(i);
                finish();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfileSR();
            }
        });
    }

    public void onBackPressed() {
        Integer backpress = 0;
        backpress = (backpress + 1);
        Intent i = new Intent(activityEditProfileSR.this, activityProfileSR.class);
        startActivity(i);
        finish();

    }


    private void editProfileSR() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "editProfileSr", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json data is", response);
                progressBar.setVisibility(View.GONE);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                LoginApi userList = mGson.fromJson(response, LoginApi.class);
                List<Datum> sam = userList.getDetails();
                if (userList.getSuccess() == 1) {

                    _appPrefs.setemail(emailprofile.getText().toString());
                    _appPrefs.setmobile_no(phnnoprofile.getText().toString());
                    _appPrefs.setpassword(password.getText().toString());
                    _appPrefs.setcity(city1);



                    new AlertDialog.Builder(activityEditProfileSR.this)
                            .setTitle("Success")
                            .setMessage("Your profile has been updated")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else {
                    Log.e("error", "ErrorOccured");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.i("Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", _appPrefs.getuserId().toString());
                params.put("mobile_no", phnnoprofile.getText().toString());
                params.put("email", emailprofile.getText().toString());
                params.put("city", city1);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

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
                city1 = selectedCity;
                onStart();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }


}
