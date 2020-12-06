package com.example.dragonspa;

import java.io.Serializable;

public class Treatment implements Serializable {
    public long price;
    public static long id;
   public String nameProduct;
   public String detail;

    public Treatment(){}
    public Treatment(long price,String name,String details){
        this.detail=details;
        this.price=price;
        this.nameProduct=name;
         id+=1;
    }
    public boolean equals(Treatment t){
        if(t.detail.equals(this.detail) && t.nameProduct.equals(this.nameProduct) && t.price == this.price){
            return true;
        }
        return false;
    }
    public String toString(){
        return this.id+":"+nameProduct +"-"+price;
    }
}
