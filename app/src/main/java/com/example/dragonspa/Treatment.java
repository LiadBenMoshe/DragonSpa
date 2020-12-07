package com.example.dragonspa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Treatment implements Serializable {
    public long price;
   // public static long id;
   public String nameProduct;
   public String detail;
   public ArrayList<Date> times;

    public Treatment(){}

    public Treatment(long price,String name,String details){
        this.detail=details;
        this.price=price;
        this.nameProduct=name;
        times = new ArrayList<>();
         //id+=1;
    }
    public boolean equals(Treatment t){
        if(t.detail.equals(this.detail) && t.nameProduct.equals(this.nameProduct) && t.price == this.price){
            return true;
        }
        return false;
    }
    public String toString(){ return nameProduct +"  |  "+price;}

}
