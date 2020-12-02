package com.example.dragonspa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Manager_login extends AppCompatActivity {
    private EditText managerEmail, managerPassword;
    private Button login;
    FirebaseDatabase mDatabase;
    DatabaseReference dbRootRef;
    private FirebaseUser manager;
    private String mngID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth fireBaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);
        setupUIviews();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String mng_email = managerEmail.getText().toString().trim();
                    String mng_password = managerPassword.getText().toString().trim();
                    fireBaseAuth.signInWithEmailAndPassword(mng_email,mng_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mDatabase = FirebaseDatabase.getInstance();
                                FirebaseUser manager = fireBaseAuth.getCurrentUser();
                                dbRootRef = mDatabase.getReference().child("clients").child(manager.getUid());
                                mngID = manager.getUid();
                                Log.d("mngID", mngID);
                                dbRootRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        boolean b = false;
                                       // String isManager = snapshot.child(mngID).child("isManager").getValue(String.class);
                                        Client c = snapshot.getValue(Client.class);
                                        String s = c.isManager;
                                        if (s.equals("00")) {
                                            b = true;
                                        }
                                        if (b == true) {
                                            Toast.makeText(Manager_login.this, "sign in successful", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(Manager_login.this, manager_main_menu.class));
                                        } else {
                                            Toast.makeText(Manager_login.this, "sign in failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.w( "loadPost:onCancelled", error.toException());
                                    }
                                });
                            }else{
                                Toast.makeText(Manager_login.this,"sign in failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void setupUIviews() {
        managerEmail = (EditText) findViewById(R.id.managerEmail);
        managerPassword = (EditText) findViewById(R.id.managerPass);
        login = (Button) findViewById(R.id.managerLoginButton);
    }

    private Boolean validate() {
        Boolean ans = false;
        String Email = managerEmail.getText().toString();
        String Password = managerPassword.getText().toString();

        if (Email.isEmpty() || Password.isEmpty()) {
            Toast.makeText(this, "please enter all the details", Toast.LENGTH_LONG).show();
        } else {
            ans = true;
        }
        return ans;
    }
}