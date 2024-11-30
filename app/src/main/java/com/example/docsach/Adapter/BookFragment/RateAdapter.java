package com.example.docsach.Adapter.BookFragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.R;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ItemViewHolder> {

    private List<DanhGiaResponse> danhGiaResponseList;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<DanhGiaResponse> list) {
        this.danhGiaResponseList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RateAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate, parent, false);
        return new ItemViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RateAdapter.ItemViewHolder holder, int position) {
        DanhGiaResponse danhGiaResponse = danhGiaResponseList.get(position);
        holder.tvName.setText(danhGiaResponse.getReaderRate().getHo() + " " + danhGiaResponse.getReaderRate().getTen());
        holder.tvNoiDung.setText(danhGiaResponse.getNhanXet());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateTime = LocalDateTime.parse(danhGiaResponse.getThoiGianCapNhat());

            LocalDateTime now = LocalDateTime.now();

            Duration duration = Duration.between(dateTime, now);

            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;
            if(days == 0){
                if(hours == 0){
                    holder.tvTime.setText(minutes + " phút trước");
                }else{
                    holder.tvTime.setText(hours + " giờ trước");
                }
            }else{
                holder.tvTime.setText(days + " ngày trước");
            }
        }
        int i;
        for(i = 0; i < danhGiaResponse.getPoint();i++){
            holder.stars[i].setImageResource(R.drawable.ic_start_yellow);
        }
        holder.tvRatePoint.setText(i + " sao");


    }

    public DanhGiaResponse getItem(int pos) {
        return danhGiaResponseList.get(pos);
    }

    @Override
    public int getItemCount() {
        if (danhGiaResponseList != null) {
            return danhGiaResponseList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNoiDung, tvTime, tvName, tvRatePoint;
        private final ImageView [] stars;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvRatePoint = itemView.findViewById(R.id.tvRatePoint);
            stars = new ImageView[]{
                    itemView.findViewById(R.id.imgStar1),
                    itemView.findViewById(R.id.imgStar2),
                    itemView.findViewById(R.id.imgStar3),
                    itemView.findViewById(R.id.imgStar4),
                    itemView.findViewById(R.id.imgStar5)
            };
        }
    }
}
