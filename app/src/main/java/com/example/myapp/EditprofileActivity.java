package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditprofileActivity extends AppCompatActivity {

    private EditText name,number,age;
    private Button save;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        setbottom();

        final DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("User");
        final DatabaseReference databaseReference=ref1.child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile=snapshot.getValue(UserProfile.class);
                name.setText(userProfile.getUserName());
                age.setText(userProfile.userAge);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditprofileActivity.this,"Database error:"+error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    userProfile.setUserAge(age.getText().toString());
                    userProfile.setUserName(name.getText().toString());
                    databaseReference.setValue(userProfile);
                    finish();
                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        //Intent myIntent = new Intent(getApplicationContext(), ProfileActivity.class);
        //startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    private void setup(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        age=findViewById(R.id.etAge);
        name=findViewById(R.id.etName);
        number=findViewById(R.id.etPhoneNumber);
        save=findViewById(R.id.btnSave);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }

    private void setbottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home: {
                        finish();
                        startActivity(new Intent(EditprofileActivity.this, CustomerActivity.class));
                        break;
                    }
                    case R.id.nav_cart:
                        startActivity(new Intent(EditprofileActivity.this,WelcomeActivity.class));
                        break;
                    case R.id.nav_profile:
                        finish();
                        startActivity(new Intent(EditprofileActivity.this,AccountActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    private boolean validate(){
        if(name.getText().toString().isEmpty()){
            Toast.makeText(EditprofileActivity.this,"Enter Your Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(age.getText().toString().isEmpty() || age.getText().toString()=="0"){
            Toast.makeText(EditprofileActivity.this,"Enter Your Age",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
