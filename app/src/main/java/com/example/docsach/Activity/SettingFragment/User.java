package com.example.docsach.Activity.SettingFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.docsach.Model.KhachHang;
import com.example.docsach.R;

import java.util.ArrayList;
import java.util.List;

public class User extends AppCompatActivity {

    private KhachHang user;
    private ImageView ivImage;
    private EditText edtTen, edtCCCD, edtDiaChi;
    private Spinner spinnerGioiTinh;
    private Button btnConfirm,btnCancel;
    private boolean gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);

        getDataIntent();

        gender = user.getGioiTinh();

        setControl();
        setSpinner();
        setData();
        setEvent();

    }

    private void setEvent() {


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialogUpdate();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinnerGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    gender = false;
                }else{
                    gender = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData() {
        ivImage.setImageResource(user.getGioiTinh() ? R.drawable.default_reader_male : R.drawable.default_reader_female);
        spinnerGioiTinh.setSelection(user.getGioiTinh() ? 0 : 1);
        edtTen.setText(user.getTen());
        edtCCCD.setText(user.getCccd());
        edtDiaChi.setText(user.getDiachi());
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        user = (KhachHang) intent.getSerializableExtra("user");
    }

    private void setControl() {
        ivImage = findViewById(R.id.ivImage);
//        spinnerGioiTinh = findViewById(R.id.spinnerGioiTinh);
//        edtTen = findViewById(R.id.edtTen);
//        edtCCCD = findViewById(R.id.edtCCCD);
//        edtDiaChi = findViewById(R.id.edtDiaChi);
//        btnConfirm = findViewById(R.id.btnConfirm);
//        btnCancel = findViewById(R.id.btnCancel);
    }
    private void setSpinner(){
        List<String> gender = new ArrayList<>();
        gender.add("NAM");
        gender.add("NỮ");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(adapter);
    }
    private void showConfirmationDialogUpdate() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Bạn có chắc chắn muốn lưu??");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "LƯU",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        user.setTen(edtTen.getText().toString());
                        user.setCccd(edtCCCD.getText().toString());
                        user.setDiachi(edtDiaChi.getText().toString());
                        user.setGioiTinh(gender);
                        dialog.dismiss();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "HUỶ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}