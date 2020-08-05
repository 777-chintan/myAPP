package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rateprovider extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button btn;
    private int k=0,raters;
    private String m="Select Star";
    private String oid,cid,pid;
    private DatabaseReference ref1,ref2;
    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rateprovider);
        setTitle("Rate Last Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                k=(int) rating;
                switch (k){
                    case 1: {
                        m="Very Bad";
                        break;
                    }
                    case 2: {
                        m="Not Good";
                        break;
                    }
                    case 3: {
                        m="Good";
                        break;
                    }
                    case 4: {
                        m="Great";
                        break;
                    }
                    case 5: {
                        m="Awesome";
                        break;
                    }
                }
                Toast.makeText(Rateprovider.this,m,Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(k==0){
                    Toast.makeText(Rateprovider.this,m,Toast.LENGTH_SHORT).show();
                }
                else {
                    get();
                }
            }
        });

    }

    private void get(){
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Provider provider=snapshot.getValue(Provider.class);
                raters=provider.getNumberofraters();
                rating=provider.getRating();
                ref1.child("rating").setValue(((rating*raters)+(float) k)/(raters+1f));
                ref1.child("numberofraters").setValue(rating+1);
                ref2.removeValue();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setup(){
        ratingBar=findViewById(R.id.ratingBar);
        btn=findViewById(R.id.btn);
        Intent intent=getIntent();
        oid=intent.getStringExtra("oid");
        cid=intent.getStringExtra("cid");
        pid=intent.getStringExtra("pid");
        ref1= FirebaseDatabase.getInstance().getReference("Provider").child(pid);
        ref2=FirebaseDatabase.getInstance().getReference("UserOrders").child("Current Order").child(cid);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
