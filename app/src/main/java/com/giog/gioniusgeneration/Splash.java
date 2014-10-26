package com.giog.gioniusgeneration;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Splash extends ActionBarActivity {

    private AnimationDrawable animLogo;
    Handler mHideHandler = new Handler();
    Runnable mAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            animLogo.start();
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

        ImageView ivImage = (ImageView) findViewById(R.id.ivLogo);

        animLogo = (AnimationDrawable) ivImage.getBackground();
        animLogo.setOneShot(true);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHideHandler.postDelayed(mAnimationRunnable,1000);
        delayedHide(2500);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onBackPressed(){}
}