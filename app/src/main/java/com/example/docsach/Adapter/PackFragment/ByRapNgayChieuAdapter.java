package com.example.docsach.Adapter.PackFragment;

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

public class ByRapNgayChieuAdapter extends RecyclerView.Adapter<ByRapNgayChieuAdapter.ItemViewHolder> {
    private final ItemInterface itemInterface;

    private List<SuatChieu> data;
    private List<Ghe> listGhe;
    private Phim phim;
    private Context context;

    public ByRapNgayChieuAdapter(ItemInterface itemInterface, Context context) {
        this.itemInterface = itemInterface;
        this.context = context;
    }
    public int getSize(){
        return listGhe.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<SuatChieu> list, List<Ghe> listGhe, Phim phim){
        this.data = list;
        this.listGhe = listGhe;
        this.phim = phim;
        notifyDataSetChanged();
    }
    public interface ItemInterface {
        void onItemClick(SuatChieu suat, List<Ghe> listGhe, Phim phim);
    }
    @NonNull
    @Override
    public ByRapNgayChieuAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_date, parent, false);
        return new ItemViewHolder(v);
    }

    public SuatChieu getItem(int pos) {
        return data.get(pos);
    }
    @Override
    public void onBindViewHolder(@NonNull ByRapNgayChieuAdapter.ItemViewHolder holder, int position) {
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {
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
                    if (pos != RecyclerView.NO_POSITION) {
                        List<Ghe> gheOfPhong = new ArrayList<>();
                        for (Ghe ghe : listGhe) {
                            if (ghe.getId_phong() == data.get(getAdapterPosition()).getId_phong()) {
                                gheOfPhong.add(ghe);
                            }
                        }
                        itemInterface.onItemClick(data.get(getAdapterPosition()), gheOfPhong, phim);
                    }
                }
            });
        }
    }
}
