package com.example.dragonspa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomListAdapterM extends BaseAdapter {
    Context c;
    ArrayList<Treatment> arrT;
    DatabaseReference Ref;
    Calendar systemCalender;
    int year1;
    int month1;
    int  day1;
    int hour1;
    int minutes1;


    public CustomListAdapterM(Context c, ArrayList<Treatment> arrt){
        this.c=c;
        this.arrT=arrt;
    }


    @Override
    public int getCount() {
        return arrT.size();
    }

    @Override
    public Object getItem(int position) {
        return arrT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArrayList<String> keyList = new ArrayList<>();
        Ref = FirebaseDatabase.getInstance().getReference().child("treatments");
        Ref.getRoot().child("treatments")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot treat : dataSnapshot.getChildren()) {
                            keyList.add(treat.getKey());
                            Log.d("key", treat.getKey());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });




        if(convertView==null){
            convertView= LayoutInflater.from(c).inflate(R.layout.row_m,parent,false);

        }
        TextView main=convertView.findViewById(R.id.mainTitle);
        TextView sub=convertView.findViewById(R.id.subTitle);
        TextView price=convertView.findViewById(R.id.price);
        Button searchDate=convertView.findViewById(R.id.addDate);


        final  Treatment f=(Treatment)this.getItem(position);
        main.setText(f.getNameProduct());
        sub.setText(f.getDetail());
        price.setText(f.getPrice() + "$");
        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTreat= keyList.get(position);


                systemCalender=Calendar.getInstance();
                int year = systemCalender.get(Calendar.YEAR);
                int month = systemCalender.get(Calendar.MONTH);
                int day = systemCalender.get(Calendar.DAY_OF_MONTH);
                TimePickerDialog timePickerDialog = new TimePickerDialog(c, new TimePickerDialog.OnTimeSetListener() {
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
                        Ref =  Ref.child("treatments").child(keyList.get(position)).child("times");
                        Ref.child(s).child(s2).setValue("time");
                        Toast.makeText(c, "added successfully", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(c,product_adding.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        c.startActivity(i);


                    }
                },hour1,minutes1,true);
                timePickerDialog.show();

                DatePickerDialog datePickerDialog=new DatePickerDialog(c, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        year1 = year;
                        month1 = month+1;
                        day1 = dayOfMonth;
                    }
                },year , month, day);
                datePickerDialog.show();

            }
        });
        main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
                builder1.setMessage("are you sure you want to delete this item?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Ref  = Ref.getRoot();
                                Ref.child("treatments").child(keyList.get(position)).removeValue();
                                keyList.remove(position);
                                arrT.remove(position);
                             //   arrayAdapter.notifyDataSetChanged();
                                dialog.cancel();
                                Toast.makeText(c, "deleted", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(c,product_adding.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                c.startActivity(i);
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



        return convertView;
    }
    private static class ViewHAolder{}

}

