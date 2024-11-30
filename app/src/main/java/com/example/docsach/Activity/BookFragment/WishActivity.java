package com.example.docsach.Activity.BookFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.API.ReaderApi;
import com.example.docsach.API.WishApi;
import com.example.docsach.Adapter.BookFragment.WishAdapter;
import com.example.docsach.Model.DTO.LichSuMuaRequest;
import com.example.docsach.Model.DTO.PurchaseRequest;
import com.example.docsach.Model.DTO.ReaderUpdate;
import com.example.docsach.Model.Key.Key_CT_Goi_Dang_Ky;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishActivity extends AppCompatActivity implements WishAdapter.ItemInterface {
    private RecyclerView rvWish;
    private Button btnPuchase, btnEmpty;
    private WishAdapter adapter;

    private List<Sach_Mong_Muon> sachMongMuonList;
    private List<Long> selectItem = new ArrayList<Long>();
    private List<LichSuMuaRequest> lichSuMuaRequestList = new ArrayList<LichSuMuaRequest>();
    private Reader reader;
    private BigDecimal total = BigDecimal.valueOf(0), amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wish);

        adapter = new WishAdapter(this, this.getApplicationContext());

        getDataIntent();
        setControl();
        setRV();

        adapter.setData(sachMongMuonList);

        btnPuchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lichSuMuaRequestList.isEmpty()){
                    PurchaseRequest purchaseRequest = new PurchaseRequest(lichSuMuaRequestList, selectItem);
                    BigDecimal soDu = amount.subtract(total);
                    if(amount.compareTo(total) >= 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(WishActivity.this);
                        builder.setTitle("Xác nhận thanh toán cho " + lichSuMuaRequestList.size() + " sách")
                                .setMessage("Thanh toán số tiền " + formatAmount(total) + "\n Số dư còn lại: " + formatAmount(soDu))
                                .setPositiveButton("Thanh toán", (dialog, which) -> {
                                    ReaderApi.readerApi.purchaseBooks(purchaseRequest).enqueue(new Callback<ResponseBody>() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if(response.isSuccessful()){
                                                AlertDialog.Builder builder = new AlertDialog.Builder(WishActivity.this);
                                                builder.setTitle("Thành công thanh toán cho " + lichSuMuaRequestList.size() + " sách.")
                                                        .setPositiveButton("OK", (dialog, which) -> {
                                                            dialog.dismiss();
                                                        })
                                                        .show();
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                    sachMongMuonList.removeIf(sachMongMuon -> selectItem.contains(sachMongMuon.getId()));
                                                }
                                                lichSuMuaRequestList.clear();
                                                selectItem.clear();
                                                btnPuchase.setText("");
                                                btnPuchase.setVisibility(View.GONE);
                                                adapter.clearSelected();
                                                adapter.notifyDataSetChanged();
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(WishActivity.this);
                        builder.setTitle("Số dư không đủ để thanh toán")
                                .setMessage("Còn thiếu " + formatAmount(total.subtract(amount)) + " vui lòng nạp thêm tiền")
                                .setPositiveButton("OK", (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .show();
                    }
                }
            }
        });

        btnEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if(!sachMongMuonList.isEmpty()){
                        WishApi.wishApi.clearWishList(reader.getId()).enqueue(new Callback<ResponseBody>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()){
                                    sachMongMuonList.clear();
                                    selectItem.clear();
                                    lichSuMuaRequestList.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });

    }
    public void getDataIntent(){
        Intent intent = getIntent();
        sachMongMuonList = (List<Sach_Mong_Muon>) intent.getSerializableExtra("wish");
        reader = (Reader) intent.getSerializableExtra("reader");
        amount = (BigDecimal) intent.getSerializableExtra("amount");

    }
    public void setControl(){
        rvWish = findViewById(R.id.rvWish);
        btnEmpty = findViewById(R.id.btnEmpty);
        btnPuchase = findViewById(R.id.btnPuchase);

        btnPuchase.setVisibility(View.GONE);
        if(!sachMongMuonList.isEmpty()){
            btnEmpty.setVisibility(View.VISIBLE);
        }else{
            btnEmpty.setVisibility(View.GONE);
        }
    }
    public void setRV(){
        rvWish.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvWish.setAdapter(adapter);
    }

    @Override
    public void onClick(View view, int position) {
        Sach_Mong_Muon sach_mong_muon = adapter.getItem(position);
        LichSuMuaRequest lichSuMuaRequest = new LichSuMuaRequest(sach_mong_muon.getSachWish().getGiaTien(), sach_mong_muon.getSachWish(), reader);
        if(selectItem.contains(sach_mong_muon.getId())){
            selectItem.remove(sach_mong_muon.getId());
            if(lichSuMuaRequestList.contains(lichSuMuaRequest)){
                lichSuMuaRequestList.remove(lichSuMuaRequest);
            }

        }else{
            selectItem.add(sach_mong_muon.getId());
            lichSuMuaRequestList.add(lichSuMuaRequest);
        }
        BigDecimal tempPrice = BigDecimal.valueOf(0);
        if(!lichSuMuaRequestList.isEmpty()){
            for (LichSuMuaRequest l : lichSuMuaRequestList){
                tempPrice = tempPrice.add(l.getGiaTien());
            }
            total = tempPrice;
            String item = "Đã chọn " + lichSuMuaRequestList.size() + " sách. " + formatAmount(total);
            Log.i("totalItem", item);
            btnPuchase.setText(item);
            btnPuchase.setVisibility(View.VISIBLE);
        }else{
            btnPuchase.setText("");
            btnPuchase.setVisibility(View.GONE);
        }

    }
    private String formatAmount(BigDecimal amo){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);
        return formatter.format(amo) + " VND";
    }

    @Override
    public void onRemoveClick(View view, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Sach_Mong_Muon sachMongMuon = adapter.getItem(position);
            if(sachMongMuon != null){
                Long id = sachMongMuon.getId();
                ReaderApi.readerApi.deleteWishItem(id).enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            sachMongMuonList.remove(sachMongMuon);
                            if(!sachMongMuonList.isEmpty()){
                                btnEmpty.setVisibility(View.VISIBLE);
                            }else{
                                btnEmpty.setVisibility(View.GONE);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        }
    }
}