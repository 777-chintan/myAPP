package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String name,age,phone,usertype,id;
    private UserProfile userProfile;
    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
        if(firebaseAuth.getUid()!=null){
            checkusertype();
            if(flag==false){
                phone=getIntent().getStringExtra("phone");
                finish();
                startActivity(new Intent(MainActivity.this,AddDetailsActivity.class).putExtra("phone",phone));
            }
            usertype=userProfile.getUserType();
            if(usertype.equals("Customer")) {
                finish();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
            if(usertype.equals("Service Provider")){
                finish();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        }
        else {
            finish();
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }
    }

    private void setup(){
        firebaseAuth=FirebaseAuth.getInstance();
    }

    private void checkusertype(){
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("User");
        System.out.println(firebaseAuth.getUid());
        DatabaseReference databaseReference=ref1.child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                flag=dataSnapshot.exists();
                if(flag!=false) {
                    userProfile = dataSnapshot.getValue(UserProfile.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
