package com.example.dragonspa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SearchFootMas extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button search,gotoResult;
    TextView displayDate;
    String idTreat ="";
    String nameT;
    String date;
    DatabaseReference Ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_foot_mas);
         date = "11-11-1111";
        search = findViewById(R.id.dateSearch);
        displayDate = findViewById(R.id.textView);

        gotoResult=findViewById(R.id.textView3);
        Intent i = getIntent();
        Bundle b =i.getExtras();
        if(b  != null) {
             idTreat = b.getString("idTreat");
             nameT = b.getString("nameT");
        }


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
               i.putExtra("idTreat" , idTreat);
               i.putExtra("nameT",nameT);
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
            displayDate.setText(date);


        }



}