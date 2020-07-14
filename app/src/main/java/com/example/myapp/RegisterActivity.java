package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private Button genOTP;
    String Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setup();

        genOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    startActivity(new Intent(RegisterActivity.this,OTPActivity.class).putExtra("phone",Number));
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Enter Valid Phone Number",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setup(){
        phoneNumber=findViewById(R.id.etPhoneNumber);
        genOTP=findViewById(R.id.btnGenOTP);
    }

    private boolean validate(){
        Number=phoneNumber.getText().toString().trim();
        if(Number.length()==10)
            return true;
        return false;
    }
}
