package com.example.docsach.Adapter.BookFragment;

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
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.LuotDocSach;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ItemViewHolder> {
    private final ItemInterface itemInterface;
    private List<SuatChieu> dsSuat;

    private List<Sach> sachList, sachFavors;
    private List<CountAllFavor> countAllFavors;
    private List<LuotDocSach> luotDocSaches;
    private List<Sach_Mong_Muon> sachMongMuonList;
    private List<LichSuMua> lichSuMuaList;
    private Reader reader;
    private Context context;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

    public BooksAdapter(ItemInterface itemInterface, Context context) {
        this.itemInterface = itemInterface;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Sach> list) {
        this.sachList = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataCountFavor(List<CountAllFavor> list) {
        this.countAllFavors = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataReadedCount(List<LuotDocSach> list) {
        this.luotDocSaches = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataSachMongMuon(List<Sach_Mong_Muon> list){
        this.sachMongMuonList = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataSachFavors(List<Sach> list){
        this.sachFavors = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataLichSuMua(List<LichSuMua> list){
        this.lichSuMuaList = list;
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setDataReader(Reader reader){
        this.reader = reader;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ItemViewHolder(v);
    }
    public Sach getItem(int pos) {
        return sachList.get(pos);
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
    public List<Sach> getAll(){
        return sachList;
    }
    private String formatAmount(BigDecimal amo){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);
        return formatter.format(amo) + " VND";
    }
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Sach sach = sachList.get(position);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
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
                Toast.makeText(BooksAdapter.this.context, "Error fetch book", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvTitle.setText(sach.getTenSach());
        holder.tvDecript.setText(sach.getGioiThieu());
        holder.tvPrice.setText(formatAmount(sach.getGiaTien()));
        holder.btnLove.setImageResource(R.drawable.ic_dislike_icon);
        holder.btnWhist.setImageResource(R.drawable.ic_unwhist_icon);
        holder.btnWhist.setVisibility(View.VISIBLE);
        for(Sach s : sachFavors){
            if(s.getId() == sach.getId()){
                holder.btnLove.setImageResource(R.drawable.ic_like_icon);
            }
        }
        for(LichSuMua l : lichSuMuaList){
            if(l.getReaderBuy().getId() == reader.getId() && l.getSachBuy().getId() == sach.getId()){
                holder.btnWhist.setVisibility(View.GONE);
            }else{
                for(Sach_Mong_Muon s : sachMongMuonList){
                    if(s.getSachWish().getId() == sach.getId()){
                        holder.btnWhist.setImageResource(R.drawable.ic_whist_icon);
                    }
                }
            }
        }
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

    @Override
    public int getItemCount() {
        if (sachList != null) {
            return sachList.size();
        }
        return 0;
    }
    public interface ItemInterface {
        void onItemClick(View view, int position);
        void onItemLove(View view, int position);
        void onItemWhist(View view, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivImage;
        private final TextView tvTitle, tvDecript, tvReadCount, tvFavorCount, tvPrice, tvAvgPointOfRate;
        private final ImageButton btnLove, btnWhist;
        private final ImageView[] stars;

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemInterface.onItemClick(v, getAdapterPosition());
                }
            });
            this.btnLove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onItemLove(view, getAdapterPosition());
                }
            });
            this.btnWhist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemInterface.onItemWhist(view, getAdapterPosition());
                }
            });
        }
    }
}

