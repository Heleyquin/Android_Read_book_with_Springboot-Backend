package com.example.docsach.Activity.BookFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.docsach.API.PackApi;
import com.example.docsach.API.ReaderApi;
import com.example.docsach.API.SachApi;
import com.example.docsach.Adapter.BookFragment.ViewPagerAdapter;
import com.example.docsach.Model.CT_Goi;
import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.DTO.CmtResponse;
import com.example.docsach.Model.DTO.DanhGiaRequest;
import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.Model.DTO.GoiDangKyResponse;
import com.example.docsach.Model.DTO.LichSuMuaRequest;
import com.example.docsach.Model.DTO.PackInUse;
import com.example.docsach.Model.Key.Key_DanhGia;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.LuotDocSach;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Book_Detail extends AppCompatActivity{

//    private Phim phim;
    private TextView tvDecript, tvTitle, tvReadCount, tvFavorCount, tvAvgPointOfRate, tvNumOfRate;
    private Button btnRead, btnTry, btnBuy;
    private ImageView ivImage;
    private ImageView[] stars;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Button tvRating;

    private Sach sach;
    private Reader reader;
    private LuotDocSach luotDocSach;
    private CountAllFavor countAllFavor;
    private List<LichSuMua> lichSuMuaList;
    private List<CT_Goi> ctGoiList;
    private PackInUse packInUse;
    private BigDecimal amount;
    private DanhGiaResponse danhGiaResponseOnBook;
    private List<DanhGiaResponse> danhGiaResponseList;

//    private Rap rap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);


        setControl();
        setDataIntent();
        setDataRate();
        setImage();

        tvRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Book_Detail.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.rating, null);

                RatingBar rtStar;
                EditText edtNhanXet;
                Button btnSendRate;

                rtStar = dialogView.findViewById(R.id.rtStar);
                edtNhanXet = dialogView.findViewById(R.id.edtNhanXet);
                btnSendRate = dialogView.findViewById(R.id.btnSendRate);

                final int[] point = {1};
                final String[] nhanXet = {""};

                if(sach != null && reader != null){
                    ReaderApi.readerApi.getRateByReaderAndBook(sach.getId(), reader.getId()).enqueue(new Callback<DanhGiaResponse>() {
                        @Override
                        public void onResponse(Call<DanhGiaResponse> call, Response<DanhGiaResponse> response) {
                            if(response.isSuccessful()){
                                danhGiaResponseOnBook = response.body();
                                if(danhGiaResponseOnBook != null){
                                    point[0] = danhGiaResponseOnBook.getPoint();
                                    nhanXet[0] = danhGiaResponseOnBook.getNhanXet();
                                    rtStar.setRating(point[0]);
                                    edtNhanXet.setText(nhanXet[0]);
                                }
                            }else{
                                danhGiaResponseOnBook = null;
                            }
                        }

                        @Override
                        public void onFailure(Call<DanhGiaResponse> call, Throwable t) {

                        }
                    });
                }

                rtStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        point[0] = (int) v;
                    }
                });

                edtNhanXet.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        nhanXet[0] = charSequence.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();

                btnSendRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Key_DanhGia keyDanhGia = new Key_DanhGia(sach.getId(), reader.getId());
                        DanhGiaRequest danhGiaRequest = new DanhGiaRequest(keyDanhGia, point[0], nhanXet[0]);
                        ReaderApi.readerApi.ratingBook(danhGiaRequest).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Book_Detail.this);
                                    builder.setTitle("Đánh giá thành công" + sach.getTenSach())
                                            .setMessage("Cảm ơn bạn đã đánh giá " + point[0] + " sao")
                                            .setPositiveButton("OK", (dialog, which) -> {
                                                dialog.dismiss();
                                            })
                                            .show();
                                    setDataRate();
                                    alertDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });

                alertDialog.show();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PdfView.class);

                intent.putExtra("sach", sach);
                intent.putExtra("reader", reader);
                intent.putExtra("try", 0);
                startActivity(intent);
            }
        });
        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PdfView.class);

                intent.putExtra("sach", sach);
                intent.putExtra("reader", reader);
                intent.putExtra("try", 1);
                startActivity(intent);
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigDecimal giaSachDecimal = sach.getGiaTien();
                BigDecimal soDu = amount.subtract(giaSachDecimal);
                if(amount.compareTo(giaSachDecimal) >= 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Book_Detail.this);
                    builder.setTitle("Xác nhận mua sách " + sach.getTenSach())
                            .setMessage("Thanh toán số tiền " + formatAmount(giaSachDecimal) + "\n Số dư còn lại: " + formatAmount(soDu))
                            .setPositiveButton("Thanh toán", (dialog, which) -> {
                                LichSuMuaRequest lichSuMuaRequest = new LichSuMuaRequest(giaSachDecimal, sach, reader);
                                ReaderApi.readerApi.buyBook(lichSuMuaRequest).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if(response.isSuccessful()){
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Book_Detail.this);
                                            builder.setTitle("Thành công mua sách " + sach.getTenSach())
                                                    .setPositiveButton("OK", (dialog, which) -> {
                                                        ReaderApi.readerApi.getLichSuMuaById(reader.getId()).enqueue(new Callback<List<LichSuMua>>() {
                                                            @Override
                                                            public void onResponse(Call<List<LichSuMua>> call, Response<List<LichSuMua>> response) {
                                                                if(response.isSuccessful()){
                                                                    lichSuMuaList.clear();
                                                                    lichSuMuaList.addAll(response.body());
                                                                    setDataIntent();
                                                                    dialog.dismiss();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<List<LichSuMua>> call, Throwable t) {

                                                            }
                                                        });
                                                        dialog.dismiss();
                                                    })
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                                dialog.dismiss();
                            })
                            .setNegativeButton("Huỷ", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Book_Detail.this);
                    builder.setTitle("Số dư không đủ để mua sách" + sach.getTenSach())
                            .setMessage("Còn thiếu " + formatAmount(giaSachDecimal.subtract(amount)) + " vui lòng nạp thêm tiền")
                            .setPositiveButton("OK", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();
                }
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void setImage() {
        SachApi.sachApi.getImage(sach.getUrlImg()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap != null) {
                        ivImage.setImageBitmap(bitmap);
                    } else {
                        Log.e("Image", "Failed to decode image");
                    }
                } else {
                    Log.e("Image", "Response error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        tvTitle.setText(sach.getTenSach());
        tvDecript.setText(sach.getGioiThieu());
        tvReadCount.setText(luotDocSach.getSo_luot_doc().toString());
        tvFavorCount.setText(countAllFavor.getFavor_count().toString());
    }

    private void fetchDataCmt(List<DanhGiaResponse> danhGiaResponseList){
        SachApi.sachApi.getCmtById(sach.getId()).enqueue(new Callback<List<CmtResponse>>() {
            @Override
            public void onResponse(Call<List<CmtResponse>> call, Response<List<CmtResponse>> response) {
                assert response.body() != null;
                setTab(response.body(), danhGiaResponseList);
            }

            @Override
            public void onFailure(Call<List<CmtResponse>> call, Throwable t) {

            }
        });
    }

    private String formatAmount(BigDecimal amo){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);
        return formatter.format(amo) + " VND";
    }

    @SuppressLint("SetTextI18n")
    private void setActionUser(PackInUse pack){
        if(!lichSuMuaList.isEmpty()){
            for (LichSuMua s : lichSuMuaList){
                if(s.getSachBuy().getId() == sach.getId()){
                    btnRead.setVisibility(View.VISIBLE);
                    btnBuy.setVisibility(View.GONE);
                    btnTry.setVisibility(View.GONE);
                    break;
                }
                else{
                    if(pack != null){
                        if(pack.getActive()){
                            LocalDateTime dateTime = null;
                            OffsetDateTime offsetDateTime = OffsetDateTime.parse(pack.getExpirDate());

                            LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
                            LocalDateTime now = LocalDateTime.now();

                            Duration duration = Duration.between(localDateTime, now);
                            if(duration.isNegative()){
                                for(CT_Goi ctGoi:ctGoiList){
                                    if(ctGoi.getSach().getId() == sach.getId()){
                                        btnRead.setVisibility(View.VISIBLE);
                                        btnBuy.setVisibility(View.VISIBLE);
                                        btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                                        btnTry.setVisibility(View.GONE);
                                        Log.i("người dùng", "1");
                                        break;
                                    }else{
                                        btnRead.setVisibility(View.GONE);
                                        btnBuy.setVisibility(View.VISIBLE);
                                        btnTry.setVisibility(View.VISIBLE);
                                        btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                                        Log.i("người dùng", "2");
                                    }
                                }
                            }else{
                                btnRead.setVisibility(View.GONE);
                                btnBuy.setVisibility(View.VISIBLE);
                                btnTry.setVisibility(View.VISIBLE);
                                btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                                Log.i("người dùng", "3");
                            }
                        }else{
                            btnRead.setVisibility(View.GONE);
                            btnBuy.setVisibility(View.VISIBLE);
                            btnTry.setVisibility(View.VISIBLE);
                            btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                            Log.i("người dùng", "4");
                        }
                    }
                    else{
                        btnRead.setVisibility(View.GONE);
                        btnBuy.setVisibility(View.VISIBLE);
                        btnTry.setVisibility(View.VISIBLE);
                        btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                        Log.i("người dùng", "5");
                    }
                }
            }

        }
        else{
            if(pack != null){
                if(pack.getActive()){
                    LocalDateTime dateTime = null;
                    OffsetDateTime offsetDateTime = OffsetDateTime.parse(pack.getExpirDate());

                    LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
                    LocalDateTime now = LocalDateTime.now();

                    Duration duration = Duration.between(localDateTime, now);
                    if(duration.isNegative()){
                        for(CT_Goi ctGoi:ctGoiList){
                            if(ctGoi.getSach().getId() == sach.getId()){
                                btnRead.setVisibility(View.VISIBLE);
                                btnBuy.setVisibility(View.VISIBLE);
                                btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                                btnTry.setVisibility(View.GONE);
                                Log.i("người dùng", "6");
                                break;
                            }else{
                                btnRead.setVisibility(View.GONE);
                                btnBuy.setVisibility(View.VISIBLE);
                                btnTry.setVisibility(View.VISIBLE);
                                btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                                Log.i("người dùng", "7");
                            }
                        }
                    }else{
                        btnRead.setVisibility(View.GONE);
                        btnBuy.setVisibility(View.VISIBLE);
                        btnTry.setVisibility(View.VISIBLE);
                        btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                        Log.i("người dùng", "8");
                    }
                }else{
                    btnRead.setVisibility(View.GONE);
                    btnBuy.setVisibility(View.VISIBLE);
                    btnTry.setVisibility(View.VISIBLE);
                    btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                    Log.i("người dùng", "9");
                }
            }
            else{
                btnRead.setVisibility(View.GONE);
                btnBuy.setVisibility(View.VISIBLE);
                btnTry.setVisibility(View.VISIBLE);
                btnBuy.setText("Mua ngay (" + formatAmount(sach.getGiaTien()) + ")");
                Log.i("người dùng", "10");
            }
        }


    }

    private void fetChPackInUse(Long idUser){
        ReaderApi.readerApi.goiDangDung(idUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String m = null;
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        try {
                            m = response.body().string();

                            if(!m.trim().isEmpty()){
                                Gson gson = new Gson();
                                String[] jsonArray = gson.fromJson(m, String[].class);
                                packInUse = new PackInUse();
                                packInUse.setMaGoi(jsonArray[0].toString());
                                packInUse.setChuThich(jsonArray[1]);
                                packInUse.setThoiGianDangKy(jsonArray[2]);
                                packInUse.setThoiHan(Integer.parseInt(jsonArray[3]));
                                packInUse.setExpirDate(jsonArray[4]);
                                packInUse.setActive(Boolean.valueOf(jsonArray[5]));

                                fetchBookInPack(packInUse, packInUse.getMaGoi());
                            }
                            fetchBookInPack(null, null);
                        } catch (IOException e) {
                            fetchBookInPack(null, null);
                            throw new RuntimeException(e);
                        }
                    }else{
                        fetchBookInPack(null, null);
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("sackk", "failed");
                fetchBookInPack(null, null);
            }
        });
    }
    private void fetchBookInPack(PackInUse pack, String maGoi){
        PackApi.packApi.allPacks().enqueue(new Callback<List<GoiDangKyResponse>>() {
            @Override
            public void onResponse(Call<List<GoiDangKyResponse>> call, Response<List<GoiDangKyResponse>> response) {
                List<GoiDangKyResponse> goiDangKyResponses = new ArrayList<>(response.body());
                GoiDangKyResponse goiDangKyResponse;
                if(maGoi != null){
                    goiDangKyResponse = goiDangKyResponses.stream()
                            .filter(item -> Objects.equals(item.getMaGoi(), maGoi))
                            .findFirst()
                            .orElse(null);
                    if(goiDangKyResponse != null){
                        ctGoiList = new ArrayList<>(goiDangKyResponse.getCtGoiSet());
                    }
                    setActionUser(pack);
                }else{
                    setActionUser(pack);
                }
            }

            @Override
            public void onFailure(Call<List<GoiDangKyResponse>> call, Throwable t) {
                setActionUser(pack);
            }
        });
    }

    private void setTab(List<CmtResponse> cmtResponseList, List<DanhGiaResponse> danhGiaResponseList){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, cmtResponseList, danhGiaResponseList, sach, reader);
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Bình luận");
            } else {
                tab.setText("Đánh giá/Nhận xét");
            }
        }).attach();
    }
    private void setDataRate(){
        SachApi.sachApi.getRateById(sach.getId()).enqueue(new Callback<List<DanhGiaResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DanhGiaResponse>> call, Response<List<DanhGiaResponse>> response) {
                if(response.body() != null){
                    danhGiaResponseList = new ArrayList<>(response.body());
                }
                fetchDataCmt(danhGiaResponseList);
                if(danhGiaResponseList != null && !danhGiaResponseList.isEmpty()){
                    int totalPoint = 0;
                    for(DanhGiaResponse d:danhGiaResponseList){
                        totalPoint += d.getPoint();
                    }
                    double avgPoint = (double) totalPoint /danhGiaResponseList.size();
                    avgPoint = Math.round(avgPoint * 100.0)/100.0;
                    tvAvgPointOfRate.setText(avgPoint + "/5 Sao");
                    tvNumOfRate.setText(danhGiaResponseList.size() + " đánh giá");
                    int i;
                    int fullStar = (int) avgPoint;
                    boolean hasHalfStar = avgPoint - fullStar > 0.0;
                    for(i = 0; i < fullStar; i++){
                        stars[i].setImageResource(R.drawable.ic_start_yellow);
                    }
                    if(hasHalfStar){
                        stars[i].setImageResource(R.drawable.baseline_star_half_24);
                    }
                }else{
                    for(int i = 0; i < 5; i++){
                        stars[i].setImageResource(R.drawable.ic_start_yellow);
                    }
                    tvAvgPointOfRate.setText("/5 sao");
                    tvNumOfRate.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<DanhGiaResponse>> call, Throwable t) {
                fetchDataCmt(danhGiaResponseList);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void setDataIntent() {
        Intent intent = getIntent();
        sach = (Sach) intent.getSerializableExtra("sach");
        luotDocSach = (LuotDocSach) intent.getSerializableExtra("readedCount");
        countAllFavor = (CountAllFavor) intent.getSerializableExtra("favorCount");
        lichSuMuaList = (List<LichSuMua>) intent.getSerializableExtra("lichSuMuaList");
        reader = (Reader) intent.getSerializableExtra("reader");
        amount = (BigDecimal) intent.getSerializableExtra("amount");
        assert reader != null;
        fetChPackInUse(reader.getId());

    }
    public void setControl(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tvRating = findViewById(R.id.tvRating);
        tvDecript = findViewById(R.id.tvDecript);
        tvTitle = findViewById(R.id.tvTitle);
        tvFavorCount = findViewById(R.id.tvFavorCount);
        tvReadCount = findViewById(R.id.tvReadCount);
        ivImage = findViewById(R.id.ivImage);
        btnRead = findViewById(R.id.btnRead);
        btnTry = findViewById(R.id.btnTry);
        btnBuy = findViewById(R.id.btnBuy);
        stars = new ImageView[]{
                findViewById(R.id.imgStar1),
                findViewById(R.id.imgStar2),
                findViewById(R.id.imgStar3),
                findViewById(R.id.imgStar4),
                findViewById(R.id.imgStar5)
        };
        tvAvgPointOfRate = findViewById(R.id.tvAvgPointOfRate);
        tvNumOfRate = findViewById(R.id.tvNumOfRate);
    }


}