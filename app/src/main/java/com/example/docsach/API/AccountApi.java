package com.example.docsach.API;

import com.example.docsach.Model.DTO.AccountRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    AccountApi accountApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.100:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AccountApi.class);
    @POST("/account/login")
    Call<String> login(@Body AccountRequest accountRequest);
}
