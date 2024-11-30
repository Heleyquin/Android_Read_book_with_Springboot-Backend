package com.example.docsach.Activity.BookFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.docsach.API.PackApi;
import com.example.docsach.API.ReaderApi;
import com.example.docsach.API.SachApi;
import com.example.docsach.Adapter.BookFragment.NgayChieuAdapter;
import com.example.docsach.Adapter.BookFragment.RapAdapter;
import com.example.docsach.Adapter.BookFragment.ViewPagerAdapter;
import com.example.docsach.Model.CT_Goi;
import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.DTO.CmtResponse;
import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.Model.DTO.GoiDangKyResponse;
import com.example.docsach.Model.DTO.LichSuMuaRequest;
import com.example.docsach.Model.DTO.PackInUse;
import com.example.docsach.Model.Ghe;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.LuotDocSach;
import com.example.docsach.Model.Phim;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.Model.SuatChieu;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Book_Detail extends AppCompatActivity implements NgayChieuAdapter.ItemInterface{

//    private Phim phim;
    private TextView tvDecript, tvTitle, tvReadCount, tvFavorCount, tvAvgPointOfRate, tvNumOfRate;
    private Button btnRead, btnTry, btnBuy;
    private ImageView ivImage;
    private ImageView[] stars;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView tvRating;

    private List<Sach> sachList, sachFavors;
    private List<Sach_Mong_Muon> sachMongMuonList;
    private Sach sach;
    private Reader reader;
    private LuotDocSach luotDocSach;
    private CountAllFavor countAllFavor;
    private List<LichSuMua> lichSuMuaList;
    private List<DanhGiaResponse> danhGiaResponseList;
    private List<CmtResponse> cmtResponseList;
    private List<CT_Goi> ctGoiList;
    private PackInUse packInUse;
    BigDecimal amount;
//    private Rap rap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);


        setControl();
        setDataIntent();
        setDataRate();
//        getAmount(reader.getId());
        setImage();

        tvRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
//        Glide.with(ivImage).load(phim.getAnh()).into(ivImage);
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
                cmtResponseList = new ArrayList<>(response.body());
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

    private void setActionUser(PackInUse pack){
        for (LichSuMua s : lichSuMuaList){
            if(s.getSachBuy().getId() == sach.getId() && Objects.equals(s.getReaderBuy().getId(), reader.getId())){

                btnRead.setVisibility(View.VISIBLE);
                btnBuy.setVisibility(View.GONE);
                btnTry.setVisibility(View.GONE);
            }
            else{
                if(pack != null){
                    LocalDateTime dateTime = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        OffsetDateTime offsetDateTime = OffsetDateTime.parse(pack.getExpirDate());

                        LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
                        LocalDateTime now = LocalDateTime.now();

                        Duration duration = Duration.between(localDateTime, now);

                        if(duration.isNegative()){
                            for(CT_Goi ctGoi:ctGoiList){
                                if(ctGoi.getSach().getId() == sach.getId()){
                                    btnRead.setVisibility(View.VISIBLE);
                                    btnBuy.setVisibility(View.VISIBLE);
                                    btnTry.setVisibility(View.GONE);
                                }
                            }
                        }else{
                            btnRead.setVisibility(View.GONE);
                            btnBuy.setVisibility(View.VISIBLE);
                            btnTry.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    btnRead.setVisibility(View.GONE);
                    btnBuy.setVisibility(View.VISIBLE);
                    btnTry.setVisibility(View.VISIBLE);
                }
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


                                fetchBookInPack(packInUse, packInUse.getMaGoi());

                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("sackk", "failed");
            }
        });
    }
    private void fetchBookInPack(PackInUse pack, String maGoi){
        PackApi.packApi.allPacks().enqueue(new Callback<List<GoiDangKyResponse>>() {
            @Override
            public void onResponse(Call<List<GoiDangKyResponse>> call, Response<List<GoiDangKyResponse>> response) {
                List<GoiDangKyResponse> goiDangKyResponses = new ArrayList<>(response.body());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    GoiDangKyResponse goiDangKyResponse = goiDangKyResponses.stream()
                            .filter(item -> Objects.equals(item.getMaGoi(), maGoi))
                            .findFirst()
                            .orElse(null);
                    if(goiDangKyResponse != null){
                        ctGoiList = new ArrayList<>(goiDangKyResponse.getCtGoiSet());
                        setActionUser(pack);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GoiDangKyResponse>> call, Throwable t) {

            }
        });
    }

//    private void getAmount(Long id){
//        ReaderApi.readerApi.getAmountById(id).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String result = response.body().string().trim();
//                    String formated = result.replace(",", ".");
//                    Double am = Double.parseDouble(formated);
//                    amount = BigDecimal.valueOf(am);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                amount = null;
//                Log.i("amount", "failed");
//            }
//        });
//    }

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

    private void setDataAdapter() {
//        List<SuatChieu> dsChieuTheoPhim = new ArrayList<>();
//        for(SuatChieu suat : dsSuatChieu){
//            if(suat.getId_phim() == phim.getIdPhim()){
//                dsChieuTheoPhim.add(suat);
//            }
//        }
//        rapAdapter.setData(dsRap, dsPhong, dsChieuTheoPhim, dsGhe, listPhim);
//        Toast.makeText(getApplicationContext(), String.valueOf(dsChieuTheoPhim.size()), Toast.LENGTH_LONG).show();
    }


//    private void setAdapterrvRap() {
//        rvRap.addItemDecoration(new SpaceItemDecoration(200));
//        rvRap.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        rvRap.setAdapter(rapAdapter);
//    }

//    public void taoRap(){
//        dsRap = new ArrayList<>();
//        dsRap.add(new Rap(1, "Rạp Hồ Chí Minh", "Q9, Tp.Thu Duc, TP.HCM"));
//        dsRap.add(new Rap(2, "Rạp Hà Nội", "Q.Hoàng Mai, P.Hai Bà Trưng, Hà Nội"));
//        dsRap.add(new Rap(3, "Rạp Thanh Hoá", "P.Tào Xuyên, Tp.Thanh Hoá, Tỉnh Thanh Hoá"));
//    }
//    public void taoPhong(){
//        dsPhong = new ArrayList<>();
//        dsPhong.add(new Phong(1, "1HCM", 1));
//        dsPhong.add(new Phong(2, "2HCM", 1));
//        dsPhong.add(new Phong(3, "3HCM", 1));
//        dsPhong.add(new Phong(4, "1HN", 2));
//        dsPhong.add(new Phong(5, "2HN", 2));
//        dsPhong.add(new Phong(6, "3HN", 2));
//        dsPhong.add(new Phong(7, "1TH", 3));
//        dsPhong.add(new Phong(8, "2TH", 3));
//        dsPhong.add(new Phong(9, "3TH", 3));
//    }
//    public void taoGhe(){
//        dsGhe = new ArrayList<>();
//        dsGhe.add(new Ghe(1, "D", "1", "1", 1));
//        dsGhe.add(new Ghe(2, "D", "1", "2", 1));
//        dsGhe.add(new Ghe(3, "D", "1", "3", 1));
//        dsGhe.add(new Ghe(4, "D", "1", "4", 1));
//        dsGhe.add(new Ghe(5, "D", "2", "5", 1));
//        dsGhe.add(new Ghe(6, "D", "2", "6", 1));
//        dsGhe.add(new Ghe(7, "D", "2", "7", 1));
//        dsGhe.add(new Ghe(8, "D", "2", "8", 1));
//        dsGhe.add(new Ghe(9, "D", "3", "9", 1));
//        dsGhe.add(new Ghe(10, "D", "3", "10", 1));
//        dsGhe.add(new Ghe(11, "D", "3", "11", 1));
//        dsGhe.add(new Ghe(12, "D", "3", "12", 1));
//        dsGhe.add(new Ghe(13, "D", "3", "13", 1));
//        dsGhe.add(new Ghe(14, "D", "4", "14", 1));
//        dsGhe.add(new Ghe(15, "D", "4", "15", 1));
//        dsGhe.add(new Ghe(16, "D", "4", "16", 1));
//        dsGhe.add(new Ghe(17, "D", "4", "17", 1));
//        dsGhe.add(new Ghe(18, "D", "5", "18", 1));
//        dsGhe.add(new Ghe(19, "D", "5", "19", 1));
//        dsGhe.add(new Ghe(20, "D", "5", "20", 1));
//        dsGhe.add(new Ghe(1, "D", "1", "1", 2));
//    }
//    public void taoSuatChieu(){
//        dsSuatChieu = new ArrayList<>();
//        dsSuatChieu.add(new SuatChieu(1, "10:30", "English", "05/06/2024", "VietSub", 100000, 1, 1, 1));
//        dsSuatChieu.add(new SuatChieu(2, "12:30", "English", "05/06/2024", "VietSub", 100000, 1, 1, 1));
//        dsSuatChieu.add(new SuatChieu(3, "13:30", "English", "06/06/2024", "VietSub", 100000, 1, 1, 1));
//        dsSuatChieu.add(new SuatChieu(4, "10:30", "English", "05/06/2024", "VietSub", 100000, 1, 1,2));
//        dsSuatChieu.add(new SuatChieu(5, "10:30", "English", "05/06/2024", "VietSub", 100000, 1, 3,2));
//        dsSuatChieu.add(new SuatChieu(6, "16:30", "English", "06/06/2024", "VietSub", 100000, 1, 1, 3));
//        dsSuatChieu.add(new SuatChieu(7, "10:30", "English", "07/06/2024", "VietSub", 100000, 1, 2,2));
//        dsSuatChieu.add(new SuatChieu(8, "10:30", "English", "08/06/2024", "VietSub", 100000, 1, 1,2));
//        dsSuatChieu.add(new SuatChieu(9, "10:30", "English", "09/06/2024", "VietSub", 100000, 1, 1,2));
//        dsSuatChieu.add(new SuatChieu(10, "10:30", "English", "10/06/2024", "VietSub", 100000, 1, 1,2));
//        dsSuatChieu.add(new SuatChieu(11, "10:30", "English", "10/06/2024", "VietSub", 100000, 1, 1,4));
//    }

    private void setDataRate(){
        SachApi.sachApi.getRateById(sach.getId()).enqueue(new Callback<List<DanhGiaResponse>>() {
            @Override
            public void onResponse(Call<List<DanhGiaResponse>> call, Response<List<DanhGiaResponse>> response) {
                assert response.body() != null;
                danhGiaResponseList = new ArrayList<>(response.body());
                fetchDataCmt(danhGiaResponseList);
                if(!response.body().isEmpty()){
                    int totalPoint = 0;
                    for(DanhGiaResponse d:response.body()){
                        totalPoint += d.getPoint();
                    }
                    double avgPoint = (double) totalPoint /response.body().size();
                    avgPoint = Math.round(avgPoint * 100.0)/100.0;
                    tvAvgPointOfRate.setText(avgPoint + "/5 Sao");
                    tvNumOfRate.setText(response.body().size() + " đánh giá");
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

            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void setDataIntent() {
        Intent intent = getIntent();
        sach = (Sach) intent.getSerializableExtra("sach");
        sachList = (List<Sach>) intent.getSerializableExtra("sachList");
        luotDocSach = (LuotDocSach) intent.getSerializableExtra("readedCount");
        countAllFavor = (CountAllFavor) intent.getSerializableExtra("favorCount");
        sachFavors = (List<Sach>) intent.getSerializableExtra("sachFavors");
        sachMongMuonList = (List<Sach_Mong_Muon>) intent.getSerializableExtra("sachMongMuonList");
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

    @Override
    public void onItemClick(SuatChieu suat, List<Ghe> listGhe, Phim phim) {
//        Intent intent = new Intent(this, ChonGhe.class);
//        intent.putExtra("suat", suat);
//        intent.putExtra("phim", phim);
//        intent.putExtra("ghe",(Serializable) listGhe);
//        startActivity(intent);
    }

}