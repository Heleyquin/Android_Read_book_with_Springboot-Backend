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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.API.SachApi;
import com.example.docsach.Adapter.BookFragment.BooksAdapter;
import com.example.docsach.Model.DTO.CT_DangKyResponse;
import com.example.docsach.Model.HoaDon;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.R;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyHistoryAdapter extends RecyclerView.Adapter<BuyHistoryAdapter.ItemViewHolder> {
    private List<LichSuMua> lichSuMuaList;
    private Context context;
    public BuyHistoryAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<LichSuMua> lichSuMuaList){
        this.lichSuMuaList = lichSuMuaList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BuyHistoryAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_buy, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyHistoryAdapter.ItemViewHolder holder, int position) {
        LichSuMua lichSuMua = lichSuMuaList.get(position);

        SachApi.sachApi.getImage(lichSuMua.getSachBuy().getUrlImg()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap != null) {
                        holder.ivImage.setImageBitmap(bitmap);
                    } else {
                        Log.e("Image", "Failed to decode image");
                    }
                } else {
                    Log.e("Image", "Response error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Error fetch book", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvTitle.setText(lichSuMua.getSachBuy().getTenSach());
        holder.tvDecript.setText(lichSuMua.getSachBuy().getGioiThieu());
        holder.tvPrice.setText(formatAmount(lichSuMua.getGiaTien()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateTime = null;
            dateTime = LocalDateTime.parse(lichSuMua.getThoiGianMua());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = dateTime.format(formatter);
            holder.tvNgayMua.setText(formattedDate);
        }
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
        if (lichSuMuaList != null) {
            return lichSuMuaList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvNgayMua, tvPrice, tvDecript;
        private ImageView ivImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDecript = itemView.findViewById(R.id.tvDecript);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvNgayMua = itemView.findViewById(R.id.tvNgayMua);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivImage = itemView.findViewById(R.id.ivImage);


        }
    }
}
