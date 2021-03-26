package com.cos.musicapp_ui.utils;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.cos.musicapp_ui.view.main.MainActivity;


import lombok.Data;

@Data
public class PlayService extends Service {

    private static final String TAG = "PlayService";
    private MediaPlayer mp;
    private MainActivity mainActivity;
    private final IBinder mBinder = new LocalBinder();

    public PlayService() {
    }

    public MediaPlayer getMediaPlayer() {
        return mp;
    }

    public void setMainActivity(MainActivity mainActivity) {
        Log.d(TAG, "setHomeActivity: 실행됨");
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder { //패키지가 달라서 public
        public com.cos.musicapp_ui.utils.PlayService getService() {
            return com.cos.musicapp_ui.utils.PlayService.this;
        }
    }




}