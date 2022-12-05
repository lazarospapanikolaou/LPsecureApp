package com.example.test2app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText Register_with_username;
    private EditText Register_with_password;
    private EditText Register_with_phoneNumber;
    private EditText Register_with_DeviceSerialNumber;
    private TextView textView_Register;
    private Button btnRegister;


    private static final String URL_REGISTER = "http://192.168.81.132:8000/api/register";

    String [] permissions={"android.permission.READ_PHONE_STATE"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Register_with_username = findViewById(R.id.Register_with_username);
        Register_with_password = findViewById(R.id.Register_with_password);
        Register_with_phoneNumber = findViewById(R.id.Register_with_phoneNumber);
        Register_with_DeviceSerialNumber = findViewById(R.id.Register_with_DeviceSerialNumber);
        textView_Register = findViewById(R.id.textView_Register);
        btnRegister = findViewById(R.id.btnRegister);





        textView_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    requestPermissions(permissions, 101);
                }

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        switch (requestCode)
        {

            case 101:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.Register_with_DeviceSerialNumber.setText(id);

                    register();



                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }

        }
    }


    private void register(){

        final String Register_with_username = this.Register_with_username.getText().toString().trim();
        final String Register_with_password = this.Register_with_password.getText().toString().trim();

        final String Register_with_phoneNumber = this.Register_with_phoneNumber.getText().toString().trim();
        final String Register_with_DeviceSerialNumber = this.Register_with_DeviceSerialNumber.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if (success.equals("true")){
                            JSONObject jsonObject1 = new JSONObject(response);
                            String registerSuccessMessage = jsonObject1.getString("registerSuccessMessage").trim();

                            Toast.makeText(RegisterActivity.this, registerSuccessMessage, Toast.LENGTH_SHORT).show();
                            Intent mainScreenIntent = new Intent(RegisterActivity.this, OnboardActivity.class);
                            startActivity(mainScreenIntent);

                        } else {
                            String message = jsonObject.getString("errors");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Register Error" + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String DataResponseRegister = "";
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        String phonenumber = "", username = "", password = "";
                        if(error.networkResponse.data!=null) {
                            try {
                                DataResponseRegister = new String(error.networkResponse.data,"UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        if (statusCode.equals("422")){
                            try {
                                JSONObject obj = new JSONObject(DataResponseRegister);
                                String errors = obj.getString("errors");

                                JSONObject obj2 = new JSONObject(errors);
                                phonenumber = obj2.getString("phonenumber");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!phonenumber.equals("")){

                                Toast.makeText(RegisterActivity.this, phonenumber, Toast.LENGTH_SHORT).show();
                            }

                            try {
                                JSONObject obj = new JSONObject(DataResponseRegister);
                                String errors = obj.getString("errors");

                                JSONObject obj2 = new JSONObject(errors);
                                username = obj2.getString("username");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!username.equals("")){

                                Toast.makeText(RegisterActivity.this, username, Toast.LENGTH_SHORT).show();
                            }

                            try {
                                JSONObject obj = new JSONObject(DataResponseRegister);
                                String errors = obj.getString("errors");

                                JSONObject obj2 = new JSONObject(errors);
                                password = obj2.getString("password");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!password.equals("")){

                                Toast.makeText(RegisterActivity.this, password, Toast.LENGTH_SHORT).show();
                            }


                        }




                    }
                })
        {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", Register_with_username);
                params.put("password", Register_with_password);
                params.put("phonenumber", Register_with_phoneNumber);
                params.put("device_id", Register_with_DeviceSerialNumber);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





}