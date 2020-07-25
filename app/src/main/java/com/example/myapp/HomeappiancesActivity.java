package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeappiancesActivity extends AppCompatActivity {

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16,tv17;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeappiances);
        setTitle("Home Appliances Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        setbottom();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), CustomerActivity.class);
        finish();
        startActivityForResult(myIntent, 0);
        return true;
    }

    private void setup(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);
        tv7=findViewById(R.id.tv7);
        tv8=findViewById(R.id.tv8);
        tv9=findViewById(R.id.tv9);
        tv10=findViewById(R.id.tv10);
        tv11=findViewById(R.id.tv11);
        tv12=findViewById(R.id.tv12);
        tv13=findViewById(R.id.tv13);
        tv14=findViewById(R.id.tv14);
        tv15=findViewById(R.id.tv15);
        tv16=findViewById(R.id.tv16);
        tv17=findViewById(R.id.tv17);
    }

    private void setbottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home: {
                        finish();
                        startActivity(new Intent(HomeappiancesActivity.this,CustomerActivity.class));
                        break;
                    }
                    case R.id.nav_cart:
                        startActivity(new Intent(HomeappiancesActivity.this,WelcomeActivity.class));
                        break;
                    case R.id.nav_profile:
                        finish();
                        startActivity(new Intent(HomeappiancesActivity.this,AccountActivity.class));
                        break;
                }
                return true;
            }
        });
    }

}
