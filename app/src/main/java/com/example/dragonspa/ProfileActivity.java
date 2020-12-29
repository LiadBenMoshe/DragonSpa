package com.example.dragonspa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<String> stringArrayList=new ArrayList<>();
    TextView ProfileName;
    ListView list;
    DatabaseReference Ref;
    String value;
    String date;
    String name;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileName=findViewById(R.id.textName);
        list=(ListView)findViewById(R.id.ListTreat);



        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("appointments");
        DatabaseReference uidRef2=rootRef.child("clients").child(uid);
        uidRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name =snapshot.child("name").getValue().toString();
                ProfileName.setText(" שלום "+name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
       uidRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               for (DataSnapshot ds : snapshot.getChildren()) {

                    if(ds.getValue().equals(uid)) {
                        String registerEventName ;
                        String nameT=snapshot.child("treatName").getValue().toString();

                        registerEventName=nameT+"  "+snapshot.child("date").getValue().toString();
                        stringArrayList.add(registerEventName);
                        adapter.notifyDataSetChanged();
                    }



               }
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               adapter.notifyDataSetChanged();
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

        list.setAdapter(adapter);

        ArrayList<String> keyList = new ArrayList<>();
        uidRef.getRoot().child("appointments")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot treat : dataSnapshot.getChildren()) {
                            if(treat.child("userId").getValue().equals(uid)) {
                                keyList.add(treat.getKey());
                                Log.d("key", treat.getKey());
                                // items.add(treat.getValue(String.class));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        /*handle errors*/
                    }
                });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileActivity.this);
                builder1.setMessage("are you sure you want to delete this Treatment?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Ref =FirebaseDatabase.getInstance().getReference();
                                Ref = Ref.getRoot();
                                Ref.child("appointments").child(keyList.get(pos)).addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
//                                            Log.d("cccc" , snapshot.getValue(Treatment.class).getNameProduct());
//                                            Log.d("check" , snapshot.toString() );
                                            String treat=snapshot.child("treatID").getValue().toString();
                                            Log.d("checkkkk" , snapshot.toString() );
                                            date = snapshot.child("date").getValue().toString();

                                            userName = snapshot.child("userName").getValue().toString();

                                            value = snapshot.child("userId").getValue().toString();
                                            Log.d("dateee" , "++"+date);
                                            String[] arr = date.split("  ");

                                            Log.d("arrCheck" , arr[0]);
                                            Ref  = Ref.getRoot();
                                            Ref.child("clients").child(value).child("times").child(arr[0]).child(arr[1]).removeValue();

                                            Ref  = FirebaseDatabase.getInstance().getReference().child("treatments");
                                            Ref.child(treat).child("times").child(arr[0]).child(arr[1]).setValue("time");




                                        }
                                        //  Log.d("checkfail" , snapshot.toString() );

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }

                                });

                                Ref  = Ref.getRoot();
                                Ref.child("appointments").child(keyList.get(pos)).removeValue();
                                keyList.remove(pos);
                                stringArrayList.remove(pos);
                                adapter.notifyDataSetChanged();
                                dialog.cancel();
                                Toast.makeText(ProfileActivity.this, "deleted", Toast.LENGTH_LONG).show();

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });

        }




    }

