package com.example.david.helloworld.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.helloworld.R;

public class MainActivity extends Activity {

    TextView username;
    ImageView profileImage;
    ImageView playButton;
    ImageView practiseButton;
    ImageView statisticButton;
    ImageView aboutButton;
    ImageView shutdownButton;
    ImageView logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView) findViewById(R.id.userName);
        profileImage = (ImageView) findViewById(R.id.userPhotoMainMenu);
        playButton = (ImageView) findViewById(R.id.main_menu_play);
        practiseButton = (ImageView) findViewById(R.id.main_menu_practise);
        statisticButton = (ImageView) findViewById(R.id.main_menu_statistic);
        aboutButton = (ImageView) findViewById(R.id.main_menu_about);
        shutdownButton = (ImageView) findViewById(R.id.shutdown);
        logoutButton = (ImageView) findViewById(R.id.logout);

    }
}
