package com.example.docsach.Adapter.Setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Model.DTO.CT_DangKyResponse;
import com.example.docsach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PackHistoryAdapter extends RecyclerView.Adapter<PackHistoryAdapter.ItemViewHolder> {

    private List<CT_DangKyResponse> ctDangKyResponseList;
    private Context context;
    public PackHistoryAdapter(Context context){
        this.context = context;
    }
    public void setData(List<CT_DangKyResponse> ctDangKyResponseList){
        this.ctDangKyResponseList = ctDangKyResponseList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PackHistoryAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pack_purchased, parent, false);
        return new ItemViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PackHistoryAdapter.ItemViewHolder holder, int position) {
        CT_DangKyResponse ctDangKyResponse = ctDangKyResponseList.get(position);
        holder.tvMaGoi.setText(ctDangKyResponse.getId().getMaGoi());
        holder.tvChuKy.setText(ctDangKyResponse.getGoiDangKy().getThoiHan() + " ngày");
        holder.tvPrice.setText(formatAmount(ctDangKyResponse.getGiaTien()));
        holder.tvDecript.setText(ctDangKyResponse.getGoiDangKy().getChuThich());
        LocalDateTime dateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.parse(ctDangKyResponse.getId().getThoiGianDangKy());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = dateTime.format(formatter);
            holder.tvNgayMua.setText(formattedDate);
        }
        try {
            InputStream inputStream = context.getAssets().open("images_pack.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.ivImage.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
    private String formatAmount(BigDecimal amo){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);
        return formatter.format(amo) + " VND";
    }

    @Override
    public int getItemCount() {
        if (ctDangKyResponseList != null) {
            return ctDangKyResponseList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMaGoi, tvDecript, tvChuKy, tvPrice, tvNgayMua;
        private ImageView ivImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChuKy = itemView.findViewById(R.id.tvChuKy);
            tvDecript = itemView.findViewById(R.id.tvDecript);
            tvMaGoi = itemView.findViewById(R.id.tvMaGoi);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvNgayMua = itemView.findViewById(R.id.tvNgayMua);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
