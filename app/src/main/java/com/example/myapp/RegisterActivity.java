package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private Button genOTP;
    String Number;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setup();
        if (firebaseAuth.getUid() != null) {
            IsAdded();
        }
        genOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    startActivity(new Intent(RegisterActivity.this,OTPActivity.class).putExtra("phone",Number));
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
        firebaseAuth=FirebaseAuth.getInstance();
    }

    private boolean validate(){
        Number=phoneNumber.getText().toString().trim();
        if(Number.length()==10)
            return true;
        return false;
    }

    private void IsAdded() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("User");
        System.out.println(firebaseAuth.getUid());
        DatabaseReference databaseReference = ref1.child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(RegisterActivity.this,AddDetailsActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this, "Database Error" + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
