package com.example.docsach.Adapter.PackFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.API.SachApi;
import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.Model.Ghe;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.LuotDocSach;
import com.example.docsach.Model.Phim;
import com.example.docsach.Model.Phong;
import com.example.docsach.Model.Rap;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.Model.SuatChieu;
import com.example.docsach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookByPackAdapter extends RecyclerView.Adapter<BookByPackAdapter.ItemViewHolder> {
    private final ItemInterface itemInterface;

    private List<Sach> sachList, sachFavors;
    private List<CountAllFavor> countAllFavors;
    private List<LuotDocSach> luotDocSaches;

    private Context context;

    public BookByPackAdapter(ItemInterface itemInterface, Context context) {
        this.itemInterface = itemInterface;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Sach> list, List<CountAllFavor> countAllFavors,List<LuotDocSach> luotDocSaches, List<Sach> sachFavors) {
        this.sachList = list;
        this.countAllFavors = countAllFavors;
        this.luotDocSaches = luotDocSaches;
        this.sachFavors = sachFavors;
        notifyDataSetChanged();
    }
    public interface ItemInterface {
        void onItemClick(View view, int position);
    }
    public Sach getItem(int pos) {
        return sachList.get(pos);
    }
    public List<Sach> getAll(){
        return sachList;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ItemViewHolder(v);
    }

    public CountAllFavor getCountFavorItem(Sach sach){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            CountAllFavor bookWithFavorCount = countAllFavors.stream()
                    .filter(item -> item.getId() == sach.getId())
                    .findFirst()
                    .orElse(null);
            if (bookWithFavorCount != null){
                return bookWithFavorCount;
            }
        }
        return null;
    }
    public LuotDocSach getReadedCountItem(Sach sach){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            LuotDocSach luotDocSach = luotDocSaches.stream()
                    .filter(item -> item.getId() == sach.getId())
                    .findFirst()
                    .orElse(null);
            if (luotDocSach != null){
                return luotDocSach;
            }
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Sach sach = sachList.get(position);
        CountAllFavor bookWithFavorCount = countAllFavors.stream()
                .filter(item -> item.getId() == sach.getId())
                .findFirst()
                .orElse(null);
        if (bookWithFavorCount != null){
            holder.tvFavorCount.setText(bookWithFavorCount.getFavor_count().toString());
        }
        LuotDocSach luotDocSach = luotDocSaches.stream()
                .filter(item -> item.getId() == sach.getId())
                .findFirst()
                .orElse(null);
        if(luotDocSach != null){
            holder.tvReadCount.setText(luotDocSach.getSo_luot_doc().toString());
        }
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
                Toast.makeText(BookByPackAdapter.this.context, "Error fetch book", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvTitle.setText(sach.getTenSach());
        holder.tvDecript.setText(sach.getGioiThieu());
        holder.tvPrice.setText(formatAmount(sach.getGiaTien()));
        holder.btnLove.setImageResource(R.drawable.ic_dislike_icon);
        holder.btnWhist.setImageResource(R.drawable.ic_unwhist_icon);
        if(sachFavors.contains(sach)){
            holder.btnLove.setImageResource(R.drawable.ic_like_icon);
        }
        holder.btnLove.setVisibility(View.GONE);
        holder.btnWhist.setVisibility(View.GONE);

        SachApi.sachApi.getRateById(sach.getId()).enqueue(new Callback<List<DanhGiaResponse>>() {
            @Override
            public void onResponse(Call<List<DanhGiaResponse>> call, Response<List<DanhGiaResponse>> response) {
                assert response.body() != null;
                if(!response.body().isEmpty()){
                    int totalPoint = 0;
                    for(DanhGiaResponse d:response.body()){
                        totalPoint += d.getPoint();
                    }
                    double avgPoint = (double) totalPoint /response.body().size();
                    avgPoint = Math.round(avgPoint * 100.0)/100.0;
                    holder.tvAvgPointOfRate.setText(avgPoint + "/5 Sao");
                    int i;
                    int fullStar = (int) avgPoint;
                    boolean hasHalfStar = avgPoint - fullStar > 0.0;
                    for(i = 0; i < fullStar; i++){
                        holder.stars[i].setImageResource(R.drawable.ic_start_yellow);
                    }
                    if(hasHalfStar){
                        holder.stars[i].setImageResource(R.drawable.baseline_star_half_24);
                    }
                }else{
                    for(int i = 0; i < 5; i++){
                        holder.stars[i].setImageResource(R.drawable.ic_start_yellow);
                    }
                    holder.tvAvgPointOfRate.setText("/5 sao");
                }
            }

            @Override
            public void onFailure(Call<List<DanhGiaResponse>> call, Throwable t) {

            }
        });
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
        if (sachList != null) {
            return sachList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvTitle, tvDecript, tvReadCount, tvFavorCount, tvPrice, tvAvgPointOfRate;
        private final ImageButton btnLove, btnWhist;
        private final ImageView[] stars;
//        private final Button btnDatVe;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ivImage = itemView.findViewById(R.id.ivImage);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvDecript = itemView.findViewById(R.id.tvDecript);
            this.tvReadCount = itemView.findViewById(R.id.tvReadCount);
            this.tvFavorCount = itemView.findViewById(R.id.tvFavorCount);
            this.tvPrice = itemView.findViewById(R.id.tvPrice);
            this.btnLove = itemView.findViewById(R.id.btnLove);
            this.btnWhist = itemView.findViewById(R.id.btnWhist);
            this.stars = new ImageView[]{
                    itemView.findViewById(R.id.imgStar1),
                    itemView.findViewById(R.id.imgStar2),
                    itemView.findViewById(R.id.imgStar3),
                    itemView.findViewById(R.id.imgStar4),
                    itemView.findViewById(R.id.imgStar5)
            };
            this.tvAvgPointOfRate = itemView.findViewById(R.id.tvAvgPointOfRate);

//            this.btnDatVe = itemView.findViewById(R.id.btnDatVe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemInterface.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }
}
