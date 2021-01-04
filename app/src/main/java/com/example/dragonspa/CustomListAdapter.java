package com.example.dragonspa;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
  Context c;
  ArrayList<Treatment> arrT;
  DatabaseReference Ref;

  public CustomListAdapter(Context c, ArrayList<Treatment> arrt){
this.c=c;
this.arrT=arrt;
  }


    @Override
    public int getCount() {
        return arrT.size();
    }

    @Override
    public Object getItem(int position) {
        return arrT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ArrayList<String> keyList = new ArrayList<>();
      Ref = FirebaseDatabase.getInstance().getReference().child("treatments");
      Ref.getRoot().child("treatments")
              .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  for (DataSnapshot treat : dataSnapshot.getChildren()) {
                    keyList.add(treat.getKey());
                    Log.d("key", treat.getKey());

                  }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

         });




      if(convertView==null){
          convertView= LayoutInflater.from(c).inflate(R.layout.row,parent,false);

      }
        TextView main=convertView.findViewById(R.id.mainTitle);
      TextView sub=convertView.findViewById(R.id.subTitle);
      TextView price=convertView.findViewById(R.id.price);
      Button searchDate=convertView.findViewById(R.id.searchDate);


      final  Treatment f=(Treatment)this.getItem(position);
      main.setText(f.getNameProduct());
      sub.setText(f.getDetail());
      price.setText(f.getPrice()+"$");
      searchDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String idTreat= keyList.get(position);
          Intent i=new Intent(c,ShowDateMassage.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.putExtra("idTreat", idTreat);
          i.putExtra("treatName",f.getNameProduct());
          Log.d("check" , idTreat );
          c.startActivity(i);
        }
      });



      return convertView;
    }
    private static class ViewHAolder{}
    
}
