package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private String verificationotp;
    private EditText OTP;
    private Button Register;
    private String phone;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        setup();
        sendVerificationCode(phone);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OTP.getText().toString().length()==6)
                    verifyVerificationCode(OTP.getText().toString());
                else
                    Toast.makeText(OTPActivity.this,"Enter Validate OTP",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setup(){
        OTP=findViewById(R.id.etOTP);
        Register=findViewById(R.id.btnValOTP);
        firebaseAuth=FirebaseAuth.getInstance();
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
    }

    private void sendVerificationCode(String phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phone,60, TimeUnit.SECONDS,OTPActivity.this,mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code=phoneAuthCredential.getSmsCode();

                if(code!=null){
                    OTP.setText(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OTPActivity.this,"Verification Failed!"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(OTPActivity.this,"OTP Sent",Toast.LENGTH_SHORT).show();
                verificationotp=s;
            }
        });
    }

    private void verifyVerificationCode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationotp,code);
        SignInWithCredential(credential);
    }

    private void SignInWithCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    IsAdded();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(OTPActivity.this,"You have entered Wrong OTP",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void IsAdded() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("User");
        System.out.println(firebaseAuth.getUid());
        DatabaseReference databaseReference = ref1.child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    startActivity(new Intent(OTPActivity.this,MainActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(OTPActivity.this,AddDetailsActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OTPActivity.this, "Database Error" + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
