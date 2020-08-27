package com.example.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aakar.models.Datum;
import com.example.aakar.models.LoginApi;
import com.example.aakar.utils.Constants;
import com.example.aakar.utils.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activityLoginSP extends AppCompatActivity {

    private SharedHelper _appPrefs;
    EditText etphoneNoSp;
    EditText etpasswordSp;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_login);

        etphoneNoSp = findViewById(R.id.et_phnno);
        etpasswordSp = findViewById(R.id.et_password);
        Button loginSp = findViewById(R.id.et_loginsp);
        TextView signupSp = findViewById(R.id.tv_signupsp);
        loader = findViewById(R.id.spLoginLoader);

        _appPrefs = new SharedHelper(getApplicationContext());

        loginSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lognApi();
            }
        });

        signupSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activityLoginSP.this, activitySignupSP.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void lognApi(){
        loader.setVisibility(View.VISIBLE);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url+"loginSp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json data is", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                LoginApi userList = mGson.fromJson(response, LoginApi.class);
                List<Datum> sam = userList.getDetails();
                if(userList.getSuccess() == 2){
                    //new logic
                    Gson gson = new Gson();
                    String json = gson.toJson(sam);
                    JsonElement jelement = new JsonParser().parse(json);
                    JsonArray jarray = jelement.getAsJsonArray();
                    JsonObject jobject = jarray.get(0).getAsJsonObject();
                    String uemail = jobject.get("email").getAsString();
                    String upass = jobject.get("password").getAsString();
                    String ufname = jobject.get("full_name").getAsString();
                    String uprof = jobject.get("photo").getAsString();
                    String umobs = jobject.get("mobile_no").getAsString();
                    String ugender = jobject.get("gender").getAsString();
                    String city = jobject.get("city").getAsString();
                    String hourly_rate = jobject.get("hourly_rate").getAsString();
                    String aadhar_card = jobject.get("aadhar_card").getAsString();
                    String uid = jobject.get("id").getAsString();
                    String weeklyrate = jobject.get("weekly_rate").getAsString();
                    String dailyrate = jobject.get("daily_rate").getAsString();
                    String monthlyrate = jobject.get("monthly_rate").getAsString();
                    String edu = jobject.get("qualification").getAsString();
                    String service = jobject.get("type_of_service").getAsString();
                    //Integer verified = jobject.get("verified").getAsInt();

                    //Log.e("the value is", verified.toString());
                    _appPrefs.saveUserdetails(uid, uemail, upass, ufname, city, umobs, ugender, hourly_rate, 1, aadhar_card, uprof, dailyrate, weeklyrate, monthlyrate, true, edu, service);
                    loader.setVisibility(View.INVISIBLE);
                    _appPrefs.setType(true);
                    Intent i = new Intent(activityLoginSP.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(userList.getSuccess() == 1){
                    loader.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(activityLoginSP.this)
                            .setTitle("Alert")
                            .setMessage(userList.getMessage())
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
                else{
                    loader.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(activityLoginSP.this)
                            .setTitle("Incorrect Credentials")
                            .setMessage("Please Check Your Credentials")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.i("Error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("mobile_no", etphoneNoSp.getText().toString());
                params.put("password", etpasswordSp.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
    public void onBackPressed(){
        Integer backpress = 0;
        backpress = (backpress + 1);
        Intent i = new Intent(activityLoginSP.this, activityLoginOption.class);
        startActivity(i);
        finish();

    }

}
