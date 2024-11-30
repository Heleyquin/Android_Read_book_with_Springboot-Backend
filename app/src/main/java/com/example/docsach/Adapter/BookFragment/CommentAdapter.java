package com.example.docsach.Adapter.BookFragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Model.DTO.CmtResponse;
import com.example.docsach.R;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemViewHolder> {

    private List<CmtResponse> cmtResponseList;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<CmtResponse> list) {
        this.cmtResponseList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ItemViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CmtResponse cmtResponse = cmtResponseList.get(position);
        holder.tvNoiDung.setText(cmtResponse.getNoiDung());
        holder.tvName.setText(cmtResponse.getReaderCmt().getHo() + " " + cmtResponse.getReaderCmt().getTen());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateTime = LocalDateTime.parse(cmtResponse.getId().getThoiGianTao());

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
    }

    public CmtResponse getItem(int pos) {
        return cmtResponseList.get(pos);
    }

    @Override
    public int getItemCount() {
        if (cmtResponseList != null) {
            return cmtResponseList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvTime;
        private final TextView tvNoiDung;
        private final TextView tvName;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            this.tvTime = itemView.findViewById(R.id.tvTime);
            this.tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
