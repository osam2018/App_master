package com.kosam.carpool.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosam.carpool.R;
import com.kosam.carpool.activities.classGroup.CarpoolListItem;
import com.kosam.carpool.activities.classGroup.UnitAdapter;
import com.kosam.carpool.activities.classGroup.UnitListItem;
import com.savagelook.android.UrlJsonAsyncTask;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitMemberActivity extends AppCompatActivity {
    private UnitMemberActivity thisActivity;
    List<UnitListItem> mUnitList;
    UnitAdapter adapter;
    private UnitMemberActivity.UnitTask mUnitTask = null;
    private UnitMemberActivity.AddUnitTask mAddUnitTask = null;
    private SharedPreferences mPreferences;
    private String mURL = "https://kosam-app-server.run.goorm.io/api/units/show";
    private String mURL1 = "https://kosam-app-server.run.goorm.io/api/units/search";

    private View mProgressView;
    private ListView mUnitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        thisActivity=this;
        mUnitList = new ArrayList<UnitListItem>();
        mUnitListView = findViewById(R.id.unit_list_view);
        mProgressView = findViewById(R.id.unit_progress);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        adapter=new UnitAdapter();

        mUnitTask = new UnitMemberActivity.UnitTask(this);
        mUnitTask.execute(mURL);

        mUnitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity, R.style.Theme_Design_Light);
                final String unit_name=((TextView) view.findViewById(R.id.unit_name)).getText().toString();
                builder
                        .setMessage(unit_name+ "에\n가입하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                            // 확인 버튼 클릭시 설정

                            public void onClick(DialogInterface dialog, int whichButton) {
                                mAddUnitTask = new UnitMemberActivity.AddUnitTask(UnitMemberActivity.this,unit_name);
                                mAddUnitTask.execute(mURL1);

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                builder.create();
                builder.show();
            }

        });




        FloatingActionButton makeUnitBtn = (FloatingActionButton) findViewById(R.id.unit_make);
        final Intent i=new Intent(this,makeUnitActivity.class);
        makeUnitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
    }

    private class AddUnitTask extends UrlJsonAsyncTask {

        private final String mUnitName;

        public AddUnitTask(Context context, String unit_name) {
            super(context);

            mUnitName = unit_name;
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject unitObj = new JSONObject();
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
                    unitObj.put("top_unit", mPreferences.getString("Group",""));
                    unitObj.put("unit_name", mUnitName);
                    holder.put("unit", unitObj);
                    holder.put("auth_token",  mPreferences.getString("AuthToken",""));
                    StringEntity se = new StringEntity(holder.toString(), HTTP.UTF_8);
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

                    SharedPreferences.Editor editor = mPreferences.edit();
                    // save the returned auth_token into the SharedPreferences
                    editor.remove("UnitId");

                    if (json.getJSONObject("data").isNull("unit_id")) {
                        editor.putInt("UnitId", -1);

                        editor.apply();
                    } else {
                        editor.putInt("UnitId", json.getJSONObject("data").getInt("unit_id"));
                        editor.putString("TopUnit", json.getJSONObject("data").getString("top_unit"));
                        editor.putString("UnitName", json.getJSONObject("data").getString("unit_name"));
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    // launch the HomeActivity and close this one

                } else {
                    showProgress(false);
                }
            } catch (Exception e) {
                // something went wrong: show a Toast with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                showProgress(false);
            } finally {
                super.onPostExecute(json);
            }
        }

        @Override
        protected void onCancelled() {
            mAddUnitTask = null;
            showProgress(false);
        }

    }


    private class UnitTask extends UrlJsonAsyncTask {


        public UnitTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject unitObj = new JSONObject();
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
                    holder.put("auth_token", mPreferences.getString("AuthToken",""));
                    StringEntity se = new StringEntity(holder.toString(), HTTP.UTF_8);
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

                    JSONArray jUnitArray = json.getJSONArray("data");

                    if(jUnitArray != null) {

                        for(int i = 0; i <jUnitArray.length(); i++) {
                            JSONObject jobj = jUnitArray.getJSONObject(i);

                            String unit_name = jobj.getString("unit_name");
                            Integer manager_id = jobj.getInt("user_id");

                            UnitListItem item = new UnitListItem(unit_name, manager_id);

                            adapter.addItem(item);
                        }

                        //여기서부터 mUnitList를 이용하여 List에 자료 넣기 코드 넣기
                        mUnitListView.setAdapter(adapter);
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
            mUnitTask = null;
            showProgress(false);
        }

    }
    private  void refreshList(){
        mUnitTask.execute(mURL);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mUnitListView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUnitListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUnitListView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mUnitListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
                Intent goIntro = new Intent(UnitMemberActivity.this, LoginActivity.class);
                startActivity(goIntro);
                finish();

                return true;
            case R.id.action_refresh:
                mUnitTask = new UnitMemberActivity.UnitTask(this);
                mUnitTask.execute(mURL);
                //mPreferences.edit().clear().commit();
                //Intent goIntro = new Intent(UnitMemberActivity.this, LoginActivity.class);
                //startActivity(goIntro);
                // finish();

                return true;
            default:

        }

        return super.onOptionsItemSelected(item);
    }

}
