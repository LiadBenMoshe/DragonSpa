package com.example.dragonspa;

import androidx.annotation.NonNull;

public class Appointment {

    treatment treat;

    public class treatment{
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
