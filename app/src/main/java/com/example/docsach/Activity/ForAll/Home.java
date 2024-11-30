package com.example.docsach.Activity.ForAll;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.docsach.Activity.BookFragment.HomeFragment;
import com.example.docsach.Activity.PackFragment.PackFragment;
import com.example.docsach.Activity.SettingFragment.SettingFragment;
import com.example.docsach.R;
import com.example.docsach.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.phim) {
                replaceFragment(new PackFragment());

            }else if (itemId == R.id.setting) {
                replaceFragment(new SettingFragment());
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
}