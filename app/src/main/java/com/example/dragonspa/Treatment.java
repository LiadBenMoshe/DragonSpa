package com.example.dragonspa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Treatment implements Serializable {
    public long price;
    public static long id;
   public String nameProduct;
   public String detail;
   public Map<String,ArrayList<String>> treatments;

    public Treatment(){}
    public Treatment(long price,String name,String details){
        this.detail=details;
        this.price=price;
        this.nameProduct=name;
         id+=1;
         this.treatments= new LinkedHashMap<String, ArrayList<String>>();
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
