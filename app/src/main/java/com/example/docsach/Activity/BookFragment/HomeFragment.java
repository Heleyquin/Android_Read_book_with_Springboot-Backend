package com.example.docsach.Activity.BookFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.UserCanceledException;
import com.example.docsach.API.ReaderApi;
import com.example.docsach.API.SachApi;
import com.example.docsach.Activity.ForAll.MainActivity;
import com.example.docsach.Adapter.BookFragment.BooksAdapter;
import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.DTO.LichSuNapRequest;
import com.example.docsach.Model.DTO.ReaderUpdate;
import com.example.docsach.Model.DTO.SachMongMuonRequest;
import com.example.docsach.Model.Key.Key_CT_Goi_Dang_Ky;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.LuotDocSach;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements BooksAdapter.ItemInterface, DropInListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvMainDisplay;
    private BooksAdapter adapter;
    private TextView tvAmount;
    private ImageButton btnLoveList, btnWishList, btnPayment;

    private SearchView svSearch;
    private ImageView ivImgMain;
    private List<Sach> sachList, sachListFil, sachFavors;
    private List<CountAllFavor> countAllFavors;
    private List<LuotDocSach> luotDocSachs;
    private List<Sach_Mong_Muon> sachMongMuonList;
    private List<LichSuMua> lichSuMuaList;
    private Reader reader;
    BigDecimal amount;
//    private List<DanhGiaResponse> danhGiaResponseList;
    private DropInClient dropInClient;
    private double money = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchBooks();
        fetchUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new BooksAdapter(this, getContext());
        return inflater.inflate(R.layout.fragment_book, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropInClient = new DropInClient(this, "sandbox_38zzbsr7_xm66fpf4j65dkgwc");
        dropInClient.setListener(this);

        svSearch = view.findViewById(R.id.SearchBar);
        rvMainDisplay = view.findViewById(R.id.rvMainDisplay);
        ivImgMain = view.findViewById(R.id.ivImgMain);
        tvAmount = view.findViewById(R.id.tvAmount);
        btnLoveList = view.findViewById(R.id.btnLoveList);
        btnWishList = view.findViewById(R.id.btnWishList);
        btnPayment = view.findViewById(R.id.btnPayment);

        setData();
        fetchUser();
        fetchBooks();

        try {
            InputStream inputStream = this.requireContext().getAssets().open("img.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ivImgMain.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        };

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
            @SuppressLint("NotifyDataSetChanged")
            private void filter(String s) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String searchFormat = removeDiacritics(s.toLowerCase().strip());
                    if(searchFormat.isEmpty()){
                        sachList.clear();
                        sachList.addAll(sachListFil);
                        adapter.notifyDataSetChanged();

                    }
                    else{
                        sachList.clear();
                        sachList.addAll(sachListFil.stream()
                                .filter(sach -> removeDiacritics(sach.getTenSach().strip().toLowerCase()).contains(searchFormat))
                                .collect(Collectors.toList()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        btnWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sachMongMuonList != null){
                    Intent intent = new Intent(view.getContext(), WishActivity.class);

                    intent.putExtra("reader", reader);
                    intent.putExtra("wish", (Serializable) sachMongMuonList);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
                }

            }
        });

        btnLoveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FavoriteActivity.class);

                List<Sach> favorList = new ArrayList<>(reader.getSachs());
                intent.putExtra("reader", reader);
                intent.putExtra("favorList", (Serializable) favorList);
                startActivity(intent);
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_input_money, null);

                TextView tvError;
                EditText edtThanhToan;
                Button btnNap;

                edtThanhToan = dialogView.findViewById(R.id.edtThanhToan);
                btnNap = dialogView.findViewById(R.id.btnNap);
                tvError = dialogView.findViewById(R.id.tvError);
                btnNap.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);

                edtThanhToan.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(charSequence != null){
                            if (!charSequence.toString().matches("[0-9]+")) {
                                // Nếu không phải số, thì bạn có thể thông báo lỗi hoặc xóa ký tự nhập vào
                                tvError.setError("Số tiền không hợp lệ, vui lòng nhập lại");
                                tvError.setVisibility(View.VISIBLE);
                                btnNap.setVisibility(View.GONE);
                            } else {
                                tvError.setVisibility(View.GONE);
                                btnNap.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(!editable.toString().isEmpty()){
                            money = Double.parseDouble(editable.toString());
                        }
                    }
                });
                builder.setView(dialogView);

                AlertDialog alertDialog = builder.create();

                btnNap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(money >= 10000){
                            alertDialog.dismiss();
                            launchDropIn();
                        }
                    }
                });

                alertDialog.show();
            }
        });

    }
    private void launchDropIn() {

        DropInRequest dropInRequest = new DropInRequest(false);
        dropInClient.launchDropIn(dropInRequest);
    }

    private String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
    private void setData() {
        rvMainDisplay.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMainDisplay.setAdapter(adapter);
    }
    private String formatAmount(BigDecimal amo){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);
        return formatter.format(amo) + " VND";
    }
    private void getAmount(Long id){
        ReaderApi.readerApi.getAmountById(id).enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().trim();
                    String formated = result.replace(",", ".");
                    Double am = Double.parseDouble(formated);
                    amount = BigDecimal.valueOf(am);
                    tvAmount.setText("Số dư: " + formatAmount(amount));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                amount = null;
                Log.i("amount", "failed");
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(view.getContext(), Book_Detail.class);

        intent.putExtra("sach", adapter.getItem(position));
        intent.putExtra("sachList",(Serializable) adapter.getAll());
        intent.putExtra("favorCount", adapter.getCountFavorItem(adapter.getItem(position)));
        intent.putExtra("readedCount", adapter.getReadedCountItem(adapter.getItem(position)));
        intent.putExtra("sachFavors",(Serializable) sachFavors);
        intent.putExtra("sachMongMuonList",(Serializable) sachMongMuonList);
        intent.putExtra("lichSuMuaList",(Serializable) lichSuMuaList);
        intent.putExtra("reader", reader);
        intent.putExtra("amount", amount);
        startActivity(intent);
    }

    @Override
    public void onItemLove(View view, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(sachFavors.contains(adapter.getItem(position))){
                sachFavors.remove(adapter.getItem(position));
            }
            else{
                sachFavors.add(adapter.getItem(position));
            }
        }
        Set<Long> listFavorId = new HashSet<Long>();
        for (Sach s : sachFavors){
            listFavorId.add(s.getId());
        }
        Set<Long> lichSuDocId = new HashSet<Long>();
        Set<Key_CT_Goi_Dang_Ky>  key_CT_Goi_Dang_Ky = new HashSet<Key_CT_Goi_Dang_Ky>();
        Set<Long>  lichSuMuaId = new HashSet<Long>();
        Set<Long>  sachMongMuonId = new HashSet<Long>();
        ReaderUpdate readerUpdate = new ReaderUpdate(reader.getId(), reader.getTen(), reader.getHo(), reader.isGioiTinh(), reader.getNgayTao(), reader.getEmail(), lichSuDocId, listFavorId, key_CT_Goi_Dang_Ky, reader.getIdAccount(), lichSuMuaId, sachMongMuonId);
        ReaderApi.readerApi.updateReader(readerUpdate).enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemWhist(View view, int position) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Sach_Mong_Muon sachMongMuon = sachMongMuonList.stream()
                    .filter(item -> item.getSachWish().getId() == adapter.getItem(position).getId() && item.getReaderWish().getId() == reader.getId())
                    .findFirst()
                    .orElse(null);
            if(sachMongMuon != null){
                Long id = sachMongMuon.getId();
                sachMongMuonList.remove(sachMongMuon);
                ReaderApi.readerApi.deleteWishItem(id).enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
            else{
                SachMongMuonRequest sachMongMuonRequest = new SachMongMuonRequest(reader, adapter.getItem(position));
                ReaderApi.readerApi.addWishItem(sachMongMuonRequest).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            ReaderApi.readerApi.getAllWhistById(reader.getId()).enqueue(new Callback<List<Sach_Mong_Muon>>() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onResponse(Call<List<Sach_Mong_Muon>> call, Response<List<Sach_Mong_Muon>> response) {
                                    sachMongMuonList.clear();
                                    assert response.body() != null;
                                    sachMongMuonList.addAll(response.body());
                                    adapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onFailure(Call<List<Sach_Mong_Muon>> call, Throwable t) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        }

    }

    public void fetchUser(){
        ReaderApi.readerApi.getReaderByUsername("user3").enqueue(new Callback<Reader>() {
            @Override
            public void onResponse(Call<Reader> call, Response<Reader> response) {
                assert response.body() != null;
                reader = response.body();
                sachFavors = new ArrayList<>(response.body().getSachs());
                adapter.setDataSachFavors(sachFavors);
                adapter.setDataReader(reader);
                Long readerId = response.body().getId();
                getAmount(readerId);
                ReaderApi.readerApi.getAllWhistById(readerId).enqueue(new Callback<List<Sach_Mong_Muon>>() {
                    @Override
                    public void onResponse(Call<List<Sach_Mong_Muon>> call, Response<List<Sach_Mong_Muon>> response) {
                        assert response.body() != null;
                        sachMongMuonList = new ArrayList<>(response.body());
                        adapter.setDataSachMongMuon(sachMongMuonList);
//                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                        String jsonWhist = gson.toJson(sachMongMuonList);
//                        Log.i("người dùng", jsonWhist);

                    }

                    @Override
                    public void onFailure(Call<List<Sach_Mong_Muon>> call, Throwable t) {

                    }
                });
                ReaderApi.readerApi.getLichSuMuaById(readerId).enqueue(new Callback<List<LichSuMua>>() {
                    @Override
                    public void onResponse(Call<List<LichSuMua>> call, Response<List<LichSuMua>> response) {
                        assert response.body() != null;
                        lichSuMuaList = new ArrayList<>(response.body());
                        adapter.setDataLichSuMua(lichSuMuaList);
//                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                        String jsonWhist = gson.toJson(lichSuMuaList);
//                        Log.i("mua", jsonWhist);

                    }

                    @Override
                    public void onFailure(Call<List<LichSuMua>> call, Throwable t) {

                    }
                });
//                QuanLy quanLy = sachFavors.get(0).getIdQuanLy();
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                String jsonQL = gson.toJson(quanLy);
//                String jsonFavor = gson.toJson(sachFavors);
//                Log.i("quản lý", jsonQL);
//                Log.i("người dùng", jsonFavor);

            }

            @Override
            public void onFailure(Call<Reader> call, Throwable t) {

            }
        });
    }

    public void fetchBooks(){
        SachApi.sachApi.allBooks().enqueue(new Callback<List<Sach>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sach>> call, Response<List<Sach>> response) {
                assert response.body() != null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    sachList = new ArrayList<Sach>(response.body()).stream()
                            .filter(item -> item.isActive())
                            .collect(Collectors.toList());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    sachListFil = new ArrayList<Sach>(response.body()).stream()
                            .filter(item -> item.isActive())
                            .collect(Collectors.toList());
                } ;
                if(sachList != null){
                    adapter.setData(sachList);
                }

            }

            @Override
            public void onFailure(Call<List<Sach>> call, Throwable t) {
                Toast.makeText(HomeFragment.super.getContext(), "Error fetch book", Toast.LENGTH_SHORT).show();
            }
        });
        SachApi.sachApi.bookWithCountFavor().enqueue(new Callback<List<CountAllFavor>>() {
            @Override
            public void onResponse(Call<List<CountAllFavor>> call, Response<List<CountAllFavor>> response) {
                assert response.body() != null;
                countAllFavors = new ArrayList<CountAllFavor>(response.body());
                adapter.setDataCountFavor(countAllFavors);

            }

            @Override
            public void onFailure(Call<List<CountAllFavor>> call, Throwable t) {

            }
        });
        SachApi.sachApi.bookWithReadedCount().enqueue(new Callback<List<LuotDocSach>>() {
            @Override
            public void onResponse(Call<List<LuotDocSach>> call, Response<List<LuotDocSach>> response) {
                assert response.body() != null;
                luotDocSachs = new ArrayList<LuotDocSach>(response.body());
                adapter.setDataReadedCount(luotDocSachs);

            }

            @Override
            public void onFailure(Call<List<LuotDocSach>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
//        String paymentMethodNonce = dropInResult.getPaymentMethodNonce().getString();
        LichSuNapRequest lichSuNapRequest = new LichSuNapRequest(reader.getId(), BigDecimal.valueOf(money));
        ReaderApi.readerApi.napTien(lichSuNapRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Đã nạp thành công " + formatAmount(BigDecimal.valueOf(money)) + "");
                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    money = 0;
                                    fetchBooks();
                                    fetchUser();
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDropInFailure(@NonNull Exception error) {
        if (error instanceof UserCanceledException) {
            Log.i("success", "Cancel");
        } else {
            // handle error
        }
    }
}