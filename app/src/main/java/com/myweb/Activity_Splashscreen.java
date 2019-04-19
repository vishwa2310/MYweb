package com.myweb;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Activity_Splashscreen extends AppCompatActivity {
    Context context;
    DeepLinker mDeepLinker = new DeepLinker();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
  /*      // Extrapolates the deeplink data
        Intent intent = getIntent();
        Uri deeplink = intent.getData();

        // Parse the deeplink and take the adequate action
        if (deeplink != null && deeplink.equals("null")) {
            Intent i = new Intent(this, Activity_Splashscreen.class);
            startActivity(i);
            System.out.println("datatttt"+deeplink);
        }*/

        context = this;
        handleIntent();
        if (GlobalObject.isNetworkAvailable(context)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(context, Test_Homepage.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }, 3000);
        } else {
            Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
        }
// ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if (appLinkData!=null){
            Intent i = new Intent(this, Activity_Splashscreen.class);
            startActivity(i);
            System.out.println("test");
        }
    } @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Override previous intent
        setIntent(intent);

        // Handle new intent
        handleIntent();
    }
    /**
     * Entry point for handling the activity's intent
     */
    private void handleIntent() {
        // Get the intent set on this activity
        Intent intent = getIntent();

        // Get the uri from the intent
        Uri uri = intent.getData();

        // Do not continue if the uri does not exist
        if (uri == null) {
            return;
        }

        // Let the deep linker do its job
        Bundle data = mDeepLinker.buildBundle(uri);
        if (data == null) {
            return;
        }

        // See if we have a valid link
        DeepLinker.Link link = DeepLinker.getLinkFromBundle(data);
        if (link == null) {
            return;
        }

        // Do something with the link
        switch (link) {
            case HOME:
                Intent i = new Intent(this, Activity_Splashscreen.class);
                startActivity(i);
                System.out.println("home");
                break;
            case PROFILE:
                Intent iii = new Intent(this, Activity_Splashscreen.class);
                startActivity(iii);
                System.out.println("profile");
                break;
            case PROFILE_OTHER:
                Intent i2 = new Intent(this, Activity_Splashscreen.class);
                startActivity(i2);
                System.out.println("other");
                break;
            case SETTINGS:
                Intent ii = new Intent(this, Activity_Splashscreen.class);
                startActivity(ii);
                System.out.println("setting");
                break;
        }

        String msg;
        long id = DeepLinker.getIdFromBundle(data);
        if (id == 0) {
            msg = String.format("Link: %s", link);
        } else {
            msg = String.format("Link: %s, ID: %s", link, id);
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
