package com.example.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aakar.display.ShowOrdersSR;
import com.example.aakar.models.CityApi;
import com.example.aakar.models.Datum;
import com.example.aakar.models.LoginApi;
import com.example.aakar.utils.Constants;
import com.example.aakar.utils.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activityEditProfileSP extends AppCompatActivity {
    private SharedHelper _appPrefs;
    EditText phnnoprofile, emailprofile,hourlyRate,dailyRate,monthlyRate,weeklyRate, password, education, typeOfService;
    TextView nameprofile;
    Button updateProfile;

ProgressBar progressBar;
    Spinner citySpinner;
    String city;

    CityApi[] data;
    List<String> cities = new ArrayList<>();
    String selectedCity = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_sp);
        _appPrefs = new SharedHelper(getApplicationContext());

        nameprofile = findViewById(R.id.tv_editprofile_SP_name);
        phnnoprofile = findViewById(R.id.tv_editprofile_SP_phnno);
        password = findViewById(R.id.tv_editprofile_SP_password);
        emailprofile = findViewById(R.id.tv_editprofile_SP_email);
        education = findViewById(R.id.tv_editprofile_SP_education);
        typeOfService = findViewById(R.id.tv_editprofile_SP_typeOfService);
        progressBar = findViewById(R.id.profileEditProgressBar);
        citySpinner = findViewById(R.id.tv_editprofile_SP_city);
         updateProfile = findViewById(R.id.tv_editprofile_SP_updateProfile);
         hourlyRate  =findViewById(R.id.tv_editprofile_SP_hourlyRate);
         dailyRate  =findViewById(R.id.tv_editprofile_SP_dailyRate);
         weeklyRate  =findViewById(R.id.tv_editprofile_SP_weeklyRate);
         monthlyRate  =findViewById(R.id.tv_editprofile_SP_monthlyRate);

        nameprofile.setText(_appPrefs.getFullName());
        phnnoprofile.setText(_appPrefs.getMobileNumber());

        emailprofile.setText(_appPrefs.getEmail());
        phnnoprofile.setText(_appPrefs.getMobileNumber());
        password.setText(_appPrefs.getPassword());
        hourlyRate.setText(_appPrefs.gethourlyPrice());
        dailyRate.setText(_appPrefs.getDailyRate());
        weeklyRate.setText(_appPrefs.getWeeklyRate());
        education.setText(_appPrefs.geteducation());
        typeOfService.setText(_appPrefs.gettypeOfService());
        monthlyRate.setText(_appPrefs.getMonthlyRate());

        GetCitiesApi();

        ImageView btnback = findViewById(R.id.backbutton);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (activityEditProfileSP.this, activityProfile.class);
                startActivity(i);
                finish();
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                editProfileSP();
            }
        });
    }
    public void onBackPressed(){
        Integer backpress = 0;
        backpress = (backpress + 1);
        Intent i = new Intent(activityEditProfileSP.this, activityProfile.class);
        startActivity(i);
        finish();
    }

    private void editProfileSP() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "editProfileSp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json data is", response);
                progressBar.setVisibility(View.GONE);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                LoginApi userList = mGson.fromJson(response, LoginApi.class);
                List<Datum> sam = userList.getDetails();
                if (userList.getSuccess() == 1) {

                    _appPrefs.sethourly_rate(hourlyRate.getText().toString());
                    _appPrefs.setdaily_rate(dailyRate.getText().toString());
                    _appPrefs.setweekly_rate(weeklyRate.getText().toString());
                    _appPrefs.setmonthly_rate(monthlyRate.getText().toString());
                    _appPrefs.setemail(emailprofile.getText().toString());
                    _appPrefs.setmobile_no(phnnoprofile.getText().toString());
                    _appPrefs.seteducation(education.getText().toString());
                    _appPrefs.settypeOfService(typeOfService.getText().toString());
                    _appPrefs.setcity(city);


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activityEditProfileSP.this);
                    alertDialogBuilder.setMessage("Profile Updated Succesfully");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent i = new Intent(activityEditProfileSP.this, activityProfile.class);
                                    startActivity(i);
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(activityEditProfileSP.this,"Complete Updating.",Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
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
                params.put("hourly_rate", hourlyRate.getText().toString());
                params.put("daily_rate",dailyRate.getText().toString());
                params.put("weekly_rate", weeklyRate.getText().toString());
                params.put("education", education.getText().toString());
                params.put("type_of_service", typeOfService.getText().toString());
                params.put("monthly_rate",monthlyRate.getText().toString());
                params.put("city",city);
                params.put("email", emailprofile.getText().toString());
                params.put("qualification",education.getText().toString());
                params.put("type_of_service", typeOfService.getText().toString());

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
                city = selectedCity;
                onStart();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }


}
