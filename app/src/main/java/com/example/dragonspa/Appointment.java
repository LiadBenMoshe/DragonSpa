package com.example.dragonspa;

import java.io.Serializable;

public class Appointment implements Serializable {

    treatment treat;

    public static class treatment{
        String name;
        String date;

        treatment(){ }
        treatment(String name,String date){
            this.name=name;
            this.date=date;
        }
    }


    Appointment(){ }



}
