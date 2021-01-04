package com.example.dragonspa;


import java.io.Serializable;

public class Appointment implements Serializable {


       public String name;
        public String date;
        public String userId;
        public  String userEmail;

        public String treatID;



    Appointment(){}
    Appointment(String userId,String name,String date,String userEmail, String treatID){

            this.name=name;
            this.date=date;
            this.userId=userId;
            this.userEmail = userEmail;
            this.treatID=treatID;

        }
        public String toString(){
            return date + "\n " + userEmail;
        }
        public String getDate(){
        return date;
        }
    public String getUserId(){
        return userId;
    }
    public String getUserName(){
        return userEmail;
    }
    public String getName(){
        return name;
    }
    public String getTreatID(){return treatID;}

    }




