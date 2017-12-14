package com.example.shivani.aspire1;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{
    
    String title;
    String description;
    ArrayList<Content> list;
    Context context;
    LayoutInflater inflater;
    public CustomAdapter(FragmentManager supportFragmentManager) {
    }

    public CustomAdapter(Context context, ArrayList<Content> list){
        this.context=context;
        this.list=list;
        
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        TextView text1;
        TextView text2;
        String tit;
        String des;
        if(inflater==null)
        {
            inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.row,parent,false);
        }
        
    
        text1=(TextView)convertView.findViewById(R.id.text1);
        text2=(TextView)convertView.findViewById(R.id.text2);
        
         tit=list.get(position).getTitle();
         des=list.get(position).getDescription();
        text1.setText(tit);
        text2.setText(des);
     return convertView;   
    }
}
