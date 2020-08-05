package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Addskill extends AppCompatActivity {

    private Spinner spn;
    private DatabaseReference ref1,ref2;
    private Button btn;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addskill);
        setTitle("Add Skill");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    String id=FirebaseAuth.getInstance().getUid();
                    String skill=spn.getSelectedItem().toString();
                    int x=Integer.parseInt(price.getText().toString().trim());
                    ref1.child(skill).child("price").setValue(x);
                    ref2.child(skill).child(id).child("id").setValue(id);
                    ref2.child(skill).child(id).child("price").setValue(x);
                }
            }
        });
    }

    private boolean check(){
        if(price!=null && price.getText().toString().isEmpty()==false){
            return true;
        }
        Toast.makeText(Addskill.this,"Enter Price plz",Toast.LENGTH_SHORT).show();
        return false;
    }

    private void setup(){
        spn=findViewById(R.id.skill);
        price=findViewById(R.id.etprice);
        btn=findViewById(R.id.add_skill);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.skill,R.layout.support_simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        ref1= FirebaseDatabase.getInstance().getReference("Skill").child(FirebaseAuth.getInstance().getUid());
        ref2=FirebaseDatabase.getInstance().getReference("All Services and Providers");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), SkillActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    public void onBackPressed(){
        Intent myIntent = new Intent(getApplicationContext(), SkillActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
    }
}
