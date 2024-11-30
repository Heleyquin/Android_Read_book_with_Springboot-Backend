package com.example.docsach.Adapter.BookFragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Model.Ghe;
import com.example.docsach.Model.Phim;
import com.example.docsach.Model.Phong;
import com.example.docsach.Model.Rap;
import com.example.docsach.Model.SuatChieu;
import com.example.docsach.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RapAdapter extends RecyclerView.Adapter<RapAdapter.ItemViewHolder> implements NgayChieuAdapter.ItemInterface {
    private List<Rap> data;
    private List<Phong> dsPhong;
    private List<SuatChieu> listSuatChieu;
    private List<Ghe> dsGhe;
    private List<Phim> dsPhim;
//    private final ItemInterfaceRap itemInterface;
    private NgayChieuAdapter ngayChieuAdapter;
    Context context;
    public RapAdapter(Context context) {
        this.context = context;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Rap> list, List<Phong> dsPhong, List<SuatChieu> listSuatChieu, List<Ghe> dsGhe, List<Phim> dsPhim) {
        this.data = list;
        this.dsPhong = dsPhong;
        this.listSuatChieu = listSuatChieu;
        this.dsGhe = dsGhe;
        this.dsPhim = dsPhim;
        notifyDataSetChanged();
    }
    public Rap getItem(int pos) {
        return data.get(pos);
    }


    @Override
    public void onItemClick(SuatChieu suat, List<Ghe> listGhe, Phim phim) {

        ((NgayChieuAdapter.ItemInterface) context).onItemClick(suat, listGhe, phim);
    }

    public List<SuatChieu> getSuatChieuByRap(int id_rap){
        List<SuatChieu> listSuatChieuOfRap;
        List<Phong> phongOfRap = new ArrayList<>();
        for(Phong p : dsPhong){
            if(p.getId_rap() == id_rap){
                phongOfRap.add(p);
            }
        }
        Set<Integer> phongIds = phongOfRap.stream()
                .map(Phong::getIdPhong)
                .collect(Collectors.toSet());
        listSuatChieuOfRap = listSuatChieu.stream()
                .filter(suat -> phongIds.contains(suat.getId_phong()))
                .collect(Collectors.toList());
        return listSuatChieuOfRap;
    }
    @NonNull
    @Override
    public RapAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_pack, parent, false);
        return new ItemViewHolder(v);
    }

    public void setRVAdapter(RecyclerView rv, NgayChieuAdapter adapter){
//        rv.addItemDecoration(new SpaceItemDecoration(15));
        rv.setLayoutManager(new GridLayoutManager(context, 2));
        rv.setAdapter(adapter);
//        Toast.makeText(context, String.valueOf(getItemCount()), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBindViewHolder(@NonNull RapAdapter.ItemViewHolder holder, int position) {
        Rap rap =data.get(position);
        ngayChieuAdapter = new NgayChieuAdapter(context, this, dsGhe, dsPhim);

        holder.tvTenRap.setText(rap.getTenRap());
        List<SuatChieu> suatChieuOfRap = getSuatChieuByRap(rap.getIdRap());

        setRVAdapter(holder.rvNgayChieu, ngayChieuAdapter);
        ngayChieuAdapter.setData(suatChieuOfRap);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenRap;
        private RecyclerView rvNgayChieu;
        public ItemViewHolder(@NonNull View itemView) {

            super(itemView);

            tvTenRap = itemView.findViewById(R.id.tvPackName);
            rvNgayChieu = itemView.findViewById(R.id.rvNgayChieu);

        }
    }
}
