package com.example.dragonspa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
  Context c;
  ArrayList<Treatment> arrT;

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
      if(convertView==null){
          convertView= LayoutInflater.from(c).inflate(R.layout.row,parent,false);

      }
        TextView main=convertView.findViewById(R.id.mainTitle);
      TextView sub=convertView.findViewById(R.id.subTitle);

      final  Treatment f=(Treatment)this.getItem(position);
      main.setText(f.getNameProduct());
      sub.setText(f.getDetail());
      return convertView;
    }
}
