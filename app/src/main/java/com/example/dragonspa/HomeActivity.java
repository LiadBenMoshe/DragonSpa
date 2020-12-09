package com.example.dragonspa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Treatment> treats = new ArrayList<>();
    DatabaseReference Ref;
    DatabaseReference Reff;

    Intent i;
    FirebaseDatabase database;
    FirebaseAuth mFirebaseAuth;
    ScrollView scrollView;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    ImageButton profile;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profile = (ImageButton) findViewById(R.id.ActionButton);
        // mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Reff = database.getReference().child("treatments");

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });
        ArrayList<String> keyList = new ArrayList<>();
        Ref = FirebaseDatabase.getInstance().getReference().child("treatments");
        Ref.getRoot().child("treatments")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot treat : dataSnapshot.getChildren()) {
                            keyList.add(treat.getKey());
                            Log.d("key", treat.getKey());
                            // items.add(treat.getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        /*handle errors*/
                    }
                });


        Ref = FirebaseDatabase.getInstance().getReference().child("treatments");
        listView = (ListView) findViewById(R.id.listviewtxt111);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        Ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(Treatment.class).toString();
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idTreat = keyList.get(position);
                i = new Intent(HomeActivity.this, SearchFootMas.class);
                i.putExtra("idTreat", idTreat);
                Ref.getRoot();
                Ref = Ref.child("treatments");
                Log.d("check" , idTreat );

                Reff.child(idTreat).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Log.d("cccc" , snapshot.getValue(Treatment.class).getNameProduct());
                            Log.d("check" , snapshot.toString() );

                            value = snapshot.getValue(Treatment.class).getNameProduct();
                            i.putExtra("nameT", value);
                            startActivity(i);
                        }
                        Log.d("checkfail" , snapshot.toString() );


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


            }


        });
    }
}