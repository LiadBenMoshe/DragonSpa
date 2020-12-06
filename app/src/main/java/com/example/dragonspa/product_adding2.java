package com.example.dragonspa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class product_adding2 extends AppCompatActivity {
    private EditText productName,price,details;
    private Button add;
    FirebaseDatabase mDatabase;
    DatabaseReference dbRootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_adding2);
        setupUIviews();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                                Toast.makeText(product_adding2.this,"add successful",Toast.LENGTH_LONG).show();
                                mDatabase = FirebaseDatabase.getInstance();
                                dbRootRef = mDatabase.getReference().child("treatments");
                                Treatment treatment = new Treatment(Long.parseLong(price.getText().toString()),productName.getText().toString(),details.getText().toString());
                                String key = dbRootRef.push().getKey();
                                dbRootRef.child(key).setValue(treatment);
                                startActivity(new Intent(product_adding2.this,product_adding.class));
                            }else{
                                Toast.makeText(product_adding2.this,"register failed",Toast.LENGTH_LONG).show();
                            }
                }

        });
    }

    private void setupUIviews() {
        productName = (EditText) findViewById(R.id.productName);
        price = (EditText) findViewById(R.id.price);
        details = (EditText) findViewById(R.id.detailes);
        add = (Button) findViewById(R.id.addThePbutton);
    }

    private Boolean validate() {
        Boolean ans = false;
        String name = productName.getText().toString();
        String Price = price.getText().toString();

        if (name.isEmpty() || Price.isEmpty()) {
            Toast.makeText(product_adding2.this, "please enter name and price", Toast.LENGTH_LONG).show();
        } else {
            ans = true;
        }
        return ans;
    }
}
