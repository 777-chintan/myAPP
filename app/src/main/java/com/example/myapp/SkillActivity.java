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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SkillActivity extends AppCompatActivity{
    private ListView List;
    private Button add,delete;
    private DatabaseReference ref,ref2;
    private int selectedposition=0;

    private ArrayList<String> skill;
    private ArrayList<String> skillkey;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        setTitle("Skills");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        delete.setEnabled(false);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedposition=position;
                delete.setEnabled(true);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=FirebaseAuth.getInstance().getUid();
                List.setItemChecked(selectedposition,false);
                ref.child(skillkey.get(selectedposition)).removeValue();
                ref2.child(skillkey.get(selectedposition)).child(id).removeValue();
                delete.setEnabled(false);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SkillActivity.this,Addskill.class));
                finish();
            }
        });

        addChildEventListner();
    }

    private void addChildEventListner(){
        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey() + " : " +  snapshot.child("price").getValue();
                adapter.add(key);
                skillkey.add(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key=snapshot.getKey();
                int index=skillkey.indexOf(key);
                if(index!=-1){
                    skill.remove(index);
                    skillkey.remove(index);
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

    private void setup(){
        List=findViewById(R.id.lvSkill);
        add=(Button) findViewById(R.id.btnadd);
        delete=(Button) findViewById(R.id.btndelete);
        ref= FirebaseDatabase.getInstance().getReference("Skill").child(FirebaseAuth.getInstance().getUid());
        ref2=FirebaseDatabase.getInstance().getReference("All Services and Providers");
        skill=new ArrayList<String>();
        skillkey=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(SkillActivity.this,android.R.layout.simple_list_item_single_choice,skill);
        List.setAdapter(adapter);
        List.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public void onBackPressed(){
        finish();
    }

}
