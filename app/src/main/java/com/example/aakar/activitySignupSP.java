package com.example.aakar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class activitySignupSP extends AppCompatActivity {

    private SharedHelper _appPrefs;

    ProgressBar loading;
    EditText nameSignupSp;
    EditText passwordSignupSp;
    EditText monNoSignupSp;
    EditText hourlyRateSignupSp, dailyRateSignupSp, weeklyRateSignupSp, monthlyRateSignupSp;
    EditText email, qualification, TypeOfService;
    ImageView adhaarImage;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    String gender;
    String fcm_t = "";
    String FilePath = "";
    Integer isFileSelected = 0;


    Spinner citySpinner;

    CityApi[] data;
    List<String> cities = new ArrayList<>();
    AlertDialog.Builder builder;
    String selectedCity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_singup);
        loading = findViewById(R.id.registerProgressSp);

        _appPrefs = new SharedHelper(getApplicationContext());
        fcm_t = _appPrefs.getFcmToken();
        builder = new AlertDialog.Builder(this);
        Log.e("fcm tok", fcm_t);

        nameSignupSp = findViewById(R.id.et_nameSpRegister);
        passwordSignupSp = findViewById(R.id.et_PasswordSpRegister);
        monNoSignupSp = findViewById(R.id.et_MobNoSpRegister);
        hourlyRateSignupSp = findViewById(R.id.et_HourlyRateSpRegister);
        dailyRateSignupSp = findViewById(R.id.et_DailyRateSpRegister);
        weeklyRateSignupSp = findViewById(R.id.et_WeeklyRateSpRegister);
        monthlyRateSignupSp = findViewById(R.id.et_MonthlyRateSpRegister);
        email = findViewById(R.id.et_EmailSpRegister);
        qualification = findViewById(R.id.et_qualificationSpRegister);
        TextView goToLoginSp = findViewById(R.id.tv_GotoLoginSpRegister);
        adhaarImage = findViewById(R.id.adhaarImage);
        TypeOfService = findViewById(R.id.et_typeOfServiceSpRegister);

        citySpinner = findViewById(R.id.spn_city1);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioGender);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        final ImageView upload = findViewById(R.id.adhaarImage);
        gender = radioSexButton.getText().toString();

        Button signupCompleteSp = findViewById(R.id.btn_SignupSpRegister);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activitySignupSP.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);
                startActivityForResult(intent, 1213);

            }
        });

        signupCompleteSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterApi();
            }
        });
        goToLoginSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activitySignupSP.this, activityLoginSP.class);
                startActivity(i);
                finish();
            }
        });

        GetCitiesApi();

        //code

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
        if ((nameSignupSp.getText().toString().length() == 0) ||
                (email.getText().toString().length() == 0) ||
                (passwordSignupSp.getText().toString().length() == 0) ||
                (monNoSignupSp.getText().toString().length() == 0) ||
                (TypeOfService.getText().toString().length() == 0) ||
                (qualification.getText().toString().length() == 0) ||
                (hourlyRateSignupSp.getText().toString().length() == 0) ||
                (dailyRateSignupSp.getText().toString().length() == 0) ||
                (weeklyRateSignupSp.getText().toString().length() == 0) ||
                (monthlyRateSignupSp.getText().toString().length() == 0) ||
                (isFileSelected == 0)){
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
            Ion.with(activitySignupSP.this)
                    .load(Constants.base_url + "RegisterSp")
                    .setTimeout(60 * 60 * 1000)
                    .setMultipartFile("aadhar_card", new File(FilePath))
                    .setMultipartParameter("email", email.getText().toString())
                    .setMultipartParameter("password", passwordSignupSp.getText().toString())
                    .setMultipartParameter("fullname", nameSignupSp.getText().toString())
                    .setMultipartParameter("mobile_no", monNoSignupSp.getText().toString())
                    .setMultipartParameter("gender", gender)
                    .setMultipartParameter("city", selectedCity)
                    .setMultipartParameter("type_of_service", TypeOfService.getText().toString())
                    .setMultipartParameter("qualification", qualification.getText().toString())
                    .setMultipartParameter("hourly_rate", hourlyRateSignupSp.getText().toString())
                    .setMultipartParameter("daily_rate", dailyRateSignupSp.getText().toString())
                    .setMultipartParameter("weekly_rate", weeklyRateSignupSp.getText().toString())
                    .setMultipartParameter("monthly_rate", monthlyRateSignupSp.getText().toString())
                    .setMultipartParameter("photo", "photo")
                    .setMultipartParameter("status", "0")
                    .setMultipartParameter("fcm_token", fcm_t)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            Log.i("hello", result);
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            SignUpApi userList = mGson.fromJson(result, SignUpApi.class);
                            if (userList.getSuccess() == 1) {
                                loading.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Registered Successfully. Please Wait For Admin Approval", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(activitySignupSP.this, activityLoginOption.class);
                                startActivity(i);
                                finish();
                            }
                            if (Integer.parseInt(userList.getSuccess().toString()) == 0) {
                                loading.setVisibility(View.INVISIBLE);
                                new AlertDialog.Builder(activitySignupSP.this)
                                        .setTitle("User Exists")
                                        .setMessage("The user with same number already exists")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .show();
                            }
                        }
                    });
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(activitySignupSP.this, activityLoginSP.class);
        startActivity(i);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            FilePath = filePath;
            isFileSelected = 1;
            Log.e("file path", FilePath);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            adhaarImage.setImageBitmap(selectedImage);
        }
    }

}
