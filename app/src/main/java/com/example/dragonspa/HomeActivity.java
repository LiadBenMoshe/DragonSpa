package com.example.dragonspa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
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

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    ImageButton profile;
    String value;

    ////////
    DatabaseReference db;
    FirebaseHelper helper;
    CustomListAdapter adapter;
    ListView LView;


    public class FirebaseHelper {
        DatabaseReference db;
        ArrayList<Treatment> arrT = new ArrayList<>();
        Context c;
        ListView listView2;

        public FirebaseHelper(DatabaseReference db, Context con, ListView mList) {
            this.db = db;
            this.c = con;
            this.listView2 = mList;
            this.retrieve();
        }

        public ArrayList<Treatment> retrieve() {
            db.child("treatments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrT.clear();
                    if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Treatment t = ds.getValue(Treatment.class);
                            arrT.add(t);
                        }
                        adapter = new CustomListAdapter(c, arrT);
                        listView2.setAdapter(adapter);
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                listView2.smoothScrollToPosition(arrT.size());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return arrT;
        }

    }
@Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    LView=(ListView)findViewById(R.id.listviewtreat);
    db=FirebaseDatabase.getInstance().getReference();
    helper=new FirebaseHelper(db, this,LView);

    profile = (ImageButton) findViewById(R.id.ActionButton);
    profile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        }
    });




}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acticity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.conectus:
                startActivity(new Intent(HomeActivity.this, ConnectUs.class));
                return true;
            case R.id.help:
                Toast.makeText(this, "Belive in Yourself, that`s the only help", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ///////
/*
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
*/
    //     @Override
    //   public void onCancelled(DatabaseError databaseError) {
    /*handle errors*/
    // }
    // });

       /*
        Ref = FirebaseDatabase.getInstance().getReference().child("treatments");
        listView = (ListView) findViewById(R.id.listviewtreat);
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

        */
    /////////////////

    ///////////////////

    }
