package com.example.dragonspa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Set;

public class SearchFootMas extends AppCompatActivity {
    AutoCompleteTextView search;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_foot_mas);

        search=findViewById(R.id.dateSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar systemCalender=Calendar.getInstance();
                int year=systemCalender.get(Calendar.YEAR);
                int month=systemCalender.get(Calendar.MONTH);
                int day=systemCalender.get(Calendar.DAY_OF_MONTH);
               datePickerDialog=new DatePickerDialog(SearchFootMas.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                      // search.setText(day + "/" + month + "/" + year);
                       search.setText(day + "/" + (month+1) +"/" +year);
                   }
               },year , month, day);
               datePickerDialog.show();
            }
        });

    }
}