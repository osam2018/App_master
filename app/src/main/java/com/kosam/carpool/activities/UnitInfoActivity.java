package com.kosam.carpool.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kosam.carpool.R;

public class UnitInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.unit_info_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.unit_info_menu, menu);
        return true;
    }

    //@Override
    /*public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.action_sign_out:

                mPreferences.edit().clear().commit();
                Intent goIntro = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(goIntro);
                finish();

                return true;

            default:

        }

        return super.onOptionsItemSelected(item);
    }
*/
}
