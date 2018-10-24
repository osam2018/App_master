package com.kosam.carpool.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;
import com.kosam.carpool.R;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AddUnitActivity extends AppCompatActivity {
    public static Activity mThisActivity;

    // For auth
    private String mURL = "https://kosam-app-server.run.goorm.io/api/units";
    private AddUnitActivity.AddUnitTask mUnitTask = null;
    private String mToken;
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private class AddUnitTask extends UrlJsonAsyncTask {

        private final Integer mUnit;

        public AddUnitTask(Context context, Integer unit) {
            super(context);

            mUnit = unit;
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
                // setup the returned values in case
                // something goes wrong
                json.put("success", false);
                json.put("info", "Something went wrong.");
                // add the user email and password to
                // the params
                holder.put("auth_token", mPreferences.getString("auth_token",""));
                unitObj.put("unit_id", mUnit);
                holder.put("unit", unitObj);
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
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IO", "" + e);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {
                    // everything is ok
                    //ColorItems.saveColorItem(getActivity(), new ColorItem(mLastPickedColor, json.getJSONObject("data").getInt("id")));
                    //tSaveCompleted(true);

                    // 메인 화면으로 이동

                } else {

                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // something went wrong: show a Toast with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }

        @Override
        protected void onCancelled() {
            mUnitTask = null;
        }

    }

}
