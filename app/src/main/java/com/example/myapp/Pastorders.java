package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pastorders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> orderList;
    HashMap<String, String> hashMap=new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastorders);
        setTitle("Past Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        func();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Orders");
        Query query=ref;
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            orderList.clear();
            if(hashMap.isEmpty()){
                Toast.makeText(Pastorders.this,"No Past Orders",Toast.LENGTH_SHORT).show();
            }
            else {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Order order=snapshot.getValue(Order.class);
                    String id=order.getOrderID();
                    if(hashMap.containsKey(id)){
                        orderList.add(order);
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
        String id= FirebaseAuth.getInstance().getUid();
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("UserOrders").child("Past Orders").child(id);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Order order=ds.getValue(Order.class);
                        hashMap.put(order.orderID,order.orderID);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setup(){
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(this,orderList);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.current:{
                startActivity(new Intent(Pastorders.this,Currentorder.class));
                finish();
                break;
            }
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
