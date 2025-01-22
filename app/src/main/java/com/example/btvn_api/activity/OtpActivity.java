package com.example.btvn_api.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btvn_api.R;
import com.example.btvn_api.network.ResponseDefault;
import com.example.btvn_api.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {

    private TextView tvEmail;

    private EditText inputOtp;
    private Button btnVerifyOtp;
    private String email, name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvEmail = findViewById(R.id.tvEmail);
        inputOtp = findViewById(R.id.txt_otp);
        btnVerifyOtp = findViewById(R.id.btn_otp);

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");

        tvEmail.setText("Email: " + email);

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = inputOtp.getText().toString().trim();
                if (otp.isEmpty()) {
                    inputOtp.setError("Enter OTP");
                    inputOtp.requestFocus();
                } else {
                    verifyOtp(email, otp);
                }
            }
        });
    }

    private void verifyOtp(String email, String otp) {
        Call<ResponseDefault> call = RetrofitClient.getInstance().getApi().verifyOTP(email, otp);
        call.enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> response) {
                if (response.isSuccessful()) {
                    ResponseDefault resp = response.body();
                    if (!resp.isError()) {
                        Toast.makeText(OtpActivity.this, "OTP Verified! Registering account.", Toast.LENGTH_SHORT).show();
                        registerAccount(name,email,password);
                    } else {
                        Toast.makeText(OtpActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable t) {
                Toast.makeText(OtpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerAccount(String name, String email, String password) {
        Call<ResponseDefault> call = RetrofitClient.getInstance().getApi().register(name,email,password);
        call.enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> response) {
                if(response.isSuccessful())
                {
                    ResponseDefault resp = response.body();
                    if(resp.isError())
                    {
                        Toast.makeText(OtpActivity.this, String.valueOf(resp.getMessage()), Toast.LENGTH_SHORT).show();
                        sendToLogin();
                    }
                    else
                    {
                        Toast.makeText(OtpActivity.this, String.valueOf(resp.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void sendToLogin()
    {
        Intent intent = new Intent(OtpActivity.this,LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}