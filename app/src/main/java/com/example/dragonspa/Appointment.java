package com.example.dragonspa;


import java.io.Serializable;

public class Appointment implements Serializable {


       public String name;
        public String date;
        public String userId;
        public  String userName;


    Appointment(){}
    Appointment(String userId,String name,String date,String userName){

            this.name=name;
            this.date=date;
            this.userId=userId;
            this.userName = userName;
        }
        public String toString(){
            return date + " " + userName;
        }
        public String getDate(){
        return date;
        }
    public String getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public String getName(){
        return name;
    }
    }




