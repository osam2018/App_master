package com.kosam.carpool.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CarpoolMakeActivity extends AppCompatActivity {
    private String mURL = "https://kosam-app-server.run.goorm.io/api/carpools/create";
    private CarpoolMakeActivity.CarpoolTask mCarpoolTask = null;
    private SharedPreferences mPreferences;

    EditText mHour;
    EditText mMinute;
    EditText mStart;
    EditText mEnd;
    EditText mMaxPerson;
    private View mProgressView;
    private View mCarpoolMakeFormView;
    public static Activity mThisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_make);
        Log.e("1","1");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        Button mAddCarpoolButton = (Button) findViewById(R.id.add_carpool_button );
        mCarpoolMakeFormView = findViewById(R.id.carpool_make_form);
        mProgressView = findViewById(R.id.carpool_make_progress);

        mHour= findViewById(R.id.make_carpool_hour);
        mMinute = findViewById(R.id.make_carpool_minute);
        mStart = findViewById(R.id.make_carpool_start);
        mEnd = findViewById(R.id.make_carpool_end);
        mMaxPerson = findViewById(R.id.make_carpool_max);

        mAddCarpoolButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                sendCarpool();
            }
        });

/*
        item = getResources().getStringArray(R.array.unit_head);
        mSpinner = (Spinner) findViewById(R.id.unit_category);
        sAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,item);
        mSpinner.setAdapter(sAdapter);

        mUnitName = (EditText) findViewById(R.id.unit_name);
        mUnitName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.unit_make || id == EditorInfo.IME_NULL) {
                    sendCarpool();
                    return true;
                }
                return false;
            }
        });

        Button mAddUnitButton = (Button) findViewById(R.id.add_unit_button );
        mMakeUnitFormView = findViewById(R.id.make_unit_form);
        mProgressView = findViewById(R.id.make_unit_progress);

        mAddCarpoolButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                sendCarpool();
            }
        });*/
    }

    private void sendCarpool() {
        if (mCarpoolTask != null) {
            return;
        }

        // Reset errors.

        mHour.setError(null);
        mMinute.setError(null);
        mStart.setError(null);
        mEnd.setError(null);
        mMaxPerson.setError(null);
        Log.e("continue","setError");

        // Store values at the time of the login attempt.
        Integer hour= Integer.parseInt(mHour.getText().toString());
        Integer minute = Integer.parseInt(mMinute.getText().toString());
        String start = mStart.getText().toString();
        String end = mEnd.getText().toString();
        Integer max_person = Integer.parseInt(mMaxPerson.getText().toString());
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(hour.toString())) {
            mHour.setError(getString(R.string.error_field_required));
            focusView = mHour;
            cancel = true;
        }
        if (TextUtils.isEmpty(minute.toString())) {
            mMinute.setError(getString(R.string.error_field_required));
            focusView = mMinute;
            cancel = true;
        }
        if (TextUtils.isEmpty(start)) {
            mStart.setError(getString(R.string.error_field_required));
            focusView = mStart;
            cancel = true;
        }
        if (TextUtils.isEmpty(end)) {
            mEnd.setError(getString(R.string.error_field_required));
            focusView = mEnd;
            cancel = true;
        }
        if (TextUtils.isEmpty(max_person.toString())) {
            mMaxPerson.setError(getString(R.string.error_field_required));
            focusView = mMaxPerson;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);
            Log.e("task","before unit task");
            mCarpoolTask = new CarpoolMakeActivity.CarpoolTask(this, hour,minute,start,end,max_person);
            mCarpoolTask.execute(mURL);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mCarpoolMakeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mCarpoolMakeFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCarpoolMakeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mCarpoolMakeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }

        if (!show) {
            mCarpoolTask = null;
        }

    }

    private class CarpoolTask extends UrlJsonAsyncTask {

        private final Integer mHour;
        private final Integer mMinute;
        private final String mStart;
        private final String mEnd;
        private final Integer mMaxPerson;


        public CarpoolTask(Context context, Integer hour, Integer minute, String start, String end, Integer max_person) {
            super(context);

            mHour = hour;
            mMinute = minute;
            mStart = start;
            mEnd = end;
            mMaxPerson = max_person;
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
                    carpoolObj.put("hour", mHour);
                    carpoolObj.put("minute", mMinute);
                    carpoolObj.put("start", mStart);
                    carpoolObj.put("end", mEnd);
                    carpoolObj.put("max_person", mMaxPerson);
                    holder.put("carpool", carpoolObj);
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

                    Intent intent = new Intent();
                    intent.putExtra("refresh", true);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    // launch the HomeActivity and close this one

                } else {
                    showProgress(false);
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
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
            mCarpoolTask = null;
            showProgress(false);
        }

    }


}
