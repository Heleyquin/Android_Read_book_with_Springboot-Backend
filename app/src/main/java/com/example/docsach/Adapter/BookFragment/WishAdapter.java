package com.example.docsach.Adapter.BookFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.API.SachApi;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ItemViewHolder> {
    private List<Sach_Mong_Muon> sachMongMuonList;
    private final ItemInterface itemInterface;
    private Context context;
    private List<Integer> listSelect = new ArrayList<Integer>();

    public WishAdapter(ItemInterface itemInterface, Context context) {
        this.itemInterface = itemInterface;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Sach_Mong_Muon> sachMongMuonList){
        this.sachMongMuonList = sachMongMuonList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public WishAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish, parent, false);
        return new ItemViewHolder(v);
    }
    public Sach_Mong_Muon getItem(int position){
        return sachMongMuonList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull WishAdapter.ItemViewHolder holder, int position) {
        Sach_Mong_Muon sachMongMuon = sachMongMuonList.get(position);
        SachApi.sachApi.getImage(sachMongMuon.getSachWish().getUrlImg()).enqueue(new Callback<ResponseBody>() {
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
        holder.tvTitle.setText(sachMongMuon.getSachWish().getTenSach());
        if(listSelect.isEmpty()){
            holder.layout.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        if (sachMongMuonList != null) {
            return sachMongMuonList.size();
        }
        return 0;
    }
    public interface ItemInterface {
        void onClick(View view, int position);
        void onRemoveClick(View view, int position);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void clearSelected(){
        listSelect.clear();

        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, btnRemoveWish;
        private ImageView ivImage;
        private LinearLayout layout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnRemoveWish = itemView.findViewById(R.id.btnRemoveWish);
            ivImage = itemView.findViewById(R.id.ivImage);
            layout = itemView.findViewById(R.id.layout);

            btnRemoveWish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onRemoveClick(view, getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onClick(view, getAdapterPosition());
                    if(listSelect.contains(getAdapterPosition())){
                        listSelect.remove(Integer.valueOf(getAdapterPosition()));
                        layout.setBackgroundColor(Color.WHITE);
                    }
                    else{
                        listSelect.add(getAdapterPosition());
                        layout.setBackgroundColor(Color.parseColor("#66B2FF"));
                    }
                }
            });
        }
    }
}
