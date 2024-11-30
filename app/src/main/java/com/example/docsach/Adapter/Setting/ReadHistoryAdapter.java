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
import com.example.docsach.Model.LichSuDoc;
import com.example.docsach.R;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadHistoryAdapter extends RecyclerView.Adapter<ReadHistoryAdapter.ItemViewHolder> {
    private List<LichSuDoc> lichSuDocList;
    private Context context;

    public ReadHistoryAdapter(Context context){
        this.context = context;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<LichSuDoc> lichSuDocList){
        this.lichSuDocList = lichSuDocList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_read_history, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        LichSuDoc lichSuDoc = lichSuDocList.get(position);
        SachApi.sachApi.getImage(lichSuDoc.getSach().getUrlImg()).enqueue(new Callback<ResponseBody>() {
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
        holder.tvDecript.setText(lichSuDoc.getSach().getGioiThieu());
        holder.tvTitle.setText(lichSuDoc.getSach().getTenSach());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateTime = null;
            dateTime = LocalDateTime.parse(lichSuDoc.getThoiGian());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = dateTime.format(formatter);
            holder.tvNgayDoc.setText(formattedDate);
        }
    }


    @Override
    public int getItemCount() {
        if (lichSuDocList != null) {
            return lichSuDocList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvDecript, tvNgayDoc;
        private ImageView ivImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle =  itemView.findViewById(R.id.tvTitle);
            tvDecript = itemView.findViewById(R.id.tvDecript);
            tvNgayDoc = itemView.findViewById(R.id.tvNgayDoc);
            ivImage = itemView.findViewById(R.id.ivImage);

        }
    }
}
