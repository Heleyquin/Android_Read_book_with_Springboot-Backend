package com.example.docsach.API;

import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.DTO.CmtResponse;
import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.Model.LuotDocSach;
import com.example.docsach.Model.Sach;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SachApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    SachApi sachApi = new Retrofit.Builder()
//            .baseUrl("http://192.168.1.100:8080")
//            .baseUrl("http://10.0.2.2:8080")
            .baseUrl("http://192.168.137.1:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SachApi.class);
    @GET("/sach")
    Call<List<Sach>> allBooks();

    @GET("/sach/img-mobie/{fileName}")
    Call<ResponseBody> getImage(@Path("fileName") String fileName);

    @GET("sach/all-favor")
    Call<List<CountAllFavor>> bookWithCountFavor();

    @GET("sach/luot-doc")
    Call<List<LuotDocSach>> bookWithReadedCount();

    @GET("sach/file-mobie/{fileName}")
    Call<ResponseBody> getPdfFile(@Path("fileName") String fileName);

    @GET("sach/rate/{id}")
    Call<List<DanhGiaResponse>> getRateById(@Path("id") Long id);

    @GET("sach/cmt/{id}")
    Call<List<CmtResponse>> getCmtById(@Path("id") Long id);
}
