package com.example.david.helloworld.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.david.helloworld.R;

public class SettingsActivity extends Activity {

    ToggleButton musicBtn;
    ImageView rateBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        musicBtn = (ToggleButton)findViewById(R.id.music_settings);
        rateBtn = (ImageView) findViewById(R.id.about_settings);
        backBtn = (ImageView) findViewById(R.id.back_settings);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
