package com.example.dragonspa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowDateMassage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
  //  Button search,gotoResult;
    //TextView displayDate;
    ListView displayDate;
    String idTreat ="";
    String nameT;
    String date;
    DatabaseReference Ref;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> keyList = new ArrayList<>();

    Typeface mTypeface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_foot_mas);
        // date = "11-11-1111";
       // search = findViewById(R.id.dateSearch);
        // displayDate = findViewById(R.id.textView);
          // gotoResult=findViewById(R.id.textView3);
        displayDate=findViewById(R.id.DateList);


        Intent i = getIntent();
        Bundle b =i.getExtras();
        if(b  != null) {
             idTreat = b.getString("idTreat");
             nameT = b.getString("treatName");
        }


        Ref=FirebaseDatabase.getInstance().getReference("treatments/times");


        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item=(TextView) super.getView(position, convertView, parent);

                item.setTypeface(item.getTypeface(), Typeface.BOLD);
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

                return item;
            }
        };
        displayDate.setAdapter(arrayAdapter);
        Ref=FirebaseDatabase.getInstance().getReference().child("treatments");
        Ref.getRoot().child("treatments").child(idTreat).child("times").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot ds :snapshot.getChildren()){
                        //אנחנו מציגים ללקוח רק את התאריכים העתידיים
                        String dateList=ds.getKey();
                        String [] dateArr =dateList.split("-");
                        if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<Integer.parseInt(dateArr[0])
                                && Calendar.getInstance().get(Calendar.MONTH)<=Integer.parseInt(dateArr[1])
                               &&Calendar.getInstance().get(Calendar.YEAR)<=Integer.parseInt(dateArr[2])
                        ){
                            arrayList.add(ds.getKey()); // מציג את התאריכים הקיימים
                            arrayAdapter.notifyDataSetChanged();
                        }



                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Ref.getRoot().child("treatments").child(idTreat).child("times")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot time : dataSnapshot.getChildren()) {

                            String dateList=time.getKey();
                            String [] dateArr =dateList.split("-");
                            Log.d("fgfgfg:", dateArr[2]);
                            if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<Integer.parseInt(dateArr[0])
                                    && Calendar.getInstance().get(Calendar.MONTH)<=Integer.parseInt(dateArr[1])
                                    &&Calendar.getInstance().get(Calendar.YEAR)<=Integer.parseInt(dateArr[2])
                            ) {
                                keyList.add(time.getKey());
//
                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        /*handle errors*/
                    }
                });
        displayDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String date=keyList.get(position);
                Intent i=new Intent(ShowDateMassage.this,Result.class);
                i.putExtra("date",date);
                i.putExtra("idTreat" , idTreat);
                i.putExtra("nameT",nameT);
                startActivity(i);
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
            //displayDate.setText(date);


        }



}