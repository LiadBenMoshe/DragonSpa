package com.example.dragonspa;

public class Client {
    public String name;
    public String phoneNumber;
    public String isManager;
    

    public Client()
    {
    }
    public Client(String name, String phoneNumber)
    {
        this.name = name;
        this.phoneNumber=phoneNumber;
         this.isManager = "1";


    }
    public String getName(){
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getIsManager(){
        return isManager;
    }
}
