package com.example.david.helloworld.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.david.helloworld.activities.LoginActivity;

/**
 * Created by david on 9.2.2018..
 */

public class NetworkHelper {

    public static void isNetworkAvailable(Context context, SharedPreferences sharedPreferences) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            Toast.makeText(context, "No internet access.\nPlease connect to internet to continue.", Toast.LENGTH_LONG).show();
            navigateToLogin(context, sharedPreferences);
        }
    }

    public static void navigateToLogin(Context context, SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("HelloWorldToken");
            editor.apply();
        }

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
