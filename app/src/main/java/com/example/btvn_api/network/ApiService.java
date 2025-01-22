package com.example.btvn_api.network;

import com.example.btvn_api.model.ForgotPasswordRequest;
import com.example.btvn_api.model.ForgotPasswordResponse;
import com.example.btvn_api.model.LoginRequest;
import com.example.btvn_api.model.LoginResponse;
import com.example.btvn_api.model.OtpVerificationRequest;
import com.example.btvn_api.model.OtpVerificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface ApiService {
    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("user/forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("user/verify-otp")
    Call<OtpVerificationResponse> verifyOtp(@Body OtpVerificationRequest otpVerificationRequest);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseDefault> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("send-otp")
    Call<ResponseDefault> sendOtp(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("verify-otp")
    Call<ResponseDefault> verifyOTP(
            @Field("email") String email,
            @Field("otp") String otp
    );
}
