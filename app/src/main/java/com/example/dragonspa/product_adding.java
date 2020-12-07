package com.example.dragonspa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class product_adding extends AppCompatActivity {
    ArrayList<Treatment> treats = new ArrayList<>();
    private FloatingActionButton add;
    DatabaseReference Ref;
    ListView listView;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Calendar systemCalender;
    int year1;
    int month1;
    int  day1;
    int hour1;
    int minutes1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_adding);
        setupUIviews();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(product_adding.this, product_adding2.class));
            }
        });
        Ref= FirebaseDatabase.getInstance().getReference().child("treatments");
        listView=(ListView)findViewById(R.id.listviewtxt);
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

         ArrayList<String> keyList = new ArrayList<>();
         ArrayList<String> items = new ArrayList<>();
         Ref.getRoot().child("treatments")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot treat : dataSnapshot.getChildren()) {
                            keyList.add(treat.getKey());
                            Log.d("key" , treat.getKey());
                           // items.add(treat.getValue(String.class));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        /*handle errors*/
                    }
                });


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
           public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                systemCalender=Calendar.getInstance();
               TimePickerDialog timePickerDialog = new TimePickerDialog(product_adding.this, new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       hour1  = hourOfDay;
                       minutes1 = minute;

                       String s = day1+"-"+month1+"-"+year1;
                       String s2 = "";
                       s2 = hour1+":"+minutes1;
                       if(hour1 < 10) {
                           s2 = "0" + hour1 + ":" + minutes1;
                       }
                       if((minutes1 < 10)) {
                           s2 = hour1 + ":0" + minutes1;
                       }
                       if(hour1 < 10 && minutes1 < 10){
                           s2 = "0" + hour1 + ":0" + minutes1;
                       }
                       Ref  = Ref.getRoot();
                       Ref =  Ref.child("treatments").child(keyList.get(pos)).child("times");
                       Ref.child(s).child(s2).setValue("time");
                       Toast.makeText(product_adding.this, "added successfully", Toast.LENGTH_LONG).show();

                   }
               },hour1,minutes1,true);
               timePickerDialog.show();

              DatePickerDialog datePickerDialog=new DatePickerDialog(product_adding.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        year1 = year;
                        month1 = month+1;
                        day1 = dayOfMonth;
                   }
               },year1 , month1, day1);
               datePickerDialog.show();

           }
       });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

             AlertDialog.Builder builder1 = new AlertDialog.Builder(product_adding.this);
             builder1.setMessage("are you sure you want to delete this item?");
             builder1.setCancelable(true);
             builder1.setPositiveButton(
                     "Yes",
                     new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             Ref  = Ref.getRoot();
                             Ref.child("treatments").child(keyList.get(pos)).removeValue();
                             keyList.remove(pos);
                             arrayList.remove(pos);
                             arrayAdapter.notifyDataSetChanged();
                             dialog.cancel();
                             Toast.makeText(product_adding.this, "deleted", Toast.LENGTH_LONG).show();
                             startActivity(new Intent(product_adding.this, product_adding.class));
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

    private void setupUIviews() {
        add = (FloatingActionButton) findViewById(R.id.addPbutton);
    }
}

