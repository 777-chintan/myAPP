package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerActivity extends AppCompatActivity {

    private FrameLayout carpentry,electrical,plumbing,home_appliances,cleaning,painting;
    private BottomNavigationView bottomNavigationView;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setup();
        setbottom();

        carpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this,CarpentryActivity.class));
            }
        });

        electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this,ElectricalActivity.class));
            }
        });

        plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this,PlumbingActivity.class));
            }
        });

        home_appliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this,HomeappiancesActivity.class));
            }
        });

        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this,CleaningActivity.class));
            }
        });

        painting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this,PaintingActivity.class));
            }
        });

//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(CustomerActivity.this,"Not Implemented",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(CustomerActivity.this,MapActivity.class));
//            }
//        });

    }

    private void setup(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        carpentry=findViewById(R.id.frCarpentry);
        electrical=findViewById(R.id.frElectrical);
        painting=findViewById(R.id.frPainting);
        plumbing=findViewById(R.id.frPlumbing);
        home_appliances=findViewById(R.id.frHomeAppliances);
        cleaning=findViewById(R.id.frCleaning);
        location=findViewById(R.id.tvlocation);
    }

    private void setbottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(CustomerActivity.this,"You are Already on the Home Page",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_cart:
                        startActivity(new Intent(CustomerActivity.this,WelcomeActivity.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(CustomerActivity.this,AccountActivity.class));
                        break;
                }
                return true;
            }
        });
    }

}
