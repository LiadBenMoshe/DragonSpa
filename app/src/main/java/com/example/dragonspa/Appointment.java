package com.example.dragonspa;


import java.io.Serializable;

public class Appointment implements Serializable {


       public String name;
        public String date;
        public String userId;
        public  String userEmail;


    Appointment(){}
    Appointment(String userId,String name,String date,String userEmail){

            this.name=name;
            this.date=date;
            this.userId=userId;
            this.userEmail = userEmail;
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
    }




