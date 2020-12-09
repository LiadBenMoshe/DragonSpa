package com.example.dragonspa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    TextView tv ;
    DatabaseReference mRef;
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseDatabase mDatabase;

    ArrayList<String> keyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv=findViewById(R.id.textView2);
        //tv1=findViewById(R.id.textView4);
        Intent i=getIntent();
    String date=i.getExtras().getString("f");
        String time=i.getExtras().getString("time");
        tv.setText("f:"+date+"time:"+time);
        listView=findViewById(R.id.searchsList);
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        mRef=FirebaseDatabase.getInstance().getReference().child("treatments/times").child(date);
        mRef.getRoot().child("treatments").child("-MNxdhX4rS7Fb-be7ZFt").child("times").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds :snapshot.getChildren()){

                        arrayList.add(ds.getKey());
                        arrayAdapter.notifyDataSetChanged();


                    }
                }
                else{
                    arrayList.add("אין תוצאות מתאימות");
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mRef.getRoot().child("treatments").child("-MNxdhX4rS7Fb-be7ZFt").child("times").child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot time : dataSnapshot.getChildren()) {
                            keyList.add(time.getKey());

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        /*handle errors*/
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void  onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Result.this);
                builder1.setMessage("זו השעה הנוחה לך?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "כן",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mRef  = mRef.getRoot();
                                mRef.child("treatments").child("-MNxdhX4rS7Fb-be7ZFt").child("times").child(date).child(keyList.get(pos)).removeValue();

                                mRef = FirebaseDatabase.getInstance().getReference().child("appointments");
                                Appointment.treatment app=new Appointment.treatment("מסאז' רגליים",keyList.get(pos));
                                String key=mRef.push().getKey();

                                mRef.child(key).setValue(app);
                                arrayList.remove(pos);
                                arrayAdapter.notifyDataSetChanged();
                                dialog.cancel();
                                Toast.makeText(Result.this, "התור שמור לך", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Result.this, Result.class));
                            }
                        });

                builder1.setNegativeButton(
                        "לא",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                //return true;
            }
        });




    }
}