package com.kosam.carpool.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kosam.carpool.R;

import java.util.Date;

// , OnMapReadyCallback
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected static final String TAG = MainActivity.class.getSimpleName();

    public static MainActivity thisActivity;
    private SharedPreferences mPreferences;
    private View mLayout;
    //navigation view 연결
    private NavigationView navigationView;
    private TextView NavUserName;
    private TextView NavUserTopUnit;
    private TextView NavUserUnitName;
    private TextView NavUserEmail;
    //ui참조
    ListviewAdapter adapter;
    ListView listview;
    FloatingActionButton carpoolMakeFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //컨텍스트 참조
        thisActivity=this;

        //ui연결
        listview=(ListView)findViewById(R.id.carpool_list);
        carpoolMakeFab = (FloatingActionButton) findViewById(R.id.carpool_make_fab);

        //리스트뷰에 어댑터 연결
        adapter=new ListviewAdapter();
        listview.setAdapter(adapter);

        //fab에 카풀 생성 액티비티 연결
        carpoolMakeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoMakeCarpool=new Intent(thisActivity,CarpoolMakeActivity.class);
                startActivity(gotoMakeCarpool);
            }
        });

        Intent thisIntent = getIntent();
        if (!thisIntent.getBooleanExtra("auto",false)){
           LoginActivity.mThisActivity.finish();
        }


        FragmentManager fragmentManager = getFragmentManager();


        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        NavUserName = (TextView) findViewById(R.id.nav_head_name);
        NavUserTopUnit = (TextView) findViewById(R.id.nav_head_topunit);
        NavUserUnitName = (TextView) findViewById(R.id.nav_head_unitname);
        NavUserEmail = (TextView) findViewById(R.id.nav_head_email);
        NavUserName.setText(mPreferences.getString("Name","ROCA"));
        NavUserTopUnit.setText(mPreferences.getString("TopUnit","육군"));
        NavUserUnitName.setText(mPreferences.getString("UnitName","국방부"));
        NavUserEmail.setText(mPreferences.getString("Email",""));
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        // Remove any pending mHideConfirmSaveMessage.

        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_alert) {
            // Handle the camera action
        } else if (id == R.id.nav_unit_info) {
            Intent gotoAlert=new Intent(this,AlertActivity.class);
            startActivity(gotoAlert);
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

}
