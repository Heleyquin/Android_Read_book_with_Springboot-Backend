package com.example.docsach.Adapter.BookFragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Model.Ghe;
import com.example.docsach.Model.HoaDon;
import com.example.docsach.Model.SuatChieu;
import com.example.docsach.Model.Ve;
import com.example.docsach.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;


public class GheNgoiAdapter extends RecyclerView.Adapter<GheNgoiAdapter.ItemViewHolder> {
    List<Ghe> listGhe;
    List<Ve> listVe;
    List<HoaDon> listHoaDon;
    Map<Ghe, Integer> gheStatusMap;//0 chưa chon; 1// Đang cn; 2//Khong duoc chon
    private ItemInterface itemInterface;
    private int size;
    private SuatChieu suat;

    public GheNgoiAdapter(ItemInterface itemInterface) {

        this.itemInterface = itemInterface;
        size = 0;
    }

    private void gheStatus() {
        gheStatusMap = new HashMap<>();
        for (Ghe ghe : listGhe) {
            gheStatusMap.put(ghe, 0);
        }
    }

    public void setSize(){
        this.size = 0;
    }
    @NonNull
    @Override
    public GheNgoiAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_seat, parent, false);
        return new ItemViewHolder(v);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Ghe> list, List<Ve> listVe, List<HoaDon> listHoaDon, SuatChieu suat) {
        this.listGhe = list;
        this.listVe = listVe;
        this.listHoaDon = listHoaDon;
        this.suat = suat;
        gheStatus();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull GheNgoiAdapter.ItemViewHolder holder, int position) {
        Ghe ghe = listGhe.get(position);
        List<Ve> veOfSuat = new ArrayList<>();
        for(Ve ve:listVe){
            if(ve.getIdSuatChieu() == suat.getIdSuatChieu()){
                veOfSuat.add(ve);
            }
        }
        Set<Integer> temp = veOfSuat.stream()
                .map(Ve::getId_ghe)
                .collect(Collectors.toSet());
        if (temp.contains(ghe.getIdGhe())) {
            holder.ivGhe.setBackgroundResource(R.drawable.baseline_event_seat_24_sold);
            holder.ivGhe.setEnabled(false);
            gheStatusMap.put(ghe, 2);
//            Log.e("HashMap",gheStatusMap.toString());
        } else {
            holder.ivGhe.setBackgroundResource(R.drawable.baseline_event_seat_24_none);
//            holder.ivGhe.setTag(R.drawable.baseline_event_seat_24_none);
        }
        holder.tvGhe.setText(String.valueOf(ghe.getIdGhe()));

    }

    public Ghe getItem(int pos) {
        return listGhe.get(pos);
    }

    @Override
    public int getItemCount() {
        if (listGhe != null) {
            return listGhe.size();
        }
        return 0;
    }

    public interface ItemInterface {
        void onItemClick(int id_Ghe, int status);
    }

    public void taoVe() {
        listVe = new ArrayList<>();
        listVe.add(new Ve(1, 1, 1, 1));
        listVe.add(new Ve(2, 2, 1, 1));
        listVe.add(new Ve(3, 13, 1, 2));
        listVe.add(new Ve(4, 4, 1, 3));
        listVe.add(new Ve(5, 10, 1, 4));
    }

    public void taoHoaDon() {
        listHoaDon = new ArrayList<>();
        listHoaDon.add(new HoaDon(1, 1));
        listHoaDon.add(new HoaDon(2, 2));
        listHoaDon.add(new HoaDon(3, 1));
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvGhe;
        private final ImageView ivGhe;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGhe = itemView.findViewById(R.id.tvGhe);
            ivGhe = itemView.findViewById(R.id.ivGhe);

            ivGhe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ghe ghe = listGhe.get(getAdapterPosition());
                    if(size < 4){
                        if (gheStatusMap.get(ghe) == 0 ) {
                            ivGhe.setBackgroundResource(R.drawable.baseline_event_seat_24_selected);
                            gheStatusMap.put(ghe, 1);
                            itemInterface.onItemClick(Integer.parseInt(tvGhe.getText().toString()), 1);
                            size += 1;
                        } else if (gheStatusMap.get(ghe) == 1) {
                            ivGhe.setBackgroundResource(R.drawable.baseline_event_seat_24_none);
                            gheStatusMap.put(ghe, 0);
                            itemInterface.onItemClick(Integer.parseInt(tvGhe.getText().toString()), 0);
                            size -= 1;
                        }else{
                            itemInterface.onItemClick(Integer.parseInt(tvGhe.getText().toString()), -1);
                        }
                    }else{
                        if(gheStatusMap.get(ghe) == 1){
                            ivGhe.setBackgroundResource(R.drawable.baseline_event_seat_24_none);
                            gheStatusMap.put(ghe, 0);
                            itemInterface.onItemClick(Integer.parseInt(tvGhe.getText().toString()), 0);
                            size -= 1;
                        }

                    }

                }
            });


        }
    }
}
