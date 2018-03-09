package com.example.david.helloworld.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.audiofx.BassBoost;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.david.helloworld.R;
import com.example.david.helloworld.services.BackgroundMusicService;

public class SettingsActivity extends Activity {

    ToggleButton musicBtn;
    ImageView rateBtn;
    ImageView backBtn;
    BackgroundMusicService musicService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            BackgroundMusicService.ServiceBinder binder = (BackgroundMusicService.ServiceBinder) service;
            musicService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

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

        if (musicService.State) {
            musicBtn.setBackgroundResource(R.drawable.music);
        } else {
            musicBtn.setBackgroundResource(R.drawable.music_off1);
        }

        musicBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && musicService.State) {
                    musicBtn.setBackgroundResource(R.drawable.music_off1);
                    musicService.pauseMusic();
                } else if (isChecked && !musicService.State) {
                    musicBtn.setBackgroundResource(R.drawable.music);
                    musicService.resumeMusic();
                } else if (musicService.State) {
                    musicBtn.setBackgroundResource(R.drawable.music_off1);
                    musicService.pauseMusic();
                } else if (!musicService.State) {
                    musicBtn.setBackgroundResource(R.drawable.music);
                    musicService.resumeMusic();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BackgroundMusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
