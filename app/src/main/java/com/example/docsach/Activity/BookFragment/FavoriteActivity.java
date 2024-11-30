package com.example.docsach.Activity.BookFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.API.ReaderApi;
import com.example.docsach.Adapter.BookFragment.FavoriteAdapter;
import com.example.docsach.Model.DTO.ReaderUpdate;
import com.example.docsach.Model.Key.Key_CT_Goi_Dang_Ky;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.ItemInterface {

    private Button btnClear;
    private RecyclerView rvFavor;
    private FavoriteAdapter adapter;

    private List<Sach> favorList;
    private Reader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite);

        adapter = new FavoriteAdapter(this, getApplicationContext());

        getDataIntent();
        setControl();
        setRV();

        adapter.setData(favorList);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    favorList.clear();
                }
                Set<Long> listFavorId = new HashSet<Long>();
                for (Sach s : favorList){
                    listFavorId.add(s.getId());
                }
                Set<Long> lichSuDocId = new HashSet<Long>();
                Set<Key_CT_Goi_Dang_Ky>  key_CT_Goi_Dang_Ky = new HashSet<Key_CT_Goi_Dang_Ky>();
                Set<Long>  lichSuMuaId = new HashSet<Long>();
                Set<Long>  sachMongMuonId = new HashSet<Long>();
                ReaderUpdate readerUpdate = new ReaderUpdate(reader.getId(), reader.getTen(), reader.getHo(), reader.isGioiTinh(), reader.getNgayTao(), reader.getEmail(), lichSuDocId, listFavorId, key_CT_Goi_Dang_Ky, reader.getIdAccount(), lichSuMuaId, sachMongMuonId);
                ReaderApi.readerApi.updateReader(readerUpdate).enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });


    }
    public void getDataIntent(){
        Intent intent = getIntent();
        favorList = (List<Sach>) intent.getSerializableExtra("favorList");
        reader = (Reader) intent.getSerializableExtra("reader");
    }
    public void setControl(){
        rvFavor = findViewById(R.id.rvFavor);
        btnClear = findViewById(R.id.btnEmpty);

        if(favorList.isEmpty()){
            btnClear.setVisibility(View.GONE);
        }else{
            btnClear.setVisibility(View.VISIBLE);
        }
    }
    public void setRV(){
        rvFavor.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvFavor.setAdapter(adapter);
    }

    @Override
    public void onRemoveClick(View view, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(favorList.contains(adapter.getItem(position))){
                favorList.remove(adapter.getItem(position));
            }
        }
        Set<Long> listFavorId = new HashSet<Long>();
        for (Sach s : favorList){
            listFavorId.add(s.getId());
        }
        Set<Long> lichSuDocId = new HashSet<Long>();
        Set<Key_CT_Goi_Dang_Ky>  key_CT_Goi_Dang_Ky = new HashSet<Key_CT_Goi_Dang_Ky>();
        Set<Long>  lichSuMuaId = new HashSet<Long>();
        Set<Long>  sachMongMuonId = new HashSet<Long>();
        ReaderUpdate readerUpdate = new ReaderUpdate(reader.getId(), reader.getTen(), reader.getHo(), reader.isGioiTinh(), reader.getNgayTao(), reader.getEmail(), lichSuDocId, listFavorId, key_CT_Goi_Dang_Ky, reader.getIdAccount(), lichSuMuaId, sachMongMuonId);
        ReaderApi.readerApi.updateReader(readerUpdate).enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if(!favorList.isEmpty()){
                        btnClear.setVisibility(View.VISIBLE);
                    }else{
                        btnClear.setVisibility(View.GONE);
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