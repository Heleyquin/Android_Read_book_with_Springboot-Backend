package com.example.docsach.Activity.PackFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.API.PackApi;
import com.example.docsach.API.ReaderApi;
import com.example.docsach.API.SachApi;
import com.example.docsach.Activity.BookFragment.Book_Detail;
import com.example.docsach.Adapter.PackFragment.PackAdapter;
import com.example.docsach.Model.CT_Goi;
import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.DTO.CT_DangKyRequest;
import com.example.docsach.Model.DTO.GoiDangKyResponse;
import com.example.docsach.Model.DTO.LichSuMuaRequest;
import com.example.docsach.Model.DTO.PackInUse;
import com.example.docsach.Model.Ghe;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.LuotDocSach;
import com.example.docsach.Model.Phim;
import com.example.docsach.Model.Phong;
import com.example.docsach.Model.Rap;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.Model.SuatChieu;
import com.example.docsach.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackFragment extends Fragment implements PackAdapter.ItemInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvGoi;
    private PackAdapter packAdapter;
    private TextView tvAmount;

    private List<GoiDangKyResponse> goiDangKyResponseList, goiDangKyResponseListFil;
    private List<Sach> sachFavors;
    private Reader reader;
    private List<CountAllFavor> countAllFavors;
    private List<LuotDocSach> luotDocSaches;
    private List<Sach_Mong_Muon> sachMongMuonList;
    private List<LichSuMua> lichSuMuaList;
    private List<CT_Goi> ctGoiList;
    private PackInUse packInUse;

    private List<Rap> dsRap, dsRapFil;
    private List<Phim> dsPhim;
    private List<SuatChieu> dsSuatChieu;
    private List<Phong> dsPhong;
    private List<Ghe> dsGhe;
    BigDecimal amount;

    private SearchView SearchBar;

    public PackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PackFragment newInstance(String param1, String param2) {
        PackFragment fragment = new PackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        packAdapter = new PackAdapter(this, getContext());

        return inflater.inflate(R.layout.fragment_rap, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        rvGoi = view.findViewById(R.id.rvGoi);
        SearchBar = view.findViewById(R.id.SearchBar);
        tvAmount = view.findViewById(R.id.tvAmount);

        setData();

        fetchPacks();
        fetchUser();
        fetchBooks();

        SearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
            private void filter(String s) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String searchFormat = removeDiacritics(s.toLowerCase().strip());
                    if(searchFormat.isEmpty()){
                        goiDangKyResponseList.clear();
                        goiDangKyResponseList.addAll(goiDangKyResponseListFil);
                        packAdapter.notifyDataSetChanged();

                    }
                    else{
                        goiDangKyResponseList.clear();
                        goiDangKyResponseList.addAll(goiDangKyResponseListFil.stream()
                                .filter(goi -> removeDiacritics(goi.getChuThich().strip().toLowerCase()).contains(searchFormat))
                                .collect(Collectors.toList()));
                        packAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    private String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private void setData() {
        rvGoi.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGoi.setAdapter(packAdapter);
    }
    private void fetchPacks(){
        PackApi.packApi.allPacks().enqueue(new Callback<List<GoiDangKyResponse>>() {
            @Override
            public void onResponse(Call<List<GoiDangKyResponse>> call, Response<List<GoiDangKyResponse>> response) {
                assert response.body() != null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    goiDangKyResponseList = new ArrayList<GoiDangKyResponse>(response.body()).stream()
                            .filter(item -> item.isActive())
                            .collect(Collectors.toList());
                };
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    goiDangKyResponseListFil = new ArrayList<GoiDangKyResponse>(response.body()).stream()
                            .filter(item -> item.isActive())
                            .collect(Collectors.toList());
                };
                if(goiDangKyResponseList != null){
                    packAdapter.setData(goiDangKyResponseList);
                }
            }

            @Override
            public void onFailure(Call<List<GoiDangKyResponse>> call, Throwable t) {

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
    private void getAmount(Long id){
        ReaderApi.readerApi.getAmountById(id).enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    String formated = result.replace(",", ".");
                    Double am = Double.parseDouble(formated);
                    amount = BigDecimal.valueOf(am);
                    tvAmount.setText("Số dư: " + formatAmount(amount));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                amount = null;
                Log.i("amount", "failed");
            }
        });
    }
    public void fetchUser(){
        ReaderApi.readerApi.getReaderByUsername("user3").enqueue(new Callback<Reader>() {
            @Override
            public void onResponse(Call<Reader> call, Response<Reader> response) {
                assert response.body() != null;
                reader = response.body();
                fetChPackInUse(response.body().getId());
                sachFavors = new ArrayList<>(response.body().getSachs());
                Long readerId = response.body().getId();
                getAmount(readerId);
                ReaderApi.readerApi.getAllWhistById(readerId).enqueue(new Callback<List<Sach_Mong_Muon>>() {
                    @Override
                    public void onResponse(Call<List<Sach_Mong_Muon>> call, Response<List<Sach_Mong_Muon>> response) {
                        assert response.body() != null;
                        sachMongMuonList = new ArrayList<>(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Sach_Mong_Muon>> call, Throwable t) {

                    }
                });
                ReaderApi.readerApi.getLichSuMuaById(readerId).enqueue(new Callback<List<LichSuMua>>() {
                    @Override
                    public void onResponse(Call<List<LichSuMua>> call, Response<List<LichSuMua>> response) {
                        assert response.body() != null;
                        lichSuMuaList = new ArrayList<>(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<LichSuMua>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Reader> call, Throwable t) {

            }
        });
    }

    public void fetchBooks(){
        SachApi.sachApi.bookWithCountFavor().enqueue(new Callback<List<CountAllFavor>>() {
            @Override
            public void onResponse(Call<List<CountAllFavor>> call, Response<List<CountAllFavor>> response) {
                assert response.body() != null;
                countAllFavors = new ArrayList<CountAllFavor>(response.body());
            }

            @Override
            public void onFailure(Call<List<CountAllFavor>> call, Throwable t) {

            }
        });
        SachApi.sachApi.bookWithReadedCount().enqueue(new Callback<List<LuotDocSach>>() {
            @Override
            public void onResponse(Call<List<LuotDocSach>> call, Response<List<LuotDocSach>> response) {
                assert response.body() != null;
                luotDocSaches = new ArrayList<LuotDocSach>(response.body());
            }

            @Override
            public void onFailure(Call<List<LuotDocSach>> call, Throwable t) {

            }
        });
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
                            packAdapter.setDataPackInUse(packInUse);
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

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(view.getContext(), Pack.class);
        GoiDangKyResponse goiDangKyResponse = goiDangKyResponseList.get(position);
        intent.putExtra("pack", goiDangKyResponse);
        intent.putExtra("reader", reader);
        intent.putExtra("sachFavors",(Serializable) sachFavors);
        intent.putExtra("sachMongMuonList",(Serializable) sachMongMuonList);
        intent.putExtra("lichSuMuaList",(Serializable) lichSuMuaList);
        intent.putExtra("luotDoc",(Serializable) luotDocSaches);
        intent.putExtra("countFavors", (Serializable) countAllFavors);
        intent.putExtra("reader", reader);
        intent.putExtra("amount", amount);

        startActivity(intent);
    }

    @Override
    public void onItemClickBuy(View view, int position) {
        GoiDangKyResponse goiDangKyResponse = packAdapter.getItem(position);
        if(packInUse != null){
            if(!Objects.equals(packInUse.getMaGoi(), goiDangKyResponse.getMaGoi())){
                OffsetDateTime offsetDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    offsetDateTime = OffsetDateTime.parse(packInUse.getExpirDate());
                    LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration = Duration.between(now, localDateTime);
                    long dayDiff = duration.toDays();
                    long hourDiff = duration.toHours();
                    long minDiff = duration.toMinutes();
                    long secDiff = duration.getSeconds();
                    if(!duration.isNegative()){
                        BigDecimal giaGoiDecimal = goiDangKyResponse.getGiaTien();
                        BigDecimal soDu = amount.subtract(giaGoiDecimal);
                        if(amount.compareTo(giaGoiDecimal) >= 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                            builder.setTitle("Xác nhận mua gói có mã: " + goiDangKyResponse.getMaGoi())
                                    .setMessage( "Thanh toán số tiền " + formatAmount(giaGoiDecimal) + "\n Số dư còn lại: " + formatAmount(soDu) + "\n Gói đang sử dụng vẫn còn thời gian hiệu lực, nếu mua gói mới sẽ vô hiệu gói cũ, bạn chắc chắn chứ??")
                                    .setPositiveButton("Thanh toán", (dialog, which) -> {
                                        CT_DangKyRequest ctDangKyRequest = new CT_DangKyRequest(goiDangKyResponse.getMaGoi(), reader.getId(), giaGoiDecimal);
                                        ReaderApi.readerApi.dangKyGoi(ctDangKyRequest).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if(response.isSuccessful()){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    builder.setTitle("Thành công mua gói có mã: " + goiDangKyResponse.getMaGoi())
                                                            .setPositiveButton("OK", (dialog, which) -> {
                                                                ReaderApi.readerApi.goiDangDung(reader.getId()).enqueue(new Callback<ResponseBody>() {
                                                                    @SuppressLint("NotifyDataSetChanged")
                                                                    @Override
                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                        if(response.isSuccessful()){
                                                                            String m = null;
                                                                            assert response.body() != null;
                                                                            try {
                                                                                m = response.body().string();
                                                                                if(!m.trim().isEmpty()){
                                                                                    Gson gson = new Gson();
                                                                                    String[] jsonArray = gson.fromJson(m, String[].class);
                                                                                    packInUse = new PackInUse();
                                                                                    packInUse.setMaGoi(jsonArray[0]);
                                                                                    packInUse.setChuThich(jsonArray[1]);
                                                                                    packInUse.setThoiGianDangKy(jsonArray[2]);
                                                                                    packInUse.setThoiHan(Integer.parseInt(jsonArray[3]));
                                                                                    packInUse.setExpirDate(jsonArray[4]);
                                                                                    packAdapter.setDataPackInUse(packInUse);
                                                                                }
                                                                            } catch (
                                                                                    IOException e) {
                                                                                throw new RuntimeException(e);
                                                                            }
                                                                            dialog.dismiss();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                            builder.setTitle("Số dư không đủ để mua gói " + goiDangKyResponse.getMaGoi())
                                    .setMessage("Còn thiếu " + formatAmount(giaGoiDecimal.subtract(amount)) + " vui lòng nạp thêm tiền")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .show();
                        }
                    }else{
                        BigDecimal giaGoiDecimal = goiDangKyResponse.getGiaTien();
                        BigDecimal soDu = amount.subtract(giaGoiDecimal);
                        if(amount.compareTo(giaGoiDecimal) >= 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                            builder.setTitle("Xác nhận mua gói có mã: " + goiDangKyResponse.getMaGoi())
                                    .setMessage( "Thanh toán số tiền " + formatAmount(giaGoiDecimal) + "\n Số dư còn lại: " + formatAmount(soDu))
                                    .setPositiveButton("Thanh toán", (dialog, which) -> {
                                        CT_DangKyRequest ctDangKyRequest = new CT_DangKyRequest(goiDangKyResponse.getMaGoi(), reader.getId(), giaGoiDecimal);
                                        ReaderApi.readerApi.dangKyGoi(ctDangKyRequest).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if(response.isSuccessful()){
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    builder.setTitle("Thành công mua gói có mã: " + goiDangKyResponse.getMaGoi())
                                                            .setPositiveButton("OK", (dialog, which) -> {
                                                                ReaderApi.readerApi.goiDangDung(reader.getId()).enqueue(new Callback<ResponseBody>() {
                                                                    @SuppressLint("NotifyDataSetChanged")
                                                                    @Override
                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                        if(response.isSuccessful()){
                                                                            String m = null;
                                                                            assert response.body() != null;
                                                                            try {
                                                                                m = response.body().string();
                                                                                if(!m.trim().isEmpty()){
                                                                                    Gson gson = new Gson();
                                                                                    String[] jsonArray = gson.fromJson(m, String[].class);
                                                                                    packInUse = new PackInUse();
                                                                                    packInUse.setMaGoi(jsonArray[0]);
                                                                                    packInUse.setChuThich(jsonArray[1]);
                                                                                    packInUse.setThoiGianDangKy(jsonArray[2]);
                                                                                    packInUse.setThoiHan(Integer.parseInt(jsonArray[3]));
                                                                                    packInUse.setExpirDate(jsonArray[4]);
                                                                                    packAdapter.setDataPackInUse(packInUse);                                                                                }
                                                                            } catch (
                                                                                    IOException e) {
                                                                                throw new RuntimeException(e);
                                                                            }
                                                                            dialog.dismiss();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                            builder.setTitle("Số dư không đủ để mua gói " + goiDangKyResponse.getMaGoi())
                                    .setMessage("Còn thiếu " + formatAmount(giaGoiDecimal.subtract(amount)) + " vui lòng nạp thêm tiền")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .show();
                        }
                    }
                }
            }
        }else{
            BigDecimal giaGoiDecimal = goiDangKyResponse.getGiaTien();
            BigDecimal soDu = amount.subtract(giaGoiDecimal);
            if(amount.compareTo(giaGoiDecimal) >= 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("Xác nhận mua gói có mã: " + goiDangKyResponse.getMaGoi())
                        .setMessage( "Thanh toán số tiền " + formatAmount(giaGoiDecimal) + "\n Số dư còn lại: " + formatAmount(soDu) + "\n Gói đang sử dụng vẫn còn thời gian hiệu lực, nếu mua gói mới sẽ vô hiệu gói cũ, bạn chắc chắn chứ??")
                        .setPositiveButton("Thanh toán", (dialog, which) -> {
                            CT_DangKyRequest ctDangKyRequest = new CT_DangKyRequest(goiDangKyResponse.getMaGoi(), reader.getId(), giaGoiDecimal);
                            ReaderApi.readerApi.dangKyGoi(ctDangKyRequest).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Thành công mua gói có mã: " + goiDangKyResponse.getMaGoi())
                                                .setPositiveButton("OK", (dialog, which) -> {
                                                    ReaderApi.readerApi.goiDangDung(reader.getId()).enqueue(new Callback<ResponseBody>() {
                                                        @SuppressLint("NotifyDataSetChanged")
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            if(response.isSuccessful()){
                                                                String m = null;
                                                                assert response.body() != null;
                                                                try {
                                                                    m = response.body().string();
                                                                    if(!m.trim().isEmpty()){
                                                                        Gson gson = new Gson();
                                                                        String[] jsonArray = gson.fromJson(m, String[].class);
                                                                        packInUse = new PackInUse();
                                                                        packInUse.setMaGoi(jsonArray[0]);
                                                                        packInUse.setChuThich(jsonArray[1]);
                                                                        packInUse.setThoiGianDangKy(jsonArray[2]);
                                                                        packInUse.setThoiHan(Integer.parseInt(jsonArray[3]));
                                                                        packInUse.setExpirDate(jsonArray[4]);
                                                                        packAdapter.setDataPackInUse(packInUse);
                                                                    }
                                                                } catch (
                                                                        IOException e) {
                                                                    throw new RuntimeException(e);
                                                                }
                                                                dialog.dismiss();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("Số dư không đủ để mua gói " + goiDangKyResponse.getMaGoi())
                        .setMessage("Còn thiếu " + formatAmount(giaGoiDecimal.subtract(amount)) + " vui lòng nạp thêm tiền")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchPacks();
        fetchUser();
        fetchBooks();
    }
    //    private List<SuatChieu> getSuatChieuByRap(int idRap) {
//        List<SuatChieu> listSuatChieuOfRap;
//        List<Phong> phongOfRap = new ArrayList<>();
//        for(Phong p : dsPhong){
//            if(p.getId_rap() == idRap){
//                phongOfRap.add(p);
//            }
//        }
//        Set<Integer> phongIds = phongOfRap.stream()
//                .map(Phong::getIdPhong)
//                .collect(Collectors.toSet());
//        listSuatChieuOfRap = dsSuatChieu.stream()
//                .filter(suat -> phongIds.contains(suat.getId_phong()))
//                .collect(Collectors.toList());
//        return listSuatChieuOfRap;
//    }
//    private List<Phim> getPhimBySuat(@NonNull List<SuatChieu> suatByRap){
//        List<Phim> phimByRap;
//        Set<Integer> suatIdPhims = suatByRap.stream()
//                .map(SuatChieu::getId_phim)
//                .collect(Collectors.toSet());
//        phimByRap = dsPhim.stream()
//                .filter(phim -> suatIdPhims.contains(phim.getIdPhim()))
//                .collect(Collectors.toList());
//        return phimByRap;
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
//    private void taoPhim() {
//        dsPhim = new ArrayList<>();
//        dsPhim.add(new Phim(1, "https://files.betacorp.vn/media%2fimages%2f2024%2f05%2f28%2f310524%2Dgarfield%2D150640%2D280524%2D95.jpg", "Phim1", "QuocGia", "29/06/2015","Trang thai", 102, "Mo ta", true, 1));
//        dsPhim.add(new Phim(2, "https://files.betacorp.vn/media%2fimages%2f2024%2f04%2f24%2f240524%2Ddraft%2Ddoraemon%2D170958%2D240424%2D90.png", "Phim2", "QuocGia", "03/05/2024", "Trang thai", 95, "Mo ta", true, 1));
//        dsPhim.add(new Phim(3, "https://files.betacorp.vn/media%2fimages%2f2024%2f05%2f27%2f400x633%2D7%2D151139%2D270524%2D46.jpg", "Phim3", "QuocGia", "23/04/2024", "Trang thai", 120, "Mo ta", false, 1));
//        dsPhim.add(new Phim(4, "https://files.betacorp.vn/media%2fimages%2f2024%2f05%2f24%2f400x633%2D6%2D103906%2D240524%2D41.jpg", "Phim4", "QuocGia", "22/04/2024", "Trang thai", 84, "Mo ta", true, 1));
//        dsPhim.add(new Phim(5, "https://files.betacorp.vn/media%2fimages%2f2024%2f05%2f28%2f070624%2Dsneak%2Dmong%2Dvuot%2D150957%2D280524%2D53.jpg", "Phim5", "QuocGia", "11/04/2024", "Trang thai", 99, "Mo ta", true, 1));
//    }
//    public void taoRap(){
//        dsRap = new ArrayList<>();
//        dsRap.add(new Rap(1, "Rạp Hồ Chí Minh", "Q9, Tp.Thu Duc, TP.HCM"));
//        dsRap.add(new Rap(2, "Rạp Hà Nội", "Q.Hoàng Mai, P.Hai Bà Trưng, Hà Nội"));
//        dsRap.add(new Rap(3, "Rạp Thanh Hoá", "P.Tào Xuyên, Tp.Thanh Hoá, Tỉnh Thanh Hoá"));
//        dsRapFil = new ArrayList<>();
//        dsRapFil.addAll(dsRap);
//    }
}