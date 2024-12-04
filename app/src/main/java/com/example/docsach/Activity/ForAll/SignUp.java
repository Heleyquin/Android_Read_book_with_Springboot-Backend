package com.example.docsach.Activity.ForAll;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.docsach.API.AccountApi;
import com.example.docsach.Model.DTO.UserAccountRequest;
import com.example.docsach.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText name, ho, username, password, rePassword, edtEmail;
    Button signUpButton;
    TextView back;
    Spinner spGender;
    short gender = 1;//1 = nam, 0 = nữ
    String genderStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        setButton();
        setDataSpinner();
        setEvent();
    }
    void setButton(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        signUpButton = findViewById(R.id.signUpButton);
        ho = findViewById(R.id.ho);
        name = findViewById(R.id.ten);
        back = findViewById(R.id.back);
        spGender = findViewById(R.id.spGender);
        edtEmail = findViewById(R.id.edtEmail);
    }
    void setDataSpinner(){
        String[] genders = {"Nam", "Nữ"};
        final HashMap<String, Integer> genderMap = new HashMap<>();
        genderMap.put("Nam", 1);
        genderMap.put("Nữ", 0);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spGender.setAdapter(adapter);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderStr = adapterView.getItemAtPosition(i).toString();
                gender = genderMap.get(genderStr).shortValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                genderStr = adapterView.getItemAtPosition(0).toString();
                gender = genderMap.get(genderStr).shortValue();
            }
        });


    }
    void setEvent(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUp.this, "Name cannot empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(username.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUp.this, "Username cannot empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().trim().isEmpty()){
                    Toast.makeText(SignUp.this, "Passưord cannot empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(rePassword.getText().toString().trim().isEmpty()){
                    return;
                }
                if(password.getText().toString().trim().equals(rePassword.getText().toString().trim())){

                }
                else{
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean gen = (gender == 1);
                UserAccountRequest userAccountRequest = new UserAccountRequest(edtEmail.getText().toString(), username.getText().toString(), ho.getText().toString(), name.getText().toString(), "", gen, 1);
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                String jsonWhist = gson.toJson(userAccountRequest);
//                Log.i("signUp", jsonWhist);
                AccountApi.accountApi.regisReader(userAccountRequest).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);

                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUp.this, "Có lỗi trong quá trình đăng ký, quay lại sau", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_layout = new Intent(SignUp.this, MainActivity.class);
                startActivity(register_layout);
                finish();
            }
        });
    }
}