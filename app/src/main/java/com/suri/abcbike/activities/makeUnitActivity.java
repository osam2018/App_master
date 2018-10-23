package com.suri.abcbike.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.suri.abcbike.R;


public class makeUnitActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter sAdapter;
    String[] item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_unit);

        item=getResources().getStringArray(R.array.unit_head);
        spinner = (Spinner) findViewById(R.id.unit_category);
        sAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,item);
        spinner.setAdapter(sAdapter);


    }
}
