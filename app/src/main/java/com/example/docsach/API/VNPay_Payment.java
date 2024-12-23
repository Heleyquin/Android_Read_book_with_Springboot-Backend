package com.example.docsach.API;

import com.example.docsach.Model.DTO.VNPay_Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VNPay_Payment {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    VNPay_Payment vnpay_payment = new Retrofit.Builder()
            .baseUrl("http://192.168.1.100:8080")
//            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(VNPay_Payment.class);
    @GET("/payment/create-order-mobie")
    Call<VNPay_Response> createPayment(
            @Query("amount") long amount,
            @Query("id_doc_gia") long idDocGia
    );
}
