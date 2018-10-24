package com.kosam.carpool.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kosam.carpool.R;
import com.savagelook.android.UrlJsonAsyncTask;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UnitMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton makeUnitBtn = (FloatingActionButton) findViewById(R.id.unit_make);
        final Intent i=new Intent(this,makeUnitActivity.class);
        makeUnitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
    }
/*
    private class SignUpTask extends UrlJsonAsyncTask {

        private final String mEmail;
        private final String mName;
        private final String mGroup;
        private final String mRank;
        private final String mPhone;
        private final Boolean mUsingCar;
        private final String mPassword;
        private final String mPasswordConfirmation;

        public SignUpTask(Context context, String email, String  name, String group, String  rank, String  phone, Boolean using_car, String password, String passwordConfirm) {
            super(context);

            mEmail = email;
            mName = name;
            mGroup = group;
            mRank = rank;
            mPhone = phone;
            mUsingCar = using_car;
            mPassword = password;
            mPasswordConfirmation = passwordConfirm;
        }

        @Override
        protected JSONObject doInBackground(String... urls) {

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject userObj = new JSONObject();
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
                    userObj.put("email", mEmail);
                    userObj.put("name", mName);
                    userObj.put("group", mGroup);
                    userObj.put("rank", mRank);
                    userObj.put("phone", mPhone);
                    userObj.put("using_car", mUsingCar);
                    userObj.put("password", mPassword);
                    userObj.put("password_confirmation", mPasswordConfirmation);
                    holder.put("user", userObj);
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

                    SharedPreferences.Editor editor = mPreferences.edit();
                    // save the returned auth_token into
                    // the SharedPreferences
                    editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
                    editor.putString("Email", json.getJSONObject("data").getJSONObject("user").getString("email"));
                    editor.putString("Name", json.getJSONObject("data").getJSONObject("user").getString("name"));
                    editor.putString("Group", json.getJSONObject("data").getJSONObject("user").getString("group"));
                    editor.putString("Rank", json.getJSONObject("data").getJSONObject("user").getString("rank"));
                    editor.putString("Phone", json.getJSONObject("data").getJSONObject("user").getString("phone"));
                    editor.putString("UsingCar", json.getJSONObject("data").getJSONObject("user").getString("using_car"));

                    if (json.getJSONObject("data").getJSONObject("user").isNull("unit_id")) {
                        editor.putInt("UnitId", -1);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), UnitMemberActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        editor.putInt("UnitId", json.getJSONObject("data").getJSONObject("user").getInt("unit_id"));
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
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
            mAuthTask = null;
            showProgress(false);
        }

    }
*/
}
