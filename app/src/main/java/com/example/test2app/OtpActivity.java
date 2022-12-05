package com.example.test2app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class OtpActivity extends AppCompatActivity {


    private EditText inputCode1;
    private EditText inputCode2;
    private EditText inputCode3;
    private EditText inputCode4;
    private EditText inputCode5;
    private EditText inputCode6;
    private Button buttonVerify;

    private TextView otp_countDownTimer;

    private static final String URL_ConfirmOtp = "http://192.168.81.132:8000/api/confirmOtp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        buttonVerify = findViewById(R.id.buttonVerify);


        otp_countDownTimer = findViewById(R.id.otp_countDownTimer);
        long duration = TimeUnit.SECONDS.toMillis(41);

        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                    otp_countDownTimer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                otp_countDownTimer.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Countdown finish", Toast.LENGTH_SHORT).show();
            }
        }.start();


        SetupOtpInputs();



        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mCode1 = inputCode1.getText().toString().trim();
                String mCode2 = inputCode2.getText().toString().trim();
                String mCode3 = inputCode3.getText().toString().trim();
                String mCode4 = inputCode4.getText().toString().trim();
                String mCode5 = inputCode5.getText().toString().trim();
                String mCode6 = inputCode6.getText().toString().trim();


                String mOtp = mCode1 + mCode2 + mCode3 + mCode4 + mCode5 + mCode6;



                if (inputCode1.getText().toString().trim().isEmpty()
                        ||inputCode2.getText().toString().trim().isEmpty()
                        ||inputCode3.getText().toString().trim().isEmpty()
                        ||inputCode4.getText().toString().trim().isEmpty()
                        ||inputCode5.getText().toString().trim().isEmpty()
                        ||inputCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                } else {

                    Verify(mOtp);
                }

            }
        });


    }







    private void SetupOtpInputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }


    private void Verify(String mOtp) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ConfirmOtp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            switch (success) {
                                case "true":

                                    JSONObject jsonObject1 = new JSONObject(response);
                                    String dataUser = jsonObject1.getString("data").trim();

                                    Toast.makeText(OtpActivity.this, "Success OTP. \nYour data :" + dataUser, Toast.LENGTH_SHORT).show();

                                    break;
                                case "false":

                                    Toast.makeText(OtpActivity.this, "OutTimeLimit", Toast.LENGTH_SHORT).show();

                                    break;
                                case "wrong":

                                    Toast.makeText(OtpActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();

                                    break;
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OtpActivity.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OtpActivity.this, "Error " +error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("otp", mOtp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }





}