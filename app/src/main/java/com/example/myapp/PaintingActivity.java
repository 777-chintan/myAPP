package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class PaintingActivity extends AppCompatActivity {

    //private TextView tv1,tv2,tv3;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting);
        setTitle("Painting Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        setbottom();
    }

    public void onclick(View v){
        TextView t=(TextView) findViewById(v.getId());
        //Toast.makeText(ElectricalActivity.this,t.getText().toString(),Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PaintingActivity.this,GetlistActivity.class).putExtra("service",t.getText().toString()));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), CustomerActivity.class);
        finish();
        startActivityForResult(myIntent, 0);
        return true;
    }

    private void setup(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
//        tv1=findViewById(R.id.tv1);
//        tv2=findViewById(R.id.tv2);
//        tv3=findViewById(R.id.tv3);
    }

    private void setbottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home: {
                        finish();
                        startActivity(new Intent(PaintingActivity.this, CustomerActivity.class));
                        break;
                    }
                    case R.id.nav_cart:
                        startActivity(new Intent(PaintingActivity.this,WelcomeActivity.class));
                        break;
                    case R.id.nav_profile:
                        finish();
                        startActivity(new Intent(PaintingActivity.this,AccountActivity.class));
                        break;
                }
                return true;
            }
        });
    }
}
