package com.suri.abcbike.activities;

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

import com.savagelook.android.UrlJsonAsyncTask;
import com.suri.abcbike.R;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CarpoolMakeActivity extends AppCompatActivity {
    private String mURL = "https://server-bpmsz.run.goorm.io/api/carpools";
    private CarpoolMakeActivity.CarpoolTask mCarpoolTask = null;
    private SharedPreferences mPreferences;

    EditText mUnitName;
    private View mProgressView;
    private View mMakeUnitFormView;
    public static Activity mThisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_make);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);


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
        });
    }


}
