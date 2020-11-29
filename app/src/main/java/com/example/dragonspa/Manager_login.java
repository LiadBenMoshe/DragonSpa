package com.example.dragonspa;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Manager_login extends AppCompatActivity {
    private EditText managerEmail, managerPassword;
    private Button login;
    FirebaseDatabase mDatabase;
    DatabaseReference dbRootRef;
    private FirebaseUser manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         FirebaseAuth fireBaseAuth = FirebaseAuth.getInstance();
        FirebaseUser manager = fireBaseAuth.getCurrentUser();
//        if(manager != null){
//            finish();
//            startActivity(new Intent(Manager_login.this,MainActivity.class));
//        }

 //       DatabaseReference.CompletionListener completionListener = new
//                DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
//                    {
//                        if (databaseError != null)
//                        {
//                            Toast.makeText(Manager_login.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(Manager_login.this,"Saved!!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
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
                                Toast.makeText(Manager_login.this,"sign in successful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Manager_login.this,manager_main_menu.class));
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