package com.example.dragonspa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Treatment> treats = new ArrayList<>();
    DatabaseReference Ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


      Ref=FirebaseDatabase.getInstance().getReference().child("treatments");
      Ref.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              collectDetails((Map<String,Object>) snapshot.getValue());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Log.w( "loadPost:onCancelled", error.toException());
          }
      });



    }
    public void collectDetails(Map<String,Object> users) {

        ArrayList<Treatment> treats = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            treats.add(new Treatment((int)singleUser.get("price"),(String)singleUser.get("nameProduct"),(String)singleUser.get("detail"),(int)singleUser.get("id")));
        }


    }




}
