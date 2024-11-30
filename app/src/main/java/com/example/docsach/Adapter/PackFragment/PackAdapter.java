package com.example.docsach.Adapter.PackFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Model.CT_Goi;
import com.example.docsach.Model.DTO.GoiDangKyResponse;
import com.example.docsach.Model.DTO.PackInUse;
import com.example.docsach.R;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.ItemViewHolder> {
    private List<GoiDangKyResponse> goiDangKyResponseList;
    private PackInUse packInUse;
    private Context context;
    private ItemInterface itemInterface;
    public PackAdapter(ItemInterface itemInterface, Context context){
        this.itemInterface = itemInterface;
        this.context = context;
    }
    @NonNull
    @Override
    public PackAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_pack, parent, false);
        return new ItemViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PackAdapter.ItemViewHolder holder, int position) {
        GoiDangKyResponse goiDangKyResponse = goiDangKyResponseList.get(position);
        try {
            InputStream inputStream = context.getAssets().open("images_pack.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.ivImage.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
        holder.tvMaGoi.setText(goiDangKyResponse.getMaGoi());
        holder.tvGiaTien.setBackgroundColor(Color.parseColor("#33FFFF"));
        if(packInUse != null){
            if(Objects.equals(packInUse.getMaGoi(), goiDangKyResponse.getMaGoi())){
                OffsetDateTime offsetDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    offsetDateTime = OffsetDateTime.parse(packInUse.getExpirDate());
                    LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration = Duration.between(now, localDateTime);
                    long dayDiff = duration.toDays();
                    long hourDiff = duration.toHours();
                    long minDiff = duration.toMinutes();
                    long secDiff = duration.getSeconds();
                    if(!duration.isNegative()){
                        holder.tvGiaTien.setBackgroundColor(Color.parseColor("#FFFFCC"));
                        if(dayDiff > 0){
                            holder.tvGiaTien.setText("Còn lại: "+ dayDiff + " ngày");
                        }else{
                            if(hourDiff > 0){
                                holder.tvGiaTien.setText("Còn lại: "+ dayDiff + " giờ");
                            }else{
                                if(minDiff > 0 ){
                                    holder.tvGiaTien.setText("Còn lại: "+ minDiff + " phút");
                                }else{
                                    if(secDiff > 0){
                                        holder.tvGiaTien.setText("Đang dùng, sắp hết thời gian");
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                holder.tvGiaTien.setText(formatAmount(goiDangKyResponse.getGiaTien()));
            }
        }else{
            holder.tvGiaTien.setText(formatAmount(goiDangKyResponse.getGiaTien()));
        }
        holder.tvThoiHan.setText(goiDangKyResponse.getThoiHan() + " ngày");
        holder.tvChuThich.setText(goiDangKyResponse.getChuThich());
    }
    private String formatAmount(BigDecimal amo){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);
        return formatter.format(amo) + " VND";
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<GoiDangKyResponse> list){
        this.goiDangKyResponseList = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataPackInUse(PackInUse pack){
        this.packInUse = pack;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (goiDangKyResponseList != null) {
            return goiDangKyResponseList.size();
        }
        return 0;
    }

    public GoiDangKyResponse getItem(int position) {
        return goiDangKyResponseList.get(position);
    }

    public interface ItemInterface {
        void onItemClick(View view, int position);
        void onItemClickBuy(View view , int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvGiaTien, tvThoiHan, tvChuThich, tvMaGoi;
        private final ImageView ivImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChuThich = itemView.findViewById(R.id.tvChuThich);
            tvThoiHan = itemView.findViewById(R.id.tvThoiHan);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTien);
            tvMaGoi = itemView.findViewById(R.id.tvMaGoi);
            ivImage = itemView.findViewById(R.id.ivImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemInterface.onItemClick(v, getAdapterPosition());
                }
            });
            tvGiaTien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onItemClickBuy(view, getAdapterPosition());
                }
            });
        }
    }
}
