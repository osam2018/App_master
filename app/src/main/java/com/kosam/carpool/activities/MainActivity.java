package com.kosam.carpool.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosam.carpool.R;
import com.savagelook.android.UrlJsonAsyncTask;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

// , OnMapReadyCallback
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected static final String TAG = MainActivity.class.getSimpleName();

    private MainActivity.CarpoolTask mCarpoolTask = null;
    private String mURL = "https://kosam-app-server.run.goorm.io/api/carpools";

    private View mProgressView;
    private View mCarpoolListView;

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
    ListView listview;
    FloatingActionButton carpoolMakeFab;

    ListviewAdapter adapter;

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

        mCarpoolListView = findViewById(R.id.carpool_list_view);
        mProgressView = findViewById(R.id.unit_progress);

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

        //navigation header 설정
        NavUserName = (TextView) findViewById(R.id.nav_head_name);
        NavUserTopUnit = (TextView) findViewById(R.id.nav_head_topunit);
        NavUserUnitName = (TextView) findViewById(R.id.nav_head_unitname);
        NavUserEmail = (TextView) findViewById(R.id.nav_head_email);
        NavUserName.setText(mPreferences.getString("Name","ROCA"));
        NavUserTopUnit.setText(mPreferences.getString("TopUnit","육군"));
        NavUserUnitName.setText(mPreferences.getString("UnitName","국방부"));
        NavUserEmail.setText(mPreferences.getString("Email",""));

        //카풀 리스트뷰 연결

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
        // 네비게이션뷰 선택 처리
        int id = item.getItemId();

        if (id == R.id.nav_alert) {
            // 알림함 연결
            Intent gotoAlert=new Intent(this,AlertActivity.class);
            startActivity(gotoAlert);
        } else if (id == R.id.nav_unit_info) {
            //부대 정보 연결
        } else if (id == R.id.nav_setting) {
            //설정 연결

        } else if (id == R.id.nav_logout) {
            //로그아웃
            mPreferences.edit().clear().commit();
            Intent goIntro = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(goIntro);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*  옵션메뉴 삭제
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
*/

    private class CarpoolTask extends UrlJsonAsyncTask {


        public CarpoolTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject carpoolObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    // setup the returned values in case
                    // something goes wrong
                    json.put("success", false);
                    json.put("info", "Something went wrong.");
                    // add the user email and password to
                    // the params
                    carpoolObj.put("auth_token", mPreferences.getString("AuthToken",""));
                    holder.put("carpool", carpoolObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);

                    // setup the request headers
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info", "Email and/or password are invalid.");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {
                    // everything is ok

                    //SharedPreferences.Editor editor = mPreferences.edit();
                    // save the returned auth_token into
                    // the SharedPreferences
                    JSONArray jCarpoolArray = json.getJSONObject("data").getJSONArray("carpool");
                    if(jCarpoolArray != null) {

                        for(int i = 0; i <jCarpoolArray.length(); i++) {
                            JSONObject jobj = jCarpoolArray.getJSONObject(i);
                            //Pair j_pair = new Pair(jobj.getInt("id"), jobj.getString("unit_name"));
                            //mCarpoolList.add(j_pair);
                        }

                        //여기서부터 mUnitList를 이용하여 List에 자료 넣기 코드 넣기

                    }


                } else {
                    showProgress(false);
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
            } finally {
                super.onPostExecute(json);
            }
        }

        @Override
        protected void onCancelled() {
            mCarpoolTask = null;
            showProgress(false);
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mCarpoolListView.setVisibility(show ? View.GONE : View.VISIBLE);
            mCarpoolListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCarpoolListView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCarpoolListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
