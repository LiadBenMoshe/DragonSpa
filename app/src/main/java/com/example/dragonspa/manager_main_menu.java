package com.example.dragonspa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class manager_main_menu extends AppCompatActivity {
    private TextView addProduct, editAppointments, stock;
    FirebaseDatabase mDatabase;
    DatabaseReference dbRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main_menu);
        setupUIviews();
        addProduct.setOnClickListener(new View.OnClickListener() {
           //need to add correct target activity to each button
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manager_main_menu.this,product_adding.class));
            }
        });
        editAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manager_main_menu.this,appointments_editing.class));
            }
        });
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manager_main_menu.this,stock_view.class));
            }
        });
    }
    private void setupUIviews() {
        addProduct = (TextView) findViewById(R.id.addProduct);
        editAppointments= (TextView) findViewById(R.id.editAppointments);
        stock = (TextView) findViewById(R.id.stock);
    }
}