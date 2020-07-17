package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class CustomerActivity extends AppCompatActivity {

    private FrameLayout carpentry,electrical,plumbing,home_appliances,cleaning,painting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setup();

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

    }

    private void setup(){
        carpentry=findViewById(R.id.frCarpentry);
        electrical=findViewById(R.id.frElectrical);
        painting=findViewById(R.id.frPainting);
        plumbing=findViewById(R.id.frPlumbing);
        home_appliances=findViewById(R.id.frHomeAppliances);
        cleaning=findViewById(R.id.frCleaning);
    }

}
