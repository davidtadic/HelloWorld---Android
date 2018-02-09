package com.example.david.helloworld.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.david.helloworld.R;
import com.example.david.helloworld.helpers.TokenHelper;
import com.example.david.helloworld.models.user.TokenModel;

public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 4000;
    private SharedPreferences sharedPreferences = null;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("MY_PREF", 0);
                authToken = sharedPreferences.getString("HelloWorldToken", "");

                if (TokenHelper.DecodeToken(authToken) != null) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
