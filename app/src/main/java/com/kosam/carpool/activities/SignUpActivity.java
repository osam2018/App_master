package com.kosam.carpool.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;
import com.kosam.carpool.R;

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

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private SharedPreferences mPreferences;
    private SignUpTask mAuthTask = null;
    private String mURL = "https://kosam-app-server.run.goorm.io/api/v1/registrations";


    // UI references.
    private EditText mEmailView;
    private EditText mNameView;
    private Spinner mGroupView;
    private EditText mRankView;
    private EditText mPhoneView;
    private CheckBox mUsingCarView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;
    private View mProgressView;
    private View mLoginFormView;
    //resource connect
    private String[] item;
    private ArrayAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        item = getResources().getStringArray(R.array.unit_head);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        sAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,item);


        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mNameView = (EditText) findViewById(R.id.name);
        mGroupView = (Spinner) findViewById(R.id.group);
        mGroupView.setAdapter(sAdapter);
        mRankView = (EditText) findViewById(R.id.rank);
        mPhoneView = (EditText) findViewById(R.id.phone);
        mUsingCarView = (CheckBox) findViewById(R.id.using_car);
        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordConfirmView = (EditText) findViewById(R.id.password_confirm);
        mPasswordConfirmView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mNameView.setError(null);
        mRankView.setError(null);
        mPhoneView.setError(null);
        mUsingCarView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirmView.setError(null);



        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordConfirm = mPasswordConfirmView.getText().toString();
        String name = mNameView.getText().toString();
        String group = mGroupView.getSelectedItem().toString();
        String rank = mRankView.getText().toString();
        String phone = mPhoneView.getText().toString();
        Boolean using_car = mUsingCarView.isChecked();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;

        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;

        } else if (TextUtils.isEmpty(passwordConfirm)){
            mPasswordConfirmView.setError(getString(R.string.error_field_required));
            focusView = mPasswordConfirmView;
            cancel = true;

        } else if (!password.equals(passwordConfirm)){
            mPasswordConfirmView.setError(getString(R.string.error_comfirmation_password));
            focusView = mPasswordConfirmView;
            cancel = true;

        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new SignUpTask(this, email, name, group, rank, phone,using_car, password, passwordConfirm);
            mAuthTask.execute(mURL);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class SignUpTask extends UrlJsonAsyncTask {

        private final String mEmail;
        private final String mName;
        private final String mGroup;
        private final String mRank;
        private final String mPhone;
        private final Boolean mUsingCar;
        private final String mPassword;
        private final String mPasswordConfirmation;

        public SignUpTask(Context context, String email,String  name, String group, String  rank,String  phone, Boolean using_car, String password, String passwordConfirm) {
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
}

