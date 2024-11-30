package com.example.docsach.Activity.SettingFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Adapter.Setting.BuyHistoryAdapter;
import com.example.docsach.Adapter.Setting.PackHistoryAdapter;
import com.example.docsach.Model.DTO.CT_DangKyResponse;
import com.example.docsach.Model.HoaDon;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.R;

import java.util.List;

public class History extends AppCompatActivity {

    private BuyHistoryAdapter adapter;
    private PackHistoryAdapter packAdapter;

    private RecyclerView rvBuy, rvBuyPack;

    private List<LichSuMua> lichSuMuaList;
    private List<CT_DangKyResponse> ctDangKyResponses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        adapter = new BuyHistoryAdapter(this);
        packAdapter = new PackHistoryAdapter(this);

        getDatIntent();
        setControl();
        setRV();

        adapter.setData(lichSuMuaList);
        packAdapter.setData(ctDangKyResponses);

    }

    private void setRV() {
        rvBuy.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvBuy.setAdapter(adapter);

        rvBuyPack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvBuyPack.setAdapter(packAdapter);
    }

    private void setControl() {
        rvBuy = findViewById(R.id.rvBuyHis);
        rvBuyPack = findViewById(R.id.rvBuyPack);
    }

    private void getDatIntent() {
        Intent intent = getIntent();
        lichSuMuaList = (List<LichSuMua>) intent.getSerializableExtra("buyHistory");
        ctDangKyResponses = (List<CT_DangKyResponse>) intent.getSerializableExtra("packPurchased");
    }
}