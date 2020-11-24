package com.akar.aakar;

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
import com.akar.aakar.models.Datum;
import com.akar.aakar.models.LoginApi;
import com.akar.aakar.utils.Constants;
import com.akar.aakar.utils.SharedHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activityLoginSR extends AppCompatActivity {
    EditText phoneNoLoginSr;
    EditText passLoginSr;
    ProgressBar loader;
    private SharedHelper _appPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_login);

        _appPrefs = new SharedHelper(getApplicationContext());

        phoneNoLoginSr = findViewById(R.id.et_phnno_sr);
        passLoginSr = findViewById(R.id.et_pass_sr);
        loader = findViewById(R.id.srLoginloader);


        Button login = findViewById(R.id.btn_login_sr);
        TextView goToSignup = findViewById(R.id.tv_goToSignup_Sr);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lognApi();
            }
        });
        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activityLoginSR.this,activitySignUpSR.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void lognApi(){
        loader.setVisibility(View.VISIBLE);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url+"loginSr", new Response.Listener<String>() {
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
                    String hourly_rate = "00";
                    String aadhar_card = "123456";
                    String uid = jobject.get("id").getAsString();
                    //Integer verified = jobject.get("verified").getAsInt();

                    //Log.e("the value is", verified.toString());
                    _appPrefs.saveUserdetails(uid, uemail, upass, ufname, city, umobs, ugender, hourly_rate, 1, aadhar_card, uprof, "", "", "", false, "", "");
                    loader.setVisibility(View.INVISIBLE);
                    _appPrefs.setType(false);
                    Intent i = new Intent(activityLoginSR.this,MainActivityReceiver.class);
                    startActivity(i);
                    finish();
                }
                else if(userList.getSuccess() == 1){
                    loader.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(activityLoginSR.this)
                            .setTitle("Alert")
                            .setMessage(userList.getMessage())
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
                else{
                    loader.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(activityLoginSR.this)
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
                params.put("mobile_no", phoneNoLoginSr.getText().toString());
                params.put("password", passLoginSr.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
    public void onBackPressed(){
        Integer backpress = 0;
        backpress = (backpress + 1);
        Intent i = new Intent(activityLoginSR.this, activityLoginOption.class);
        startActivity(i);
        finish();

    }

}
