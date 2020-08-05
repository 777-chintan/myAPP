package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Currentorder extends AppCompatActivity {

    private Order order;
    private TextView service,address,price,total,quantity,phonenumber;
    private Button btn;
    DatabaseReference ref,ref2,ref3,ref4;
    private boolean completed=false;
    private boolean user=false;
    private String Number,key,x;
    private ArrayList<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentorder);
        setTitle("Current Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        getcurrentOrder();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1,id2,id3;
                id1=order.getOrderID();
                id2=order.getCustomerID();
                id3=order.getProviderID();
                DatabaseReference r=FirebaseDatabase.getInstance().getReference("Provider").child(id3).child("status");
                r.setValue(true);
                if(btn.getText().toString().equals("Cancel")){
                    ref2.child(id1).removeValue();
                    ref3.child(id2).removeValue();
                    ref3.child(id3).removeValue();
                }
                else{
                    ref2.child("check").setValue(true);
                    ref3.child(id3).removeValue();
                    ref4.child(id2).child(id1).child("orderID").setValue(id1);
                    ref4.child(id3).child(id1).child("orderID").setValue(id1);
                }
                finish();
            }
        });

    }

    private void setup(){
        ref= FirebaseDatabase.getInstance().getReference("UserOrders").child("Current Order").child(FirebaseAuth.getInstance().getUid());
        ref2=FirebaseDatabase.getInstance().getReference("Orders");
        ref3=FirebaseDatabase.getInstance().getReference("UserOrders").child("Current Order");
        ref4=FirebaseDatabase.getInstance().getReference("UserOrders").child("Past Orders");
        Number=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        service=findViewById(R.id.tvservice);
        address=findViewById(R.id.tvaddress);
        price=findViewById(R.id.tvprice);
        total=findViewById(R.id.tvtotal);
        quantity=findViewById(R.id.tvquantity);
        phonenumber=findViewById(R.id.tvnumber);
        btn=findViewById(R.id.btnplace);
        keys=new ArrayList<String>();
        setinvisible();
    }

   private void getcurrentOrder(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                keys.clear();
                if(snapshot.exists()){
                    for(DataSnapshot a:snapshot.getChildren()){
                        key=a.getKey();
                    }
                    getcurrentOrder2();
                }
                else{
                    Toast.makeText(Currentorder.this,"You have no current Order.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   }

   private void getcurrentOrder2(){
        ref2.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                order=snapshot.getValue(Order.class);
                completed=order.Iscompleted();
                if(completed)
                    Toast.makeText(Currentorder.this,"You have no current Order.",Toast.LENGTH_SHORT).show();
                else
                    set();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   }

   private void set(){
       if(order!=null){
           if(!completed){
               setvisible();
               setorder();
               if(Number.equals(order.getCustomerno())){
                   phonenumber.setText("Provider No. : " + order.getProviderno());
                   btn.setText("Cancel");
               }
               else{
                   phonenumber.setText("Customer No. : " + order.getCustomerno());
                   btn.setText("Complete");
               }
           }
           else{
               Toast.makeText(Currentorder.this,"You have no current Order.",Toast.LENGTH_SHORT).show();
           }
       }
       else
           Toast.makeText(Currentorder.this,"You have no current Order.",Toast.LENGTH_SHORT).show();
   }
   private void setvisible(){
       service.setVisibility(View.VISIBLE);
       address.setVisibility(View.VISIBLE);
       price.setVisibility(View.VISIBLE);
       total.setVisibility(View.VISIBLE);
       quantity.setVisibility(View.VISIBLE);
       phonenumber.setVisibility(View.VISIBLE);
       btn.setVisibility(View.VISIBLE);
   }
   private void setinvisible(){
       service.setVisibility(View.INVISIBLE);
       address.setVisibility(View.INVISIBLE);
       price.setVisibility(View.INVISIBLE);
       total.setVisibility(View.INVISIBLE);
       quantity.setVisibility(View.INVISIBLE);
       phonenumber.setVisibility(View.INVISIBLE);
       btn.setVisibility(View.INVISIBLE);
   }

   private void setorder(){
        service.setText("Service : " + order.getService());
        address.setText("Address : " + order.getAddress());
        price.setText("Price : " + order.getPrice());
        total.setText("Total : " + order.getTotal());
        quantity.setText("Quantity : " + order.getQuantity());
   }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.past,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.past:{
                startActivity(new Intent(Currentorder.this,Pastorders.class));
                finish();
                break;
            }
        }
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        finish();
    }
}
