package com.example.docsach.Activity.BookFragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.docsach.API.ReaderApi;
import com.example.docsach.API.SachApi;
import com.example.docsach.Adapter.BookFragment.CommentAdapter;
import com.example.docsach.Model.DTO.CmtRequest;
import com.example.docsach.Model.DTO.CmtResponse;
import com.example.docsach.Model.Key.Key_BinhLuan;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.R;

import java.time.LocalDateTime;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvCmt;
    private CommentAdapter adapter;
    private EditText edtCmt;
    private Button btnSend;

    private List<CmtResponse> cmtResponseList;
    private String cmt;
    private Sach sach;
    private Reader reader;


    public CommentFragment() {

    }
    public CommentFragment(List<CmtResponse> list, Sach sach, Reader reader){
        this.cmtResponseList = list;
        this.reader = reader;
        this.sach = sach;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentFragment newInstance(String param1, String param2) {
        CommentFragment fragment = new CommentFragment();
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

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        btnSend = view.findViewById(R.id.btnSend);
        edtCmt = view.findViewById(R.id.edtCmt);

        rvCmt = view.findViewById(R.id.rvCmt);
        rvCmt.setLayoutManager(new LinearLayoutManager(getContext()));

        edtCmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cmt = charSequence.toString();
                cmt = cmt.trim();
                if(cmt.isEmpty()){
                    btnSend.setVisibility(View.GONE);
                }else{
                    btnSend.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cmt = charSequence.toString();
                cmt = cmt.trim();
                if(cmt.isEmpty()){
                    btnSend.setVisibility(View.GONE);
                }else{
                    btnSend.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                cmt = cmt.trim();
                if(cmt.isEmpty()){
                    btnSend.setVisibility(View.GONE);
                }else{
                    btnSend.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CmtRequest cmtRequest = new CmtRequest(new Key_BinhLuan(sach.getId(), reader.getId()), cmt);
                ReaderApi.readerApi.comment(cmtRequest).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CommentFragment.super.getContext());
                            builder.setTitle("Bình luận thành công cho " + sach.getTenSach())
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        SachApi.sachApi.getCmtById(sach.getId()).enqueue(new Callback<List<CmtResponse>>() {
                                            @Override
                                            public void onResponse(Call<List<CmtResponse>> call, Response<List<CmtResponse>> response) {
                                                if(response.isSuccessful()){
                                                    cmtResponseList = response.body();
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                        cmtResponseList.sort((cmt1, cmt2) ->{
                                                            LocalDateTime time1 = LocalDateTime.parse(cmt1.getId().getThoiGianTao());
                                                            LocalDateTime time2 = LocalDateTime.parse(cmt2.getId().getThoiGianTao());
                                                            return time2.compareTo(time1);
                                                        });
                                                    }
                                                    adapter.setData(cmtResponseList);
                                                    cmt = "";
                                                    edtCmt.setText("");
                                                    edtCmt.clearFocus();
                                                    dialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<CmtResponse>> call, Throwable t) {

                                            }
                                        });
                                        dialog.dismiss();
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        adapter = new CommentAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cmtResponseList.sort((cmt1, cmt2) ->{
                LocalDateTime time1 = LocalDateTime.parse(cmt1.getId().getThoiGianTao());
                LocalDateTime time2 = LocalDateTime.parse(cmt2.getId().getThoiGianTao());
                return time2.compareTo(time1);
            });
        }
        adapter.setData(cmtResponseList);
        rvCmt.setAdapter(adapter);

        return view;
    }
}