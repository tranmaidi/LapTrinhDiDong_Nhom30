package com.example.btvn_api.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText txt_name, txt_email, txt_password;
    private Button btn_register;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_name = findViewById(R.id.txt_Name);
        txt_email = findViewById(R.id.txt_Email);
        txt_password = findViewById(R.id.txt_Password);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        String name = txt_name.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        if(name.isEmpty())
        {
            txt_name.setError("Enter Name");
            txt_name.requestFocus();
            return;
        }
        if (name.length()<3)
        {
            txt_name.setError("Name too Short");
            txt_name.requestFocus();
            return;
        }
        if (name.length()>30)
        {
            txt_name.setError("Name too Long");
            txt_name.requestFocus();
            return;
        }
        if (email.isEmpty())
        {
            txt_email.setError("Enter Email");
            txt_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            txt_email.setError("Enter Valid Email");
            txt_email.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            txt_password.setError("Enter Password");
            txt_password.requestFocus();
            return;
        }
        if (password.length()<6)
        {
            txt_password.setError("Password too Short");
            txt_password.requestFocus();
            return;
        }
        if (password.length()>30)
        {
            txt_password.setError("Password too Long");
            txt_password.requestFocus();
        }
        else
        {
//            Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
//            intent.putExtra("name", name);
//            intent.putExtra("email", email);
//            intent.putExtra("password", password);
            sendOtp(email);
        }
    }

    private void sendOtp(String email) {
        btn_register.setEnabled(false);
        Call<ResponseDefault> call = RetrofitClient.getInstance().getApi().sendOtp(email);
        call.enqueue(new Callback<ResponseDefault>() {
            @Override
            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> response) {
                btn_register.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "OTP sent to your email", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", txt_name.getText().toString().trim());
                    intent.putExtra("password", txt_password.getText().toString().trim());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDefault> call, Throwable t) {
                t.printStackTrace();
                btn_register.setEnabled(true);
            }
        });
    }

//    private void doRegister(String name, String email, String password) {
//        btn_register.setEnabled(false);
//        Call<ResponseDefault> call = RetrofitClient.getInstance().getApi().register(name, email, password);
//        call.enqueue(new Callback<ResponseDefault>() {
//            @Override
//            public void onResponse(Call<ResponseDefault> call, Response<ResponseDefault> response) {
//                btn_register.setEnabled(true);
//                if (response.isSuccessful() && !response.body().isError()) {
//                    Toast.makeText(RegisterActivity.this, "OTP sent to your email!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
//                    intent.putExtra("email", email);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(RegisterActivity.this, "Registration failed: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDefault> call, Throwable t) {
//                t.printStackTrace();
//                btn_register.setEnabled(true);
//            }
//        });
  //  }
}

