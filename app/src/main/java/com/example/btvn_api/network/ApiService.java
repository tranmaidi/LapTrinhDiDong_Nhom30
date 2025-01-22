package com.example.btvn_api.network;

import com.example.btvn_api.model.ForgotPasswordRequest;
import com.example.btvn_api.model.ForgotPasswordResponse;
import com.example.btvn_api.model.LoginRequest;
import com.example.btvn_api.model.LoginResponse;
import com.example.btvn_api.model.OtpVerificationRequest;
import com.example.btvn_api.model.OtpVerificationResponse;
import com.example.btvn_api.model.RegisterRequest;
import com.example.btvn_api.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {
    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("user/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("user/forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("user/verify-otp")
    Call<OtpVerificationResponse> verifyOtp(@Body OtpVerificationRequest otpVerificationRequest);
}
