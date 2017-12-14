package com.example.shivani.aspire1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class TabTwo extends Fragment {
    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2,radioButton3;
    private CheckedTextView checkedTextView;
    private CheckBox checkBox;

    
    public TabTwo() {
        // Required empty public constructor
    }

   
    public static TabTwo newInstance(String param1, String param2) {
        TabTwo fragment = new TabTwo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab_two, container, false);
        return view;
    }


   
}