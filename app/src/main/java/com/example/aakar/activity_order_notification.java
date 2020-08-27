package com.example.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.aakar.display.ShowOrdersSP;
import com.example.aakar.display.ShowOrdersSP;
import com.example.aakar.display.ShowOrdersSR;
import com.example.aakar.models.Datum;
import com.example.aakar.models.LoginApi;
import com.example.aakar.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_order_notification extends AppCompatActivity {

    public static String get_name, get_address, get_phone_no, get_email, get_order_id, get_gender, get_amount, get_orderstatus, getDate, getDescription;
    TextView name, address, orderid, phone_no, email, gender, amount, dateTime, ageService;
    Button accept, declined, completeOrder;
    ProgressBar loader;
    ImageView backbutton1;
    LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_notification);
        buttonLayout = findViewById(R.id.buttonLayout);
        completeOrder = findViewById(R.id.complete_order);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();


        Log.e("order status", get_orderstatus);

        if (get_orderstatus.contains("2")) {
            completeOrder.setVisibility(View.GONE);
        }

        //Log.e("name", get_name);

        name = findViewById(R.id.lbl_name);
        orderid = findViewById(R.id.lbl_orders);
        address = findViewById(R.id.lbl_address);
        phone_no = findViewById(R.id.lbl_phnno);
        email = findViewById(R.id.lbl_email);
        gender = findViewById(R.id.lbl_gender);
        amount = findViewById(R.id.lbl_amount);
        backbutton1 = findViewById(R.id.backbutton);
        dateTime = findViewById(R.id.lbl_time);
        ageService = findViewById(R.id.lbl_age);


        loader = findViewById(R.id.orderNotifyLoader);


        name.setText(get_name);
        orderid.setText(get_order_id);
        address.setText(get_address);
        phone_no.setText(get_phone_no);
        email.setText(get_email);
        gender.setText(get_gender);
        amount.setText(get_amount);
        dateTime.setText(getDate);
        ageService.setText(getDescription);


        if (get_orderstatus.contains("1")) {
            buttonLayout.setVisibility(View.GONE);
            completeOrder.setVisibility(View.VISIBLE);
        }
        if (get_orderstatus.contains("2")) {
            buttonLayout.setVisibility(View.GONE);
            completeOrder.setVisibility(View.GONE);
        }


        accept = findViewById(R.id.btn_accept);
        declined = findViewById(R.id.btn_deny);

        backbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity_order_notification.this, ShowOrdersSP.class);
                startActivity(i);
                finish();
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSp("1");
                get_orderstatus = "1";
            }
        });

        declined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeOrder.setVisibility(View.GONE);
                bookSp("2");
                get_orderstatus = "2";
            }
        });

        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeOrders("1");
            }
        });

    }

    public void onBackPressed()
    {
        Integer backpress = 0;
        backpress = (backpress + 1);
        Intent i = new Intent(activity_order_notification.this, ShowOrdersSP.class);
        startActivity(i);
        finish();
    }

    private void bookSp(final String status) {
        loader.setVisibility(View.VISIBLE);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "updateOrdersDetail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json data is", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                LoginApi userList = mGson.fromJson(response, LoginApi.class);
                List<Datum> sam = userList.getDetails();
                if (userList.getSuccess() == 1) {
                    loader.setVisibility(View.INVISIBLE);
                    buttonLayout.setVisibility(View.GONE);

                    if (get_orderstatus.contains("2")) {
                        completeOrder.setVisibility(View.GONE);
                    } else {
                        completeOrder.setVisibility(View.VISIBLE);
                    }

                    //completeOrder.setVisibility(View.VISIBLE);

                    new AlertDialog.Builder(activity_order_notification.this)
                            .setTitle("Success")
                            .setMessage("Your Order Status Has Been Updated")
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
                params.put("orderid", get_order_id);
                params.put("status", status);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void completeOrders(final String status) {
        loader.setVisibility(View.VISIBLE);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.base_url + "updateCompleteStatus", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json data is", response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                LoginApi userList = mGson.fromJson(response, LoginApi.class);
                List<Datum> sam = userList.getDetails();
                if (userList.getSuccess() == 1) {
                    loader.setVisibility(View.INVISIBLE);
                    buttonLayout.setVisibility(View.GONE);

                    completeOrder.setVisibility(View.GONE);

                    new AlertDialog.Builder(activity_order_notification.this)
                            .setTitle("Success")
                            .setMessage("Order Is Completed")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else {
                    Log.e("error",  "ErrorOccured");
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
                params.put("orderid", get_order_id);
                params.put("status", status);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


}
