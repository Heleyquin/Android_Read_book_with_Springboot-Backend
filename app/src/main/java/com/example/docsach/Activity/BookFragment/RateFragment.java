package com.example.docsach.Activity.BookFragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.docsach.Adapter.BookFragment.RateAdapter;
import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.R;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvRate;
    private RateAdapter adapter;

    private List<DanhGiaResponse> danhGiaResponseList;
    private String cmt;
    private Sach sach;
    private Reader reader;

    public RateFragment(){

    }
    public RateFragment(List<DanhGiaResponse> list, Sach sach, Reader reader) {
        this.danhGiaResponseList = list;
        this.reader = reader;
        this.sach = sach;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RateFragment newInstance(String param1, String param2) {
        RateFragment fragment = new RateFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rate, container, false);

        rvRate = v.findViewById(R.id.rvRate);
        rvRate.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RateAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            danhGiaResponseList.sort((cmt1, cmt2) ->{
                LocalDateTime time1 = LocalDateTime.parse(cmt1.getThoiGianCapNhat());
                LocalDateTime time2 = LocalDateTime.parse(cmt2.getThoiGianCapNhat());
                return time2.compareTo(time1);
            });
        }
        adapter.setData(danhGiaResponseList);
        rvRate.setAdapter(adapter);
        return v;
    }
}