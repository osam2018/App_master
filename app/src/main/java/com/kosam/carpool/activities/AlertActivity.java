package com.kosam.carpool.activities;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.widget.ListView;

import com.kosam.carpool.R;
import com.kosam.carpool.activities.classGroup.AlertAdapter;
import com.kosam.carpool.activities.classGroup.CarpoolAdapter;

public class AlertActivity extends AppCompatActivity {
    private static ListView alertList;
    private static AlertAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter=new AlertAdapter();
        alertList=(ListView)findViewById(R.id.carpool_list);
        if(alertList!=null)
             alertList.setAdapter(adapter);

    }
}
