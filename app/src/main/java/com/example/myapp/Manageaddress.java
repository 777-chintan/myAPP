package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Manageaddress extends AppCompatActivity {

    private ListView List;
    private Button add,delete;
    private DatabaseReference ref;
    private boolean addressselected;
    private int selectedposition=0;

    private ArrayList<String> addressname;
    private ArrayList<String> addresskey;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageaddress);
        setTitle("Manage Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setup();
        delete.setEnabled(false);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedposition=position;
                addressselected=true;
                delete.setEnabled(true);
            }
        });

        addChildEventListner();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Manageaddress.this,Addaddress.class));
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List.setItemChecked(selectedposition,false);
                ref.child(addresskey.get(selectedposition)).removeValue();
                addressselected=false;
                delete.setEnabled(false);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    private void setup(){
        List=findViewById(R.id.lvaddresslist);
        add=(Button) findViewById(R.id.btnadd);
        delete=(Button) findViewById(R.id.btndelete);
        ref=FirebaseDatabase.getInstance().getReference("Addresses").child(FirebaseAuth.getInstance().getUid());
        addressname=new ArrayList<String>();
        addresskey=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(Manageaddress.this,android.R.layout.simple_list_item_single_choice,addressname);
        List.setAdapter(adapter);
        List.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void addChildEventListner(){
        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.add((String) snapshot.child("address").getValue());
                addresskey.add(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key=snapshot.getKey();
                int index=addresskey.indexOf(key);
                if(index!=-1){
                    addressname.remove(index);
                    addresskey.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addChildEventListener(childEventListener);
    }

}
