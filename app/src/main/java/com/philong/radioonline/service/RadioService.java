package com.philong.radioonline.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Long on 6/30/2017.
 */

public class RadioService extends Service implements MediaPlayer.OnPreparedListener{

    public static final String PLAY = "PLAY";
    public static final String PAUSE = "PAUSE";

    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        regisBroadcastPause();
        regisBroadcastPlay();
        if(mMediaPlayer == null){
            try {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(intent.getStringExtra("Link"));
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(RadioService.this);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(RadioService.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }else{
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(intent.getStringExtra("Link"));
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(RadioService.this);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(RadioService.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
        unregisterReceiver(broadcasrPause);
        unregisterReceiver(broadcastPlay);
        stopSelf();
    }

    private BroadcastReceiver broadcastPlay = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!mMediaPlayer.isPlaying() && mMediaPlayer != null){
                mMediaPlayer.start();
            }
        }
    };

    private void regisBroadcastPlay(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PLAY);
        registerReceiver(broadcastPlay, intentFilter);
    }

    private BroadcastReceiver broadcasrPause = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();
            }
        }
    };

    private void regisBroadcastPause(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PAUSE);
        registerReceiver(broadcasrPause, intentFilter);
    }
}