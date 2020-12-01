package com.example.dragonspa;

public class Treatment {
    int price;
    int id;
    String nameProduct;
    String detail;

    public Treatment(){}
    public Treatment(int price,String name,String details,int id){
        this.detail=details;
        this.price=price;
        this.nameProduct=name;
        this.id=id;
    }
}
