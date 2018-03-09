package com.example.david.helloworld.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.david.helloworld.R;

public class BackgroundMusicService extends Service implements MediaPlayer.OnErrorListener {
    public BackgroundMusicService() {
    }

    final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;
    public static boolean State = true;

    public class ServiceBinder extends Binder {
        public BackgroundMusicService getService() {
            return BackgroundMusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.background_music1);
        mPlayer.setLooping(true);
        mPlayer.setVolume(20, 20);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.start();
        State = true;
        return super.onStartCommand(intent, flags, startId);
    }


    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();
            State = false;
        }
    }

    public void resumeMusic() {
        if (mPlayer.isPlaying() == false) {
            mPlayer.seekTo(length);
            mPlayer.start();
            State = true;
        }
    }

    public void stopMusic() {
        mPlayer.stop();
        mPlayer.release();
        State = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                // State = false;
                mPlayer = null;
            }
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
