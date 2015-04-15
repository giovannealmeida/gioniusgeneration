package com.giog.gioniusgeneration.activities;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.utils.ExitGameDialog;
import java.util.Random;

import com.giog.gioniusgeneration.utils.GameUtils.GAME_COLORS;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_DIFFICULT;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_MODE;

import static com.giog.gioniusgeneration.utils.GameUtils.DEFAULT_DELAY;
import static com.giog.gioniusgeneration.utils.GameUtils.PREFS_GAME_MODE_KEY;
import static com.giog.gioniusgeneration.utils.GameUtils.getRamdomColorsSequence;
import static com.giog.gioniusgeneration.utils.GameUtils.setTextViewModeTitle;

public class GameGeniusActivity extends ActionBarActivity implements View.OnClickListener {

    private int postDelay = DEFAULT_DELAY;
    private GAME_DIFFICULT game_difficult = GAME_DIFFICULT.GENIUS;

    private TextView tvGameMode;
    private ImageButton btnProgress;
    private AnimationDrawable animProgress;
    private GAME_MODE game_mode;
    private GAME_COLORS[] levelsSequence;
    private int currentLevel;

    private Handler handler;

    private ImageButton btnRed, btnYellow, btnBlue, btnGreen, btnOrange, btnPink, btnGray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_genius);

        handler = new Handler();
        tvGameMode = (TextView) findViewById(R.id.tvGameMode);

        initializeButtons();
        initializeScreen();
        initializeSequence();
    }

    private void initializeButtons(){
        btnProgress= (ImageButton) findViewById(R.id.imPlaySample);
        btnProgress.setOnClickListener(this);

        this.btnRed = (ImageButton) findViewById(R.id.btnRed);
        this.btnYellow = (ImageButton) findViewById(R.id.btnYellow);
        this.btnBlue = (ImageButton) findViewById(R.id.btnBlue);
        this.btnGreen = (ImageButton) findViewById(R.id.btnGreen);
        this.btnOrange = (ImageButton) findViewById(R.id.btnOrange);
        this.btnPink = (ImageButton) findViewById(R.id.btnPink);
        this.btnGray = (ImageButton) findViewById(R.id.btnGray);
    }

    private void initializeScreen(){
        game_mode = (GAME_MODE) getIntent().getExtras().get(PREFS_GAME_MODE_KEY);
        setTextViewModeTitle(tvGameMode, game_mode, this);
    }

    private void initializeSequence(){
        levelsSequence = getRamdomColorsSequence(new Random(System.currentTimeMillis()), game_difficult);
        currentLevel = 1;
    }

    @Override
    public void onBackPressed(){
        new ExitGameDialog().show(getSupportFragmentManager(),"exit_dialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imPlaySample:
                btnProgress.setBackgroundResource(R.drawable.anim_progress);
                animProgress = (AnimationDrawable) btnProgress.getBackground();
                startAnimProgress();
//                toggleAnimProgress();
                playSample();
                stopAnimProgress();
                break;
        }
    }

    private void playSample() {
        for(int i=0; i<currentLevel; i++){
            switch (levelsSequence[i]){
                case RED:
                    playRed();
                    break;
                case YELLOW:
                    playYellow();
                    break;
                case BLUE:
                    playBlue();
                    break;
                case GREEN:
                    playGreen();
                    break;
                case ORANGE:
                    playOrange();
                    break;
                case PINK:
                    playPink();
                    break;
                case GRAY:
                    playGray();
                    break;
            }
        }

        postDelay = DEFAULT_DELAY;
    }

    private void playRed() {
        performPlay(btnRed, 0, R.drawable.button_red, R.drawable.button_red_pressed);
    }

    private void playYellow() {
        performPlay(btnYellow, 0, R.drawable.button_yellow, R.drawable.button_yellow_pressed);
    }

    private void playBlue() {
        performPlay(btnBlue, 0, R.drawable.button_blue, R.drawable.button_blue_pressed);
    }

    private void playGreen() {
        performPlay(btnGreen, 0, R.drawable.button_green, R.drawable.button_green_pressed);
    }

    private void playOrange() {
        performPlay(btnOrange, 0, R.drawable.button_orange, R.drawable.button_orange_pressed);
    }

    private void playPink() {
        performPlay(btnPink, 0, R.drawable.button_pink, R.drawable.button_pink_pressed);
    }

    private void playGray() {
        performPlay(btnGray, 0, R.drawable.button_gray, R.drawable.button_gray_pressed);
    }

    private void toggleAnimProgress(){
        if(animProgress.isRunning()){
            animProgress.stop();
            btnProgress.setBackgroundResource(R.drawable.ic_sample);
        }else{
            animProgress.start();
        }
    }

    private void startAnimProgress(){
        if(!animProgress.isRunning()){
            btnProgress.setBackgroundResource(R.drawable.anim_progress);
            animProgress = (AnimationDrawable) btnProgress.getBackground();
            animProgress.start();
        }
    }

    private void stopAnimProgress(){
        if(animProgress.isRunning()){
            animProgress.stop();
            btnProgress.setBackgroundResource(R.drawable.ic_sample);
        }
    }

    @Override
    protected void onPause(){
        if(animProgress != null && animProgress.isRunning()){
            animProgress.stop();
            btnProgress.setBackgroundResource(R.drawable.ic_sample);
        }
        super.onPause();
    }

    private void performPlay(final ImageButton button, final int soundId,
                             final int normalState, final int modifiedState) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                soundPool.play(soundId, volume, volume, 1, 0, 1f);
                button.setBackgroundResource(modifiedState);
            }
        }, postDelay+=DEFAULT_DELAY);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                button.setBackground(getResources().getDrawable(normalState));
                button.setBackgroundResource(normalState);
            }
        }, postDelay+=DEFAULT_DELAY);
    }
}
