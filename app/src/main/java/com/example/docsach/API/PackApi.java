package com.example.docsach.API;

import com.example.docsach.Model.DTO.GoiDangKyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PackApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    PackApi packApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.100:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PackApi.class);
    @GET("/dang-ki-goi")
    Call<List<GoiDangKyResponse>> allPacks();
    @GET("/dang-ki-goi/{maGoi}")
    Call<GoiDangKyResponse> getPackById(@Path("maGoi") String maGoi);
}
