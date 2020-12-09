package com.example.dragonspa;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class Appointment implements Serializable {

     public treatment treat;

    public static class treatment implements Serializable{
       public String name;
        public String date;
        public String userId;

        treatment(){ }
        treatment(String userId,String name,String date){
            this.name=name;
            this.date=date;
            this.userId=userId;
        }
    }


    Appointment(){ }



}
