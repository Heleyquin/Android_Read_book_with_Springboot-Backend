package com.example.docsach.Activity.SettingFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.docsach.API.ReaderApi;
import com.example.docsach.Activity.ForAll.MainActivity;
import com.example.docsach.Model.Account;
import com.example.docsach.Model.DTO.CT_DangKyResponse;
import com.example.docsach.Model.DTO.PackInUse;
import com.example.docsach.Model.HoaDon;
import com.example.docsach.Model.KhachHang;
import com.example.docsach.Model.LichSuDoc;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvUser, tvHistoryBuy, tvHistoryRead, tvOut;

    private List<LichSuMua> lichSuMuaList;
    private List<LichSuDoc> lichSuDocList;
    private Reader reader;
    private PackInUse packInUse;
    private List<CT_DangKyResponse> ctDangKyResponses;
    private BigDecimal amount;
    private String username;

    public SettingFragment(String username) {
        this.username = username;
    }

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        // Inflate the layout for this fragment

        fetchUser();


        return inflater.inflate(R.layout.fragment_setting, container, false);
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
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    String formated = result.replace(",", ".");
                    Double am = Double.parseDouble(formated);
                    amount = BigDecimal.valueOf(am);
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
        ReaderApi.readerApi.getReaderByUsername(username).enqueue(new Callback<Reader>() {
            @Override
            public void onResponse(Call<Reader> call, Response<Reader> response) {
                assert response.body() != null;
                reader = response.body();
                lichSuDocList = new ArrayList<>(response.body().getLichSuDocs());
                Long readerId = response.body().getId();
                getAmount(readerId);
                fetChPackInUse(readerId);
                fetchPackPurchased(readerId);
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
//                QuanLy quanLy = sachFavors.get(0).getIdQuanLy();
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                String jsonQL = gson.toJson(quanLy);
//                String jsonFavor = gson.toJson(sachFavors);
//                Log.i("quản lý", jsonQL);
//                Log.i("người dùng", jsonFavor);
            }

            @Override
            public void onFailure(Call<Reader> call, Throwable t) {

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
                            packInUse.setActive(Boolean.valueOf(jsonArray[5]));
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
    private void fetchPackPurchased(Long id){
        ReaderApi.readerApi.allPackPurchased(id).enqueue(new Callback<List<CT_DangKyResponse>>() {
            @Override
            public void onResponse(Call<List<CT_DangKyResponse>> call, Response<List<CT_DangKyResponse>> response) {
                ctDangKyResponses = new ArrayList<>(response.body());
            }

            @Override
            public void onFailure(Call<List<CT_DangKyResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUser = view.findViewById(R.id.tvUser);
        tvHistoryBuy = view.findViewById(R.id.tvHistoryBuy);
        tvOut = view.findViewById(R.id.tvOut);
        tvHistoryRead = view.findViewById(R.id.tvHistoryRead);

        tvUser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(view.getContext(), User.class);
//                intent.putExtra("user", user);
//                startActivity(intent);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_user, null);

                TextView tvTen, tvAmount, tvNgayTao, tvPack;
                tvTen = dialogView.findViewById(R.id.tvTen);
                tvAmount = dialogView.findViewById(R.id.tvAmount);
                tvNgayTao = dialogView.findViewById(R.id.tvNgayTao);
                tvPack = dialogView.findViewById(R.id.tvPack);

                builder.setView(dialogView);

                if(packInUse != null){
                    if(packInUse.getActive() == true){
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
                            if (!duration.isNegative()) {
                                if(dayDiff > 0){
                                    tvPack.setText("Gói đang dùng: " + packInUse.getMaGoi() + " .Thời gian còn lại: " + dayDiff + " ngày.");
                                }else{
                                    if(hourDiff > 0){
                                        tvPack.setText("Gói đang dùng: " + packInUse.getMaGoi() + " .Thời gian còn lại: " + hourDiff + " giờ.");
                                    }else{
                                        if(minDiff > 0){
                                            tvPack.setText("Gói đang dùng: " + packInUse.getMaGoi() + " .Thời gian còn lại: " + minDiff + " phút.");
                                        }else{
                                            tvPack.setText("Gói đang dùng: " + packInUse.getMaGoi() + " .Sắp hết hạn");
                                        }
                                    }
                                }
                            }else{
                                tvPack.setText("Gói đang dùng: Không có gói nào đang sử dụng.");
                            }
                        }
                    }else{
                        tvPack.setText("Gói đang dùng: Không có gói nào đang sử dụng.");
                    }
                }else{
                    tvPack.setText("Gói đang dùng: Không có gói nào đang sử dụng.");
                }
                if(reader != null){
                    tvTen.setText(reader.getHo() + " " + reader.getTen());
                    LocalDateTime dateTime = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        dateTime = LocalDateTime.parse(reader.getNgayTao());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        String formattedDate = dateTime.format(formatter);
                        tvNgayTao.setText("Ngày tạo tài khoản: " + formattedDate);
                    }
                }
                if(amount != null){
                    tvAmount.setText("Số dư: " + formatAmount(amount));
                }

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        tvHistoryBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), History.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    lichSuMuaList.sort((m1, m2) ->{
                        LocalDateTime time1 = LocalDateTime.parse(m1.getThoiGianMua());
                        LocalDateTime time2 = LocalDateTime.parse(m2.getThoiGianMua());
                        return time2.compareTo(time1);
                    });
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ctDangKyResponses.sort((ct1, ct2) ->{
                        LocalDateTime time1 = LocalDateTime.parse(ct1.getId().getThoiGianDangKy());
                        LocalDateTime time2 = LocalDateTime.parse(ct2.getId().getThoiGianDangKy());
                        return time2.compareTo(time1);
                    });
                }
                intent.putExtra("buyHistory", (Serializable) lichSuMuaList);
                intent.putExtra("packPurchased", (Serializable) ctDangKyResponses);

                startActivity(intent);
            }
        });

        tvHistoryRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ReadHistory.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    lichSuDocList.sort((l1, l2) ->{
                        LocalDateTime time1 = LocalDateTime.parse(l1.getThoiGian());
                        LocalDateTime time2 = LocalDateTime.parse(l2.getThoiGian());
                        return time2.compareTo(time1);
                    });
                }
                intent.putExtra("readHis", (Serializable) lichSuDocList);

                startActivity(intent);
            }
        });

        tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialogOut();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void showConfirmationDialogOut() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Bạn có chắc chắn muốn đăng xuất??");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ĐĂNG XUẤT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        // Bắt đầu MainActivity
                        startActivity(intent);
                        // Kết thúc toàn bộ ứng dụng
                        getActivity().finishAffinity();
                        dialog.dismiss();
                    }
                });

        builder1.setNegativeButton(
                "HUỶ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}