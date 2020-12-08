package com.example.dragonspa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class SearchFootMas extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    AutoCompleteTextView search;
    TextView display,gotoResult;


    Calendar systemCalender;
    String date;
    DatabaseReference Ref;
    ValueEventListener valueEventListener;
   FirebaseDatabase mFire;


   ListView listView;
   ArrayList<String> arrayList=new ArrayList<>();
   ArrayAdapter <String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_foot_mas);

        search = findViewById(R.id.dateSearch);
        display = findViewById(R.id.textView);
        listView = findViewById(R.id.searchsList);
        gotoResult=findViewById(R.id.textView3);
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);



        Ref=FirebaseDatabase.getInstance().getReference("treatments/times");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showData();

            }
        });

      /* display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SearchFootMas.this,Result.class);
                i.putExtra("date",date);
               startActivity(i);
            }
        });*/
       gotoResult.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                   Intent i=new Intent(SearchFootMas.this,Result.class);
               i.putExtra("f",date);
                   startActivity(i);
               /* Ref=FirebaseDatabase.getInstance().getReference().child("treatments/times").child(date);
               Ref.getRoot().child("treatments").child("-MNxdhX4rS7Fb-be7ZFt").child("times").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
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
                         arrayAdapter.clear();
                         arrayList.clear();
                   }
               });*/

           }
       });



}



        private void showData () {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();

        }

        @Override
        public void onDateSet (DatePicker view,int year, int month, int dayOfMonth){
            date = dayOfMonth + "-" + (month + 1) + "-" + year;
            display.setText(date);


        }



}