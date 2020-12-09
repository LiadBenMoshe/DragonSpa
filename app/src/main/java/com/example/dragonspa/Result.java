package com.example.dragonspa;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    TextView tv,tv5 ;
    DatabaseReference mRef;
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth firebaseAuth;

    ArrayList<String> keyList = new ArrayList<>();
String date;
String nameT;
String idTreat;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        firebaseAuth= FirebaseAuth.getInstance();
tv5=findViewById(R.id.textView5);
        tv=findViewById(R.id.textView2);
        //tv1=findViewById(R.id.textView4);
        Intent i=getIntent();
        Bundle b = i.getExtras();
        if(b != null) {
             date = i.getExtras().getString("f");
             idTreat = i.getExtras().getString("idTreat");
             nameT = i.getExtras().getString("nameT");
        }
        listView=findViewById(R.id.searchsList);
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        mRef=FirebaseDatabase.getInstance().getReference().child("treatments/times").child(date);
        mRef.getRoot().child("treatments").child(idTreat).child("times").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
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
        mRef.getRoot().child("treatments").child(idTreat).child("times").child(date)
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
                            public synchronized void onClick(DialogInterface dialog, int id) {
                                mRef  = mRef.getRoot();
                                mRef.child("treatments").child(idTreat).child("times").child(date).child(keyList.get(pos)).removeValue();
                                mRef = FirebaseDatabase.getInstance().getReference().child("appointments");
                                String str = firebaseAuth.getCurrentUser().getUid();
                                mRef = mRef.getRoot();
                                     mRef   = mRef.child("clients").child(str);
                                     mRef.addValueEventListener(new ValueEventListener() {
                                         @Override
                                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                                             try {
                                                 if (snapshot.getValue(Client.class) != null) {
                                                     try {
                                                         Client c = snapshot.getValue(Client.class);
                                                         userName = c.name;
                                                        // String n = snapshot.child("nameProduct").getValue(String.class).toString();
                                                       //  tv5.setText(n);
                                                        // Log.d("use", userName + n);
                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                     }
                                                 }else{
                                                     Log.d("use", userName );}

                                                 }
                                             catch (Exception e){
                                                 e.printStackTrace();
                                             }

                                         }

                                         @Override
                                         public void onCancelled(@NonNull DatabaseError error) {

                                         }
                                     });

                                     String uid = firebaseAuth.getCurrentUser().getUid();
                                Appointment app=new Appointment(uid,nameT,date+"  "+keyList.get(pos), userName);
                                String key = mRef.push().getKey();
                                mRef.getRoot();
                                mRef = FirebaseDatabase.getInstance().getReference().child("appointments");

                                mRef.child(key).setValue(app);
                                arrayList.remove(pos);
                                arrayAdapter.notifyDataSetChanged();
                                dialog.cancel();
                                mRef.getRoot();
                                mRef = FirebaseDatabase.getInstance().getReference().child("clients").child(uid).child("times");
                                mRef.child(date).child(keyList.get(pos)).setValue("time");


                                Toast.makeText(Result.this, "התור שמור לך", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Result.this, SearchFootMas.class));
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