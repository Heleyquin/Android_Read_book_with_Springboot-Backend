package com.example.docsach.Adapter.BookFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Model.Ghe;
import com.example.docsach.Model.Phim;
import com.example.docsach.Model.SuatChieu;
import com.example.docsach.R;

import java.util.ArrayList;
import java.util.List;

public class NgayChieuAdapter extends RecyclerView.Adapter<NgayChieuAdapter.ItemViewHolder> {
    private final ItemInterface itemInterface;

    private List<SuatChieu> data;
    private List<Ghe> listGhe;
    private List<Phim> listPhim;
    private Context context;


    public NgayChieuAdapter(Context context, ItemInterface itemInterface, List<Ghe> listGhe, List<Phim> listPhim) {
        this.itemInterface = itemInterface;
        this.context = context;
        this.listGhe = listGhe;
        this.listPhim = listPhim;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<SuatChieu> list) {
        this.data = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NgayChieuAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_date, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NgayChieuAdapter.ItemViewHolder holder, int position) {
        SuatChieu date =data.get(position);

        holder.tvDate.setText(date.getNgayChieu());
        holder.tvTime.setText(date.getGioBatDau());
        holder.tvSub.setText(date.getSub());
        holder.tvPhong.setText(String.valueOf (date.getId_phong()));
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public SuatChieu getItem(int pos) {
        return data.get(pos);
    }

    public interface ItemInterface {
        void onItemClick(SuatChieu suat, List<Ghe> listGhe, Phim phim);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvDate;
        private final TextView tvTime;
        private final TextView tvSub;
        private final TextView tvPhong;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSub = itemView.findViewById(R.id.tvSub);
            tvPhong = itemView.findViewById(R.id.tvPhong);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){

                        List<Ghe> gheOfPhong = new ArrayList<>();
                        Phim phim = null;
                        for(Ghe ghe:listGhe){
                            if(ghe.getId_phong() == data.get(pos).getId_phong()){
                                gheOfPhong.add(ghe);
                            }
                        }
                        for(Phim p:listPhim){
                            if(p.getIdPhim() == data.get(pos).getId_phim()){
                                phim = p;
                            }
                        }
                        itemInterface.onItemClick(data.get(pos), gheOfPhong, phim);
                    }
                }
            });
        }
    }
}
