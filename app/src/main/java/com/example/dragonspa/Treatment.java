package com.example.dragonspa;

import java.io.Serializable;

public class Treatment implements Serializable {
    public long price;
    public String nameProduct;
    public String detail;
    //public ArrayList<Integer> times;


    public Treatment(){}

    public Treatment(long price,String name,String details){
        this.detail=details;
        this.price=price;
        this.nameProduct=name;
        // times = new ArrayList<>();

    }
    public boolean equals(Treatment t){
        if(t.detail.equals(this.detail) && t.nameProduct.equals(this.nameProduct) && t.price == this.price){
            return true;
        }
        return false;
    }
    public String toString(){ return "סוג הטיפול:"+ nameProduct+'\n' +"תיאור:"+detail +'\n' +" מחיר:" +price;}
   public  String getNameProduct(){
        return  nameProduct;
   }
    public  String getDetail(){
        return  detail;
    }
    public  long getPrice(){
        return  price;
    }

}