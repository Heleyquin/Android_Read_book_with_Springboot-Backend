package com.example.docsach.API;

import com.example.docsach.Model.DTO.CT_DangKyRequest;
import com.example.docsach.Model.DTO.CT_DangKyResponse;
import com.example.docsach.Model.DTO.CmtRequest;
import com.example.docsach.Model.DTO.DanhGiaRequest;
import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.Model.DTO.GoiDangKyResponse;
import com.example.docsach.Model.DTO.LichSuDocRequest;
import com.example.docsach.Model.DTO.LichSuMuaRequest;
import com.example.docsach.Model.DTO.LichSuNapRequest;
import com.example.docsach.Model.DTO.PackInUse;
import com.example.docsach.Model.DTO.PurchaseRequest;
import com.example.docsach.Model.DTO.ReaderUpdate;
import com.example.docsach.Model.DTO.SachMongMuonRequest;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReaderApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ReaderApi readerApi = new Retrofit.Builder()
//            .baseUrl("http://192.168.1.100:8080")
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ReaderApi.class);
    @GET("/reader")
    Call<List<Reader>> allReaders();

    @GET("reader/username/{username}")
    Call<Reader> getReaderByUsername(@Path("username") String username);

    @GET("/reader/lich-su-mua/{id}")
    Call<List<LichSuMua>> getLichSuMuaById(@Path("id") Long id);

    @GET("reader/sach-mong-muon/{id}")
    Call<List<Sach_Mong_Muon>> getAllWhistById(@Path("id") Long id);

    @POST("reader/mua-sach")
    Call<ResponseBody> buyBook(@Body LichSuMuaRequest lichSuMuaRequest);

    @DELETE("reader/empty")
    Call<ResponseBody> emptyWhistList(@Body List<Sach_Mong_Muon> sachMongMuonList);

    @GET("reader/amount/{id}")
    Call<ResponseBody> getAmountById(@Path("id") Long id);

    @POST("reader/nap-tien")
    Call<ResponseBody> napTien(@Body LichSuNapRequest lichSuNapRequest);

    @GET("reader/pack-on-use/{id}")
    Call<ResponseBody> goiDangDung(@Path("id") Long id);

    @POST("reader/purchase-pack")
    Call<ResponseBody> dangKyGoi(@Body CT_DangKyRequest ctDangKyRequest);

    @POST("reader/comment")
    Call<ResponseBody> comment(@Body CmtRequest cmtRequest);

    @POST("reader/rate")
    Call<ResponseBody> rate(@Body DanhGiaRequest danhGiaRequest);

    @POST("/lichSuDoc")
    Call<ResponseBody> docSach(@Body LichSuDocRequest lichSuDocRequest);

    @PUT("/reader")
    Call<ResponseBody> updateReader(@Body ReaderUpdate readerUpdate);

    @POST("/wish")
    Call<ResponseBody> addWishItem(@Body SachMongMuonRequest sachMongMuonRequest);

    @DELETE("/wish/{id}")
    Call<ResponseBody> deleteWishItem(@Path("id") Long id);

    @GET("reader/pack-purchased/{id}")
    Call<List<CT_DangKyResponse>> allPackPurchased(@Path("id") Long id);


    @POST("reader/purchase")
    Call<ResponseBody> purchaseBooks(@Body PurchaseRequest purchaseRequest);

    @GET("/reader/rate-by-book-reader")
    Call<DanhGiaResponse> getRateByReaderAndBook(@Query("idSach") Long idSach, @Query("idDocGia") Long idDocGia);

    @POST("/reader/rate")
    Call<ResponseBody> ratingBook(@Body DanhGiaRequest danhGiaRequest);

}
