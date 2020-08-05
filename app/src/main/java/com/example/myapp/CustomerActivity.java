package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity{

    private FrameLayout carpentry,electrical,plumbing,home_appliances,cleaning,painting;
    private BottomNavigationView bottomNavigationView;
    private Spinner location;
    private ArrayList<AddressSetup> address;
    private ArrayList<String> addressname;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setup();
        setbottom();
        makelist();

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Fix Address").child(FirebaseAuth.getInstance().getUid());
                if(parent.getItemAtPosition(position).equals("Select Location")){
                    Toast.makeText(CustomerActivity.this,"plz Select or add Location into Manage Address",Toast.LENGTH_SHORT).show();
                    //ref.removeValue();
                }
                else{
                    AddressSetup addressSetup=address.get(position-1);
                    ref.setValue(addressSetup);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        carpentry=findViewById(R.id.frCarpentry);
        electrical=findViewById(R.id.frElectrical);
        painting=findViewById(R.id.frPainting);
        plumbing=findViewById(R.id.frPlumbing);
        home_appliances=findViewById(R.id.frHomeAppliances);
        cleaning=findViewById(R.id.frCleaning);
        location=findViewById(R.id.tvlocation);
        address=new ArrayList<>();
        addressname=new ArrayList<>();
        adapter=new ArrayAdapter<>(CustomerActivity.this, R.layout.support_simple_spinner_dropdown_item, addressname);
        location.setAdapter(adapter);
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
                        startActivity(new Intent(CustomerActivity.this,Currentorder.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(CustomerActivity.this,AccountActivity.class));
                        makelist();
                        break;
                }
                return true;
            }
        });
    }

    private void makelist(){
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Addresses").child(FirebaseAuth.getInstance().getUid());
        address.clear();
        addressname.clear();
        addressname.add("Select Location");
        adapter.notifyDataSetChanged();
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        AddressSetup  addressSetup=snapshot1.getValue(AddressSetup.class);
                        address.add(addressSetup);
                        addressname.add(addressSetup.getAddress());
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(CustomerActivity.this,"Please add address into the ManageAddress",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
