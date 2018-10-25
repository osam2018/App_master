package com.kosam.carpool.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kosam.carpool.R;

public class UnitInfoActivity extends AppCompatActivity {
    Menu mMenu;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.unit_info_toolbar);
        setSupportActionBar(toolbar);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.unit_info_menu, menu);
        mMenu=menu;
        return true;
    }
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (mMenu != null) {
            MenuItem SignOut = mMenu.findItem(R.id.menu_unit_info_sign_out);
            MenuItem NameChange = mMenu.findItem(R.id.menu_unit_info_name_change);
            MenuItem DisPose = mMenu.findItem(R.id.menu_unit_info_dispose);



        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.menu_unit_info_sign_out:
                //부대 탈퇴
                return true;
            case R.id.menu_unit_info_name_change:
                //부대 이름 변경
                return true;
            case R.id.menu_unit_info_dispose:
                //부대 삭제

                return true;

            default:

        }

        return super.onOptionsItemSelected(item);
    }

}
