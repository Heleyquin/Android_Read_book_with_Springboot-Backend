package com.example.docsach.Adapter.BookFragment;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.docsach.Activity.BookFragment.CommentFragment;
import com.example.docsach.Activity.BookFragment.RateFragment;
import com.example.docsach.Model.DTO.CmtResponse;
import com.example.docsach.Model.DTO.DanhGiaResponse;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<CmtResponse> cmtResponseList;
    private List<DanhGiaResponse> danhGiaResponseList;
    private Reader reader;
    private Sach sach;
    @SuppressLint("NotifyDataSetChanged")
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<CmtResponse> cmtResponseList, List<DanhGiaResponse> danhGiaResponseList, Sach sach, Reader reader) {
        super(fragmentActivity);

        this.danhGiaResponseList = danhGiaResponseList;
        this.cmtResponseList = cmtResponseList;
        this.reader = reader;
        this.sach = sach;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new CommentFragment(cmtResponseList, sach, reader);
        } else {
            if (danhGiaResponseList == null || danhGiaResponseList.isEmpty()) {
                return new RateFragment(danhGiaResponseList, sach, reader);
            }else{
                return new RateFragment(danhGiaResponseList, sach, reader);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
