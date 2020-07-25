package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

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
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");


                logininto();
                finish();

                startActivity(new Intent(OTPActivity.this, MainActivity.class));
            }

                else{
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(OTPActivity.this,"You have entered Wrong OTP",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void logininto() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = currentUser.getUid();

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //getting token for user
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            String token;
                            if (!task.isSuccessful()) {
                                token = task.getException().getMessage();
                                Log.w("FCM TOKEN Failed", task.getException());
                            } else {
                                token = task.getResult().getToken();
                                Log.i("FCM TOKEN", token);
                                ref.child(userid).child("userToken").setValue(token);
                            }
                        }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OTPActivity.this,"Error fetching data",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
