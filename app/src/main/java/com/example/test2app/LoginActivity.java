package com.example.test2app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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


public class LoginActivity extends AppCompatActivity {

    //dhlvnv tiw metablites poy exv xrhsimopoihsei sta xml activities kai lambanoun xora edo
    private NotificationManagerCompat notificationManager;
    private EditText sign_in_with_username;
    private EditText sign_in_with_password;
    private EditText sign_in_with_phoneNumber;
    private TextView textView_Login;
    private Button btnLogin;
    private String DeviceID;


    //dhlono to permission poy exo prosthesei sto manifest
    String [] permissions={"android.permission.READ_PHONE_STATE"};

    //arxikopoio to url pou xrhsimopoieitai gia thn syndesh toy RESTful API me to android studio gia to login
    private static final String URL_LOGIN = "http://192.168.81.132:8000/api/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //krybo to action bar kai to notification bar apo thn othoni tou kintoy
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //ftiaxnv ena kanalo to opoio einai diauesimo gia andorid q dhladh 10 gia na stalthei to notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NotificationChannel channel = new NotificationChannel("CHANNEL_1_ID", "My Notification",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        notificationManager = NotificationManagerCompat.from(this);

        //orizv tiw metablhtes poy exo dilosei parapano na kavnoy interact me autes tou xml
        sign_in_with_username = findViewById(R.id.sign_in_with_username);
        sign_in_with_password = findViewById(R.id.sign_in_with_password);
        sign_in_with_phoneNumber = findViewById(R.id.sign_in_with_phoneNumber);
        textView_Login = findViewById(R.id.textView_Login);
        btnLogin = findViewById(R.id.btnLogin);

        //dhlono thn leitourgia apo to button textView_Login
        textView_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se periptosi poy paththei to button tote apo to Login page pame sto Register Page
                Intent RegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });

        //dhlono thn leitourgia apo to button btnLogin
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //se peripotso poy h kinhth syskeyh parexei yphresiew android q tote lambanei xora ta permissions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    requestPermissions(permissions, 101);
                }

            }
        });
    }

    @SuppressLint("HardwareIds")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //ginetai lipsi to DeviceID apo thn sykeyh kathos stelbetai kai persmission
        DeviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        switch (requestCode)
        {

            case 101:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String mUsername = sign_in_with_username.getText().toString().trim();
                    String mPassword = sign_in_with_password.getText().toString().trim();
                    String mPhoneNumber = sign_in_with_phoneNumber.getText().toString().trim();

                    if (!mUsername.isEmpty() || !mPassword.isEmpty() || mPhoneNumber.isEmpty()) {

                 //       Toast.makeText(LoginActivity.this, "The DeviceId is correct", Toast.LENGTH_SHORT).show();

                        //pragmtopoieitai eisodos me tis parakato metavlites
                        Login(mUsername, mPassword, mPhoneNumber);



                    } else {
                        sign_in_with_username.setError("Please insert username");
                        sign_in_with_password.setError("Please insert password");
                        sign_in_with_phoneNumber.setError("Please insert phoneNumber");
                    }


                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }

        }
    }



    //ksekinaei h diadikasia login me to username, password, phonenumber
    private void Login(String sign_in_with_username, String sign_in_with_password, String sign_in_with_phoneNumber) {


            System.out.println("0");
        //ginetai sindesi toy login me to rest api meso tou volley library me methodo post
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override


                    //diadikasia poy lambanei xora sto response
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            System.out.println("1");

                            if (success.equals("true")) {

                                    JSONObject jsonObject1 = new JSONObject(response);

                                    //sto respoonse apothikeuontai ta dedoena otp kai otp_expires_at
                                    String otp = jsonObject1.getString("otp").trim();
                                    String otp_expires_at = jsonObject1.getString("otp_expires_at").trim();

                                System.out.println("2");

                                //axizei h diadikasia tou notification
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(LoginActivity.this,"CHANNEL_1_ID");
                                        builder.setContentTitle("This is the your One Time Password");
                                        builder.setContentText(otp);
                                        builder.setSmallIcon(R.drawable.ic_launcher_background);
                                        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
                                        builder.setAutoCancel(true);


                                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(LoginActivity.this);
                                        managerCompat.notify(1,builder.build());


                                Intent intent = new Intent(getApplicationContext(),OtpActivity.class);
                                intent.putExtra("otp", otp);
                                intent.putExtra("otp_expires_at", otp_expires_at);
                                startActivity(intent);


                            } else if (success.equals("false")){

                                JSONObject jsonObject2 = new JSONObject(response);
                                String error = jsonObject2.getString("error");

                                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String DataResponseLogin = "";
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        String phonenumber = "", username = "", password = "";
                        if (error.networkResponse.data != null) {
                            try {
                                DataResponseLogin = new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        if (statusCode.equals("422")) {
                            try {
                                JSONObject obj = new JSONObject(DataResponseLogin);
                                String errors = obj.getString("errors");

                                JSONObject obj2 = new JSONObject(errors);
                                username = obj2.getString("username");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!username.equals("")) {

                                Toast.makeText(LoginActivity.this, username, Toast.LENGTH_SHORT).show();
                            }

                            try {
                                JSONObject obj = new JSONObject(DataResponseLogin);
                                String errors = obj.getString("errors");

                                JSONObject obj2 = new JSONObject(errors);
                                password = obj2.getString("password");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!password.equals("")) {

                                Toast.makeText(LoginActivity.this, password, Toast.LENGTH_SHORT).show();
                            }

                            try {
                                JSONObject obj = new JSONObject(DataResponseLogin);
                                String errors = obj.getString("errors");

                                JSONObject obj2 = new JSONObject(errors);
                                phonenumber = obj2.getString("phonenumber");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!phonenumber.equals("")) {

                                Toast.makeText(LoginActivity.this, phonenumber, Toast.LENGTH_SHORT).show();
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
                params.put("username", sign_in_with_username);
                params.put("password", sign_in_with_password);
                params.put("phonenumber", sign_in_with_phoneNumber);
                params.put("device_id", DeviceID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}