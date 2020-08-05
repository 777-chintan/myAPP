package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Accountprovider extends AppCompatActivity {

    private TextView tv1,tv2,tv3,tv4;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setTitle("MY Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        setbottom();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Accountprovider.this,ProfileActivity.class));
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Accountprovider.this,Manageaddress.class));
                finish();
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Accountprovider.this,RegisterActivity.class));
                finish();
            }
        });
    }

    private void setup(){
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private void setbottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home: {
                        finish();
                        startActivity(new Intent(Accountprovider.this, ServiceProvider.class));
                        break;
                    }
                    case R.id.nav_cart:
                        startActivity(new Intent(Accountprovider.this,Currentorder.class));
                        break;
                    case R.id.nav_profile:
                        Toast.makeText(Accountprovider.this,"You are there",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}
