package com.kosam.carpool.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import com.pusher.pushnotifications.PushNotifications;

import com.kosam.carpool.R;

import java.util.Date;

public class CarpoolListAtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_list_ativity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PushNotifications.start(getApplicationContext(), "8c22fa07-fa3f-4a04-b7aa-a6fbcddb1711");
        PushNotifications.subscribe("hello");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Intent i=new Intent(this,CarpoolMakeActivity.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
        ListviewAdapter adapter=new ListviewAdapter();
        ListView listview=(ListView)findViewById(R.id.carpool_list);
        listview.setAdapter(adapter);
        adapter.addItem(new ListviewItem(new Date(12312),"부대","간부 아파트","정태훈",1,4));
    }

}
