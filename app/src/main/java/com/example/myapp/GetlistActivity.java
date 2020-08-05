package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.SphericalUtil;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProviderAdapter adapter;
    private List<Provider> providerList;
    private AddressSetup fix_address=null;
    private TextView t1;
    private String service;
    Double rating;
    HashMap<String,Provider> hashMap=new HashMap<String,Provider>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getlist);
        setup();
        setTitle(service);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        func();

        getaddress();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("All Services and Providers");
        Query query=ref.child(service);
        query.addListenerForSingleValueEvent(valueEventListener);


    }

    private void getaddress(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Fix Address").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    fix_address=snapshot.getValue(AddressSetup.class);
                }
                else{
                    Toast.makeText(GetlistActivity.this,"You haven't enter location",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            providerList.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Provider provider=snapshot.getValue(Provider.class);
                    String id=provider.getId();
                    int x=provider.getPrice();
                    if(fix_address!=null ) {
                        provider=hashMap.get(id);
                        String address=provider.getAddress();
                        if(address!=null) {
                            if (provider.getStatus() == true) {
                                double distance=SphericalUtil.computeDistanceBetween(new LatLng(fix_address.getlat(),fix_address.getLng()), new LatLng(provider.getLatitude(),provider.getLongitude()));
                                if (Double.compare(distance, 7000) < 0) {
                                    provider.setDistance(distance);
                                    provider.setPrice(x);
                                    providerList.add(provider);
                                }
                            }
                        }
                    }
                    else {
                        Toast.makeText(GetlistActivity.this,"You haven't enter location",Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void func(){
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Provider");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Provider provider=ds.getValue(Provider.class);
                        hashMap.put(provider.id,provider);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setup(){
        service=getIntent().getStringExtra("service");
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        providerList = new ArrayList<>();
        adapter = new ProviderAdapter(this,providerList,service);
        recyclerView.setAdapter(adapter);
        t1=findViewById(R.id.txt);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
