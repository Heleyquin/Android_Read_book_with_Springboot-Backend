package com.example.docsach.API;

import com.example.docsach.Model.DTO.AccountRequest;
import com.example.docsach.Model.DTO.UserAccountRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    AccountApi accountApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.100:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AccountApi.class);
    @POST("/account/login-reader")
    Call<ResponseBody> login(@Body AccountRequest accountRequest);

    @GET("/account/extract")
    Call<ResponseBody> extract(@Query("token") String token);

    @POST("/user-account")
    Call<ResponseBody> regisReader(@Body UserAccountRequest userAccountRequest);


}
