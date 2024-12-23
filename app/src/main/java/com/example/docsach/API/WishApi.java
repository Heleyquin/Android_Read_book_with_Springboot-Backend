package com.example.docsach.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface WishApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    WishApi wishApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.100:8080")
//            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WishApi.class);
    @DELETE("wish/clear/{id}")
    Call<ResponseBody> clearWishList(@Path("id") Long id);
}
