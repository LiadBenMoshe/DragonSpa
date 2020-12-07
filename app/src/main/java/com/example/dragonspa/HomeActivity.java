package com.example.dragonspa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Treatment> treats = new ArrayList<>();
    DatabaseReference Ref;
    FirebaseAuth mFirebaseAuth;
    ScrollView scrollView;
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    ImageButton profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profile=(ImageButton)findViewById(R.id.ActionButton);
        scrollView=(ScrollView)findViewById(R.id.scrollView);
       // mFirebaseAuth = FirebaseAuth.getInstance();

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

      Ref=FirebaseDatabase.getInstance().getReference().child("treatments");
      listView=(ListView) findViewById(R.id.ListView);
      arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
      listView.setAdapter(arrayAdapter);
      Ref.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              String value=snapshot.getValue(Treatment.class).toString();
              arrayList.add(value);
              arrayAdapter.notifyDataSetChanged();
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot snapshot) {
          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });




    }




}
