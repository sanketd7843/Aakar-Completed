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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aakar.models.CityApi;
import com.example.aakar.models.SignUpApi;
import com.example.aakar.utils.Constants;
import com.example.aakar.utils.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activitySignUpSR extends AppCompatActivity {

    private SharedHelper _appPrefs;

    ProgressBar loading;
    EditText fullName;
    EditText passwordSignupSp;
    EditText monNoSignupSp;
    EditText email;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    String gender;

    Spinner citySpinner;

    CityApi[] data;
    List<String> cities = new ArrayList<>();

    AlertDialog.Builder builder;
    String selectedCity = "";
    String fcm_t = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_singup);

        builder = new AlertDialog.Builder(this);
        _appPrefs = new SharedHelper(getApplicationContext());

        fcm_t = _appPrefs.getFcmToken();
        Log.e("fcm tok", fcm_t);

        fullName = findViewById(R.id.et_nameSignupSR);
        monNoSignupSp = findViewById(R.id.et_phnnoSignupSR);
        passwordSignupSp = findViewById(R.id.et_passSignupSR);
        email = findViewById(R.id.et_emailSignupSR);

        loading = findViewById(R.id.srSignupLoader);

        TextView signupSR = findViewById(R.id.tv_SignupSR);
        TextView gotoLoginSR = findViewById(R.id.tv_goToLoginSR);


        citySpinner = findViewById(R.id.spn_citySR);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        gender = radioSexButton.getText().toString();

        GetCitiesApi();
        signupSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterApi();
            }
        });
        gotoLoginSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activitySignUpSR.this, activityLoginSR.class);
                startActivity(i);
                finish();
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
                loading.setVisibility(View.GONE);
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
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void RegisterApi() {
        if ((email.getText().toString().length() == 0) ||
                (passwordSignupSp.getText().toString().length() == 0) ||
                (fullName.getText().toString().length() == 0) ||
                (monNoSignupSp.getText().toString().length() == 0) ||
                (selectedCity.length() == 0) ||
                (gender.length() == 0)) {
            //Setting message manually and performing action on button click
            builder.setMessage("Please Complete All The Fields")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Alert");
            alert.show();
        } else {
            loading.setVisibility(View.VISIBLE);
            RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "RegisterSr", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("hello", response);
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    SignUpApi userList = mGson.fromJson(response, SignUpApi.class);
                    if (userList.getSuccess() == 1) {
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Registered Successfully Please Login", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activitySignUpSR.this, activityLoginSR.class);
                        startActivity(i);
                        finish();
                    }
                    if (Integer.parseInt(userList.getSuccess().toString()) == 0) {
                        loading.setVisibility(View.INVISIBLE);
                        new AlertDialog.Builder(activitySignUpSR.this)
                                .setTitle("User Exists")
                                .setMessage("The user with same number already exists")
                                .setPositiveButton(android.R.string.ok, null)
                                .show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email.getText().toString());
                    params.put("password", passwordSignupSp.getText().toString());
                    params.put("fullname", fullName.getText().toString());
                    params.put("mobile_no", monNoSignupSp.getText().toString());
                    params.put("gender", gender);
                    params.put("city", selectedCity);
                    params.put("aadhar_card", "Not Available");
                    params.put("hourly_rate", "0");
                    params.put("photo", "photo");
                    params.put("status", "1");
                    params.put("fcm_token", fcm_t);

                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(activitySignUpSR.this, activityLoginSR.class);
        startActivity(i);
        finish();

    }
}
