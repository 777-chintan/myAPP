package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceProvider extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private Spinner spn;
    private Button btn;
    Float rating;
    private Switch status;
    private boolean c,flag;
    private BottomNavigationView bottomNavigationView;


    private Spinner location;
    private ArrayList<AddressSetup> address;
    private ArrayList<String> addressname;
    private ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        setup();
        setbottom();
        getstatus();
        makelist();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                check();
//            }
//        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcurrentOrder();
                if(c){
                    c=false;
                }
                else{
                    if(flag) {
                        c = true;
                    }
                    else{
                        Toast.makeText(ServiceProvider.this,"You Cannot Turnon Status",Toast.LENGTH_SHORT).show();
                    }
                }
                status.setChecked(c);
                reference.child("status").setValue(c);
            }
        });

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Location")){
                    Toast.makeText(ServiceProvider.this,"Select Location plz or add into Manage Address",Toast.LENGTH_SHORT).show();
                }
                else{
                    AddressSetup addressSetup=address.get(position-1);
                    reference.child("lng").setValue(addressSetup.getLng());
                    reference.child("lat").setValue(addressSetup.getlat());
                    reference.child("address").setValue(addressSetup.getAddress());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getcurrentOrder(){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("UserOrders").child("Current Order").child(FirebaseAuth.getInstance().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    flag=false;
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

    private void setup(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        firebaseAuth= FirebaseAuth.getInstance();
        //spn=findViewById(R.id.skill);
        //btn=findViewById(R.id.add_skill);
        //ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.skill,R.layout.support_simple_spinner_dropdown_item);
        //spn.setAdapter(adapter);
        status=findViewById(R.id.swstatus);
        reference=FirebaseDatabase.getInstance().getReference().child("Provider").child(FirebaseAuth.getInstance().getUid());
        location=findViewById(R.id.tvlocation);
        address=new ArrayList<>();
        addressname=new ArrayList<>();
        adapter2=new ArrayAdapter<>(ServiceProvider.this, R.layout.support_simple_spinner_dropdown_item, addressname);
        location.setAdapter(adapter2);
    }

    private void getstatus(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Provider provider=snapshot.getValue(Provider.class);
                status.setChecked(provider.getStatus());
                c=provider.getStatus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void check(){
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("All Services and Providers");
        DatabaseReference ref2=ref1.child(spn.getSelectedItem().toString());
        System.out.println(firebaseAuth.getUid());
        ref2.orderByChild("id").equalTo(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())  addData();
                else{
                    Toast.makeText(ServiceProvider.this,spn.getSelectedItem().toString()+" is already added",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addData(){
        String id=FirebaseAuth.getInstance().getUid();
        DatabaseReference ref3=FirebaseDatabase.getInstance().getReference("All Services and Providers");
        DatabaseReference ref4=ref3.child(spn.getSelectedItem().toString());
        ref4.child(id).child("id").setValue(id);
        Toast.makeText(ServiceProvider.this,spn.getSelectedItem().toString()+" added Successfully",Toast.LENGTH_SHORT).show();
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ServiceProvider.this,RegisterActivity.class));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu:{
                startActivity(new Intent(ServiceProvider.this,Accountprovider.class));
                address.clear();
                addressname.clear();
                addressname.add("Select Location");
                break;
            }
            case R.id.skill:{
                startActivity(new Intent(ServiceProvider.this,SkillActivity.class));
                break;
            }
            case R.id.current:{
                startActivity(new Intent(ServiceProvider.this,Currentorder.class));
                break;
            }
            case R.id.past:{
                startActivity(new Intent(ServiceProvider.this,Pastorders.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void makelist(){
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Addresses").child(FirebaseAuth.getInstance().getUid());
        address.clear();
        addressname.clear();
        addressname.add("Select Location");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        AddressSetup  addressSetup=snapshot1.getValue(AddressSetup.class);
                        address.add(addressSetup);
                        addressname.add(addressSetup.getAddress());
                    }
                    adapter2.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(ServiceProvider.this,"Please add address into the ManageAddress",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setbottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home: {
                        Toast.makeText(ServiceProvider.this,"You are there",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_cart:
                        startActivity(new Intent(ServiceProvider.this,Currentorder.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(ServiceProvider.this, Accountprovider.class));
                        break;
                }
                return true;
            }
        });
    }

}
