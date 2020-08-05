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
    private String usertype="SET";
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getUid()!=null) {
            gotopage();
        }
        else{
            finish();
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
        }
    }

    private void gotopage() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("User");
        System.out.println(firebaseAuth.getUid());
        DatabaseReference databaseReference = ref1.child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    userProfile = snapshot.getValue(UserProfile.class);
                    usertype = userProfile.getUserType();
                    if(usertype==null){
                        finish();
                        startActivity(new Intent(MainActivity.this,AddDetailsActivity.class));
                    }
                    if (usertype != null && usertype.equals("Customer")) {
                        finish();
                        startActivity(new Intent(MainActivity.this, CustomerActivity.class));
                    }
                    if (usertype != null && usertype.equals("Service Provider")) {
                        finish();
                        startActivity(new Intent(MainActivity.this, ServiceProvider.class));
                    }
                }
                else{
                    finish();
                    startActivity(new Intent(MainActivity.this,AddDetailsActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database Error" + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
