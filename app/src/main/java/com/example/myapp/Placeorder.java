package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Placeorder extends AppCompatActivity {

    private int k=1,cost,t;
    private TextView service,address,price,total,quantity;
    private ImageView plus,minus;
    private Button place;
    private Provider provider;
    private String s,key;
    private AddressSetup fix_address=null;
    private DatabaseReference ref,ref2,ref3;
    private Order order;
    private PushNotification pushNotification;
    private String token,id;
    private boolean completed,flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);
        setTitle("Place Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        Intent intent=getIntent();
        provider=(Provider) intent.getSerializableExtra("Provider");
        id=provider.getId();
        s=intent.getStringExtra("service");
        service.setText("Service : " + s);
        getaddress();
        cost=provider.getPrice();
        t=cost;
        price.setText("Price : " + Integer.toString(cost));
        quantity.setText("Quantity : " + Integer.toString(k));
        total.setText("Total : " + Integer.toString(t));

        getToken();

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k++;
                t=k*cost;
                quantity.setText("Quantity : " + Integer.toString(k));
                total.setText("Total : " + Integer.toString(t));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(k==1){
                    Toast.makeText(Placeorder.this,"Quantity 0 is not valid",Toast.LENGTH_SHORT).show();
                }
                else{
                    k--;
                    t=k*cost;
                    quantity.setText("Quantity : " + Integer.toString(k));
                    total.setText("Total : " + Integer.toString(t));
                }
            }
        });

        check();

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref=FirebaseDatabase.getInstance().getReference("Orders");
                ref2=FirebaseDatabase.getInstance().getReference("UserOrders").child("Current Order");
                ref3=FirebaseDatabase.getInstance().getReference("Provider").child(id);
                if(flag) {
                    String key = ref.push().getKey();
                    order.setOrderID(key);
                    order.setCustomerID(FirebaseAuth.getInstance().getUid());
                    order.setProviderID(provider.getId());
                    order.setService(s);
                    order.setCustomerno(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    order.setProviderno(provider.getPhoneNumber());
                    order.setQuantity(k);
                    order.setPrice(cost);
                    order.setTotal(k, cost);
                    order.setAddress(fix_address.getAddress());
                    order.setlatitude(fix_address.getlat());
                    order.setlongitude(fix_address.getLng());
                    order.setcompleted(false);
                    ref.child(key).setValue(order);
                    ref2.child(provider.getId()).child(key).child("orderID").setValue(key);
                    ref2.child(FirebaseAuth.getInstance().getUid()).child(key).child("orderId").setValue(key);
                    pushNotification.RequestNotification("App name","You Have recieved Order",token);
                    ref3.child("status").setValue(false);
                    startActivity(new Intent(Placeorder.this,Currentorder.class));
                    finish();
                }
            }
        });
    }

    private void getToken(){
        DatabaseReference r=FirebaseDatabase.getInstance().getReference("User").child(id);
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile p=snapshot.getValue(UserProfile.class);
                token=p.getUserToken();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void check(){
        getcurrentOrder();
    }

    private void getcurrentOrder(){
        ref2.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot a:snapshot.getChildren()){
                        key=a.getKey();
                    }
                    getcurrentOrder2();
                }
                else{
                    flag=true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getcurrentOrder2(){
        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                order=snapshot.getValue(Order.class);
                completed=order.Iscompleted();
                if(completed) {
                    Intent intent=new Intent(Placeorder.this,Rateprovider.class);
                    intent.putExtra("oid",order.orderID);
                    intent.putExtra("cid",order.customerID);
                    intent.putExtra("pid",order.providerID);
                    startActivity(intent);
                    flag=true;
                }
                else{
                    flag=false;
                    Toast.makeText(Placeorder.this,"You already have an Order.",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getaddress(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Fix Address").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    fix_address=snapshot.getValue(AddressSetup.class);
                    address.setText("Address : " + fix_address.getAddress());
                }
                else{
                    Toast.makeText(Placeorder.this,"You haven't enter location",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setup(){
        service=findViewById(R.id.tvservice);
        address=findViewById(R.id.tvaddress);
        price=findViewById(R.id.tvprice);
        total=findViewById(R.id.tvtotal);
        quantity=findViewById(R.id.tvquantity);
        plus=findViewById(R.id.ivplus);
        minus=findViewById(R.id.ivminus);
        place=findViewById(R.id.btnplace);
        order=new Order();
        pushNotification=new PushNotification();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), CustomerActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }
}
