package com.example.docsach.Activity.ForAll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.docsach.Activity.BookFragment.HomeFragment;
import com.example.docsach.Activity.PackFragment.PackFragment;
import com.example.docsach.Activity.SettingFragment.SettingFragment;
import com.example.docsach.Model.CountAllFavor;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.Model.LuotDocSach;
import com.example.docsach.Model.Reader;
import com.example.docsach.Model.Sach;
import com.example.docsach.Model.Sach_Mong_Muon;
import com.example.docsach.R;
import com.example.docsach.databinding.ActivityHomeBinding;

import java.math.BigDecimal;
import java.util.List;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    String username;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setDataIntent();

        replaceFragment(new HomeFragment(username));
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.home){
                replaceFragment(new HomeFragment(username));
            } else if (itemId == R.id.phim) {
                replaceFragment(new PackFragment(username));

            }else if (itemId == R.id.setting) {
                replaceFragment(new SettingFragment(username));
            }
            return true;
        });


    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameNav, fragment);
        fragmentTransaction.commit();
    }
    private void setDataIntent(){
        Intent intent = getIntent();

        username = (String) intent.getSerializableExtra("username");
    }
}