package com.example.deskneedloginui;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements VvVolleyInterface {

    private TextInputEditText phone, password;
    private Button login, signu;
    private String phone_no, pass, GEN_ID;
    private ProgressDialog progressDialog;
    //Request URL
    private String URL = "http://admin.doorhopper.in/api/vdhp/account/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Id Assigning.
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        signu = findViewById(R.id.btn_link_signup);

        //Phone and Password String.
//        phone_no = phone.getText().toString().trim();
//        pass = password.getText().toString().trim();

        //Progress Dialogue
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);




        //Generate Random Strings
        generateRandomString();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check Empty fields

                if(TextUtils.isEmpty(phone.getText().toString().trim()))
                    phone.setError("this field cannot be blank");

                if(TextUtils.isEmpty(password.getText().toString().trim()))
                    password.setError("this field cannot be empty");

                progressDialog.show();
                loginOldUser();
            }
        });
    }

    private void loginOldUser() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", phone.getText().toString().trim());
        params.put("key", password.getText().toString().trim());
        params.put("regId", GEN_ID);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/account/login", params);
    }

    private void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
        //   String responseres = jsonObject.getString("responseResult");
           if(jsonObject.getString("responseResult").equals("failure"))
           {
               progressDialog.dismiss();
               Log.d("Token","Not found");
               Toast.makeText(MainActivity.this, "Invalid Phone or password", Toast.LENGTH_LONG).show();
           }else
           {
               progressDialog.dismiss();
               Log.d("Token","found");
               ApplicationVariable.ACCOUNT_DATA.token = jsonObject.getString("token");
               Intent intent = new Intent(MainActivity.this,TabViewActivity.class);
               startActivity(intent);
           }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//generate Id is here
    private void generateRandomString() {
        //Random String generation
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        GEN_ID = sb1.toString();
    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("DSK_OPER", result);
        parseData(result);
    }
}
