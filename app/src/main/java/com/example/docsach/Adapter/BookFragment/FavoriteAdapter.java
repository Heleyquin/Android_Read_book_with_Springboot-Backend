package com.example.docsach.Adapter.BookFragment;

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
import com.example.docsach.Model.Sach;
import com.example.docsach.R;

import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ItemViewHolder> {
    private final ItemInterface itemInterface;
    private List<Sach> favorList;
    private Context context;
    public FavoriteAdapter(ItemInterface itemInterface, Context context){
        this.itemInterface = itemInterface;
        this.context = context;
    }
    @NonNull
    @Override
    public FavoriteAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        return new ItemViewHolder(v);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Sach> favorList){
        this.favorList = favorList;
        notifyDataSetChanged();
    }
    public Sach getItem(int position){
        return favorList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ItemViewHolder holder, int position) {
        Sach sach = favorList.get(position);
        holder.tvTitle.setText(sach.getGioiThieu());
        SachApi.sachApi.getImage(sach.getUrlImg()).enqueue(new Callback<ResponseBody>() {
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
    }

    @Override
    public int getItemCount() {
        if (favorList != null) {
            return favorList.size();
        }
        return 0;
    }
    public interface ItemInterface {
        void onRemoveClick(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, btnRemoveFavor;
        private ImageView ivImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnRemoveFavor = itemView.findViewById(R.id.btnRemoveFavor);
            ivImage = itemView.findViewById(R.id.ivImage);

            btnRemoveFavor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onRemoveClick(view, getAdapterPosition());
                }
            });
        }
    }
}
