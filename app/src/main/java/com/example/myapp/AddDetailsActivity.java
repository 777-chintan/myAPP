package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDetailsActivity extends AppCompatActivity {

    private EditText UserName,UserAge;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String name,age,phone,usertype,id;
    private Button save;
    private boolean radiocheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        setup();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    sendUserData();
                }
            }
        });

    }

    private void setup(){
        UserName=findViewById(R.id.etUSerName);
        UserAge=findViewById(R.id.etAge);
        radioGroup=findViewById(R.id.rgroup);
        save=findViewById(R.id.btnSave);
        radiocheck=false;
    }

    private boolean validate(){
        name=UserName.getText().toString();
        age=UserAge.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(AddDetailsActivity.this,"Enter Your Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(age.isEmpty() || age=="0"){
            Toast.makeText(AddDetailsActivity.this,"Enter Your Age",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(radiocheck==false){
            Toast.makeText(AddDetailsActivity.this,"Select Usertype",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void rbclick(View view) {
        radiocheck=((RadioButton) view).isChecked();
        int radiobuttonid= radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radiobuttonid);
        usertype=radioButton.getText().toString();
        Toast.makeText(AddDetailsActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    private void sendUserData(){
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("User");
        String id= FirebaseAuth.getInstance().getUid();
        phone=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        UserProfile userprofile=new UserProfile(name,age,phone,usertype,id);
        myRef.child(id).setValue(userprofile);
        if(usertype=="Custormer"){
            startActivity(new Intent(AddDetailsActivity.this,WelcomeActivity.class));
            finish();
        }
        if(usertype=="Service Provider"){
            startActivity(new Intent(AddDetailsActivity.this,WelcomeActivity.class));
            finish();
        }
    }

}
