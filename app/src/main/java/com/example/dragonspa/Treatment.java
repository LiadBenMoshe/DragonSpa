package com.example.dragonspa;

import java.io.Serializable;

public class Treatment implements Serializable {
    public long price;
    public long id;
   public String nameProduct;
   public String detail;

    public Treatment(){}
    public Treatment(long price,String name,String details,long id){
        this.detail=details;
        this.price=price;
        this.nameProduct=name;
        this.id=id;
    }
    public String toString(){
        return this.id+":"+nameProduct +"-"+price;
    }
}
