package com.example.docsach.Activity.ForAll;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.docsach.API.AccountApi;
import com.example.docsach.Model.DTO.AccountRequest;
import com.example.docsach.Model.DTO.PackInUse;
import com.example.docsach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    TextView signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUp);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent register_layout = new Intent(MainActivity.this, Home.class);
//                register_layout.putExtra("username", username.getText().toString());
//
//                startActivity(register_layout);
//                finish();
                AccountRequest accountRequest = new AccountRequest(username.getText().toString(), password.getText().toString());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonWhist = gson.toJson(accountRequest);
                Log.i("loginAccount", jsonWhist);
                AccountApi.accountApi.login(accountRequest).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String m = null;
                        if(response.isSuccessful()){
                            Log.i("login", username.getText().toString());
                            assert response.body() != null;
                            try {
                                m = response.body().string();
                                AccountApi.accountApi.extract(m).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if(response.isSuccessful()){
                                            Intent register_layout = new Intent(MainActivity.this, Home.class);
                                            register_layout.putExtra("username", username.getText().toString());

                                            startActivity(register_layout);
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Lỗi đăng nhập, hãy thử lại", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Lỗi token, hãy thử lại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Sai thông tin đăng nhập, hãy thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Lỗi đăng nhập, hãy thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent register_layout = new Intent(MainActivity.this, SignUp.class);
                startActivity(register_layout);
                finish();
            }
        });
    }
}