package com.giog.gioniusgeneration;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Splash extends ActionBarActivity {

    private AudioManager audioManager;
    private SoundPool soundPool;

    private float volume;

    private int soundLogoId;

    private AnimationDrawable animLogo;
    Handler mHideHandler = new Handler();
    Runnable mAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            animLogo.start();
            soundPool.play(soundLogoId,volume,volume,1,0,1f);
        }
    };
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Splash.this.startActivity(intent);
            Splash.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ImageView ivImage = (ImageView) findViewById(R.id.ivLogoSplash);

        animLogo = (AnimationDrawable) ivImage.getBackground();
        initializeSounds();
    }

    private void initializeSounds() {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        soundLogoId = soundPool.load(this, R.raw.sound_logo, 1);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        volume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHideHandler.postDelayed(mAnimationRunnable,1500);
        delayedHide(5500);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onBackPressed(){}
}