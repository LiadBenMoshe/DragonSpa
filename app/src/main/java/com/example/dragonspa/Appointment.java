package com.example.dragonspa;

public class Appointment {

   public treatment treat;

    public class treatment{
       public String name;
       public String date;
      public   String user;

        treatment(){ }
        treatment(String name,String date,String user){
            this.name=name;
            this.date=date;
            this.user=user;
        }
        public String toString(){
            return date + " | " + name;
        }
    }


    Appointment(){ }
    public String toString(){
        return this.treat.toString();
    }


}
