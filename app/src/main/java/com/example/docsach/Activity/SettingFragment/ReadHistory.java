package com.example.docsach.Activity.SettingFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docsach.Adapter.Setting.ReadHistoryAdapter;
import com.example.docsach.Model.LichSuDoc;
import com.example.docsach.Model.LichSuMua;
import com.example.docsach.R;

import java.util.List;

public class ReadHistory extends AppCompatActivity {
    private TextView tvReadHis;
    private RecyclerView rvReadHis;
    private ReadHistoryAdapter adapter;
    private List<LichSuDoc> lichSuDocList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_account);

        adapter = new ReadHistoryAdapter(this);

        getDataIntent();
        setControl();
        setRv();
        adapter.setData(lichSuDocList);

    }

//    private void setEvent() {
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (oldPass.getText().toString().equals(tk.getMk())){
//                    if(newPass.getText().toString().isEmpty()){
//                        Toast.makeText(getApplicationContext(), "Mật khẩu mới không được để trống", Toast.LENGTH_LONG).show();
//                    }else{
//                        if(newPass.getText().toString().equals(rePassword.getText().toString())){
//                            showConfirmationDialogChangePass();
//                        }else{
//                            Toast.makeText(getApplicationContext(), "Mật khẩu mới không trùng nhau", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Mật khẩu cũ không hợp lệ", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }


    private void setRv(){
        rvReadHis.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvReadHis.setAdapter(adapter);
    }
    private void setControl() {
        rvReadHis = findViewById(R.id.rvReadHis);
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        lichSuDocList = (List<LichSuDoc>) intent.getSerializableExtra("readHis");
    }
//    private void showConfirmationDialogChangePass() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//        builder1.setMessage("Bạn có chắc chắn muốn đổi mật khẩu??");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "ĐỔI MẬT KHẨU",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        tk.setMk(newPass.getText().toString());
//                        dialog.dismiss();
//                        Toast.makeText(getApplicationContext(), "Mật khẩu đã được đổi", Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "HUỶ",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }
}