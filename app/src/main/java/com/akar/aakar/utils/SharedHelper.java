package com.akar.aakar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedHelper(Context context) {
        preferences = context.getSharedPreferences("NurseApp", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public SharedHelper() {
    }

    public void saveUserdetails(String id, String email, String password, String full_name, String city, String mobile_no, String gender, String Hourlyprice, Integer logged, String aadhar, String photo, String dailyrate, String weeklyrate, String monthlyrate, Boolean isSp, String education, String typeOfService){
        Integer idd = Integer.parseInt(id);
        editor.putInt("id", idd);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("full_name", full_name);
        editor.putString("mobile_no", mobile_no);
        editor.putString("gender", gender);
        editor.putString("city", city);
        editor.putString("aadhaar", aadhar);
        editor.putString("photo", photo);
        editor.putString("hourlyPrice", Hourlyprice);
        editor.putBoolean("logged", true);
        editor.putString("dailyrate", dailyrate);
        editor.putString("weeklyrate", weeklyrate);
        editor.putString("monthlyrate", monthlyrate);
        editor.putBoolean("isSp", isSp);
        editor.putString("education", education);
        editor.putString("typeOfService", typeOfService);

        editor.commit();
    }
    public Integer getuserId(){
        Integer userid = preferences.getInt("id", 0);
        return userid;
    }
    public Boolean getLoggedIn(){
        Boolean loggedin = preferences.getBoolean("logged", true);
        return loggedin;
    }
    public String getEmail(){
        String email = preferences.getString("email", "123@gmail.com");
        return email;
    }
    public String getPassword(){
        String password = preferences.getString("password", "12345");
        return password;
    }
    public String getFullName(){
        String full_name = preferences.getString("full_name", "John Wick");
        return full_name;
    }
    public String getMobileNumber(){
        String mobile = preferences.getString("mobile_no", "9090909090");
        return mobile;
    }
    public String getGender(){
        String gender = preferences.getString("gender", "None");
        return gender;
    }
    public String getCity(){
        String city = preferences.getString("city", "Nagpur");
        return city;
    }
    public String gethourlyPrice(){
        String email = preferences.getString("hourlyPrice", "1234");
        return email;
    }

    public String getPayPrice(){
        String payPrice = preferences.getString("pay_price", "");
        return payPrice;
    }

    public Boolean setType(Boolean data){
        editor.putBoolean("isSp", data);
        editor.commit();
        return true;
    }
    public String setmobile_no(String mobile_no){
        editor.putString("mobile_no", mobile_no);
        editor.commit();
        return mobile_no;
    }
    public String setPayPrice(String payPrice){
        editor.putString("pay_price", payPrice);
        editor.commit();
        return payPrice;
    }
    public String setpassword(String password){
        editor.putString("password", password);
        editor.commit();
        return password;
    }
    public String sethourly_rate(String hourly_rate){
        editor.putString("hourly_rate", hourly_rate);
        editor.commit();
        return hourly_rate;
    }
    public String setdaily_rate(String daily_rate){
        editor.putString("daily_rate", daily_rate);
        editor.commit();
        return daily_rate;
    }
    public String setweekly_rate(String weekly_rate){
        editor.putString("weekly_rate", weekly_rate);
        editor.commit();
        return weekly_rate;
    }
    public String setmonthly_rate(String monthly_rate){
        editor.putString("monthly_rate", monthly_rate);
        editor.commit();
        return monthly_rate;
    }
    public String setcity(String city){
        editor.putString("city", city);
        editor.commit();
        return city;
    }
    public String setemail(String email){
        editor.putString("email", email);
        editor.commit();
        return email;
    }

    public String seteducation(String education){
        editor.putString("education", education);
        editor.commit();
        return education;
    }

    public String settypeOfService(String typeOfService){
        editor.putString("typeOfService", typeOfService);
        editor.commit();
        return typeOfService;
    }

    public Boolean gettype(){
        Boolean isSp = preferences.getBoolean("isSp", false);
        return isSp;
    }

    public String getFcmToken(){
        String userid = preferences.getString("fcm_token", "null");
        return userid;
    }

    public String getDailyRate(){
        String dailyRate = preferences.getString("dailyrate", "null");
        return dailyRate;
    }
    public String getWeeklyRate(){
        String weeklyRate = preferences.getString("weeklyrate", "null");
        return weeklyRate;
    }
    public String getMonthlyRate(){
        String monthlyRate = preferences.getString("monthlyrate", "null");
        return monthlyRate;
    }

    public String gettypeOfService(){
        String typeOfService = preferences.getString("typeOfService", "null");
        return typeOfService;
    }
    public String geteducation(){
        String education = preferences.getString("education", "null");
        return education;
    }





}
