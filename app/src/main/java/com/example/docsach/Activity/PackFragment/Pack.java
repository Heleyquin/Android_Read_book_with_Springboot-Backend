package com.example.docsach.Activity.PackFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Activity.BookFragment.Book_Detail;
import com.example.docsach.Adapter.PackFragment.BookByPackAdapter;
import com.example.docsach.Model.CT_Goi;
import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.DTO.GoiDangKyResponse;
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
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Pack extends AppCompatActivity implements BookByPackAdapter.ItemInterface{

    private List<Sach> sachList, sachListFil, sachFavors;

    private List<CountAllFavor> countAllFavors;
    private List<LuotDocSach> luotDocSaches;
    private List<LichSuMua> lichSuMuaList;
    private Reader reader;
    BigDecimal amount;

    private RecyclerView rvBook;
    private TextView tvMaGoi;
    private BookByPackAdapter adapter;
    private SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_by_pack);

        adapter = new BookByPackAdapter(this, getApplicationContext());

        setControl();
        setDataIntent();
        setRV();
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
            @SuppressLint("NotifyDataSetChanged")
            private void filter(String s) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String searchFormat = removeDiacritics(s.toLowerCase().strip());
                    if(searchFormat.isEmpty()){
                        sachList.clear();
                        sachList.addAll(sachListFil);
                        adapter.notifyDataSetChanged();

                    }
                    else{
                        sachList.clear();
                        sachList.addAll(sachListFil.stream()
                                .filter(goi -> removeDiacritics(goi.getTenSach().strip().toLowerCase()).contains(searchFormat))
                                .collect(Collectors.toList()));
                        adapter.notifyDataSetChanged();
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
    @SuppressLint("SetTextI18n")
    private void setDataIntent() {
        Intent intent = getIntent();
        GoiDangKyResponse goiDangKyResponse = (GoiDangKyResponse) intent.getSerializableExtra("pack");
        reader = (Reader) intent.getSerializableExtra("reader");
        sachFavors = (List<Sach>) intent.getSerializableExtra("sachFavors");
        lichSuMuaList = (List<LichSuMua>) intent.getSerializableExtra("lichSuMuaList");
        luotDocSaches = (List<LuotDocSach>)  intent.getSerializableExtra("luotDoc");
        countAllFavors = (List<CountAllFavor>) intent.getSerializableExtra("countFavors");
        amount = (BigDecimal) intent.getSerializableExtra("amount");

        assert goiDangKyResponse != null;
        tvMaGoi.setText("Gói " + goiDangKyResponse.getMaGoi() + "\n" + goiDangKyResponse.getChuThich());
        List<CT_Goi> ctGoi = goiDangKyResponse.getCtGoiSet().stream().toList();
        sachList = new ArrayList<>();
        sachListFil = new ArrayList<>();
        sachList = ctGoi.stream()
                .map(CT_Goi::getSach)
                .filter(item -> item.isActive())
                .collect(Collectors.toList());
        sachListFil = ctGoi.stream()
                .map(CT_Goi::getSach)
                .filter(item -> item.isActive())
                .collect(Collectors.toList());


    }
    @SuppressLint("SetTextI18n")
    private void setControl(){
        rvBook = findViewById(R.id.rvBook);
        tvMaGoi = findViewById(R.id.tvPackName);
        searchBar = findViewById(R.id.searchBar);

    }
    private void setRV(){
        rvBook.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvBook.setAdapter(adapter);

        adapter.setData(sachList, countAllFavors, luotDocSaches, sachFavors);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), Book_Detail.class);

        intent.putExtra("sach", adapter.getItem(position));
        intent.putExtra("favorCount", adapter.getCountFavorItem(adapter.getItem(position)));
        intent.putExtra("readedCount", adapter.getReadedCountItem(adapter.getItem(position)));
        intent.putExtra("lichSuMuaList",(Serializable) lichSuMuaList);
        intent.putExtra("reader", reader);
        intent.putExtra("amount", amount);
        startActivity(intent);

    }
}