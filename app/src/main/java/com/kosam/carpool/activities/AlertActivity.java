package com.kosam.carpool.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.kosam.carpool.R;

public class AlertActivity extends AppCompatActivity {
    private static ListView alertList;
    private static ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter=new ListviewAdapter();
        alertList=(ListView)findViewById(R.id.carpool_list);
        alertList.setAdapter(adapter);
}
}
