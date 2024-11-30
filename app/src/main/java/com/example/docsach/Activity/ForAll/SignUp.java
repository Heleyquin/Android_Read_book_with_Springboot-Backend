package com.example.docsach.Activity.ForAll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.docsach.R;

public class SignUp extends AppCompatActivity {

    EditText name, ho, username, password, rePassword;
    Button signUpButton;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        setButton();
        setEvent();
    }
    void setButton(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        signUpButton = findViewById(R.id.signUpButton);
        back = findViewById(R.id.back);
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
                    Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
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