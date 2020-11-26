package com.akar.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class activityBookFinalSP extends AppCompatActivity {
    String[] country = {"India", "USA", "China", "Japan", "Other"};

    public static String get_name = "";
    public static String get_address = "";
    public static String get_phono = "";
    public static String get_email = "";
    public static String get_id = "";
    public static String get_price = "";
    public static String get_city = "";
    public static String getGet_hourlyrate = "";
    public static String get_dailyRate = "";
    public static String get_weeklyRate = "";
    public static String get_monthlyRate = "";
    public static String duration = "Hourly";
    public static String calculated_amount = "";
    public static String net_amount = "";
    public static String total_amount = "";
    public static String getQualification = "";
    public static String getTypeOfService = "";

    public static Integer testInt = 0;


    TextView name, address, city, pho_no, email, hourlyRate, dailyRate, weeklyRate, monthlyRate, totalrate, qualification, tof;
    Button BookNow;
    ImageView backButton;
    EditText durationTime, age, service;
    ProgressBar loader;

    private SharedHelper _appPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_final_s_p);
        _appPrefs = new SharedHelper(getApplicationContext());

        net_amount = getGet_hourlyrate;
        Log.e( "onCreate: ", net_amount+" here is the value");

        name = findViewById(R.id.tv_profile_nameBookfinal);
        address = findViewById(R.id.tv_profile_addressBookfinal);
        pho_no = findViewById(R.id.tv_profile_phnnoBookfinal);
        email = findViewById(R.id.tv_profile_emailBookfinal);
        backButton = findViewById(R.id.backbutton);
        city = findViewById(R.id.tv_profile_cityBookfinal);
        totalrate = findViewById(R.id.totalRateShow);
        loader = findViewById(R.id.bookProgress);
        qualification = findViewById(R.id.tv_profile_educationBookfinal);
        tof = findViewById(R.id.tv_profile_tofBookfinal);
        hourlyRate = findViewById(R.id.hourlyrate);
        dailyRate = findViewById(R.id.dailyRate);
        durationTime = findViewById(R.id.et_durationTime);
        monthlyRate = findViewById(R.id.monthlyRate);
        weeklyRate = findViewById(R.id.weeklyRate);


        age = findViewById(R.id.et_enterAge);
        service = findViewById(R.id.et_enterService);

        name.setText(get_name);
        address.setText(get_address);
        pho_no.setText(get_phono);
        email.setText(get_email);
        tof.setText(getTypeOfService);
        qualification.setText(getQualification);
        monthlyRate.setText(get_monthlyRate);
        hourlyRate.setText(getGet_hourlyrate);
        weeklyRate.setText(get_weeklyRate);
        dailyRate.setText(get_dailyRate);
        setupDefault();


        BookNow = findViewById(R.id.btn_Bookfinal);

        BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSp();
            }
        });

        //on text change
        durationTime.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            // TextView myOutputBox = (TextView) findViewById(R.id.et_durationTime);
                try{
                    calculated_amount = "";
                    calculated_amount = s.toString();
                    testInt = Integer.parseInt(calculated_amount);
                    Log.e( "onTextChanged: ", calculated_amount+" this is ");
                    if(calculated_amount.matches("")) {
                        testInt = 0;
                        Log.e( "onTextChanged: ", testInt + "in if");

                    }
                    else{
                        total_amount = String.valueOf(Integer.parseInt(net_amount) * Integer.parseInt(calculated_amount));
                        Log.e( "onTextChanged: ", testInt + "in else");
                    }
                    totalrate.setText(total_amount);

                }
                catch(NumberFormatException ex){ // handle your exception
                }  }
        });

    }



    private void bookSp() {
        loader.setVisibility(View.VISIBLE);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "PlaceOrder", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json data is", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                LoginApi userList = mGson.fromJson(response, LoginApi.class);
                List<Datum> sam = userList.getDetails();
                if (userList.getSuccess() == 1) {
                    loader.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activityBookFinalSP.this);
                    alertDialogBuilder.setMessage("Order placed. Would you like to go back?");
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Intent i = new Intent(activityBookFinalSP.this, MainActivityReceiver.class);
                                            startActivity(i);
                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(activityBookFinalSP.this,"Book another order.",Toast.LENGTH_SHORT).show();
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
                params.put("spid", get_id);
                params.put("srid", _appPrefs.getuserId().toString());
                params.put("price", totalrate.getText().toString());
                params.put("spname", name.getText().toString());
                params.put("srname", _appPrefs.getFullName());
                params.put("receiver_city", get_address);
                params.put("receiver_mobile", _appPrefs.getMobileNumber());
                params.put("duration", duration);
                params.put("duration_time", durationTime.getText().toString());
                params.put("description_about", age.getText().toString()+", "+service.getText().toString());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void setupDefault() {
        NiceSpinner spinner = findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("Hourly", "Daily", "Weekly", "Monthly"));
        spinner.attachDataSource(dataset);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(activityBookFinalSP.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
                duration = item.toString();

                if(duration == "Hourly"){
                        net_amount = getGet_hourlyrate;
                    if(calculated_amount.matches("")){
                        total_amount = Integer.toString(Integer.parseInt(net_amount) * 1);
                        totalrate.setText(total_amount);
                    }
                    else {
                        total_amount = Integer.toString(Integer.parseInt(net_amount) * Integer.parseInt(calculated_amount));
                        totalrate.setText(total_amount);
                    }
                    }
                    if(duration == "Daily"){
                        net_amount = get_dailyRate;
                        if(calculated_amount.matches("")){
                            total_amount = Integer.toString(Integer.parseInt(net_amount) * 1);
                            totalrate.setText(total_amount);
                        }
                        else{
                            total_amount = Integer.toString(Integer.parseInt(net_amount) * Integer.parseInt(calculated_amount));
                            totalrate.setText(total_amount);
                        }

                    }
                    if(duration == "Weekly"){
                        net_amount = get_weeklyRate;
                        if(calculated_amount.matches("")){
                            total_amount = Integer.toString(Integer.parseInt(net_amount) * 1);
                            totalrate.setText(total_amount);
                        }
                        else{
                            total_amount = Integer.toString(Integer.parseInt(net_amount) * Integer.parseInt(calculated_amount));
                            totalrate.setText(total_amount);
                        }
                    }
                    if(duration == "Monthly"){

                        net_amount = get_monthlyRate;
                        if(calculated_amount.matches("")){
                            total_amount = Integer.toString(Integer.parseInt(net_amount) * 1);
                            totalrate.setText(total_amount);
                        }
                        else{
                            total_amount = Integer.toString(Integer.parseInt(net_amount) * Integer.parseInt(calculated_amount));
                            totalrate.setText(total_amount);

                        }
                    }


            }
        });
    }
}
