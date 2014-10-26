package com.giog.gioniusgeneration.activities;

import android.graphics.drawable.AnimationDrawable;
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

    private Handler handler;

    private ImageButton btnRed, btnYellow, btnBlue, btnGreen, btnOrange, btnPink, btnWhite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_genius);

        handler = new Handler();
        tvGameMode = (TextView) findViewById(R.id.tvGameMode);
        btnProgress= (ImageButton) findViewById(R.id.imPlaySample);
        btnProgress.setOnClickListener(this);

        initializeButtons();
        initializeScreen();
        initializeSequence();
    }

    private void initializeButtons(){
        this.btnRed = (ImageButton) findViewById(R.id.btnRed);
        this.btnYellow = (ImageButton) findViewById(R.id.btnYellow);
    }

    private void initializeScreen(){
        game_mode = (GAME_MODE) getIntent().getExtras().get(PREFS_GAME_MODE_KEY);
        setTextViewModeTitle(tvGameMode, game_mode, this);
    }

    private void initializeSequence(){
        levelsSequence = getRamdomColorsSequence(new Random(System.currentTimeMillis()), game_difficult);
    }

    @Override
    public void onBackPressed(){
        new ExitGameDialog().show(getSupportFragmentManager(),"exit_dialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imPlaySample:
//                btnProgress.setBackgroundResource(R.drawable.anim_progress);
//                animProgress = (AnimationDrawable) btnProgress.getBackground();
//                toggleAnimProgress();
                playSample();
                break;
        }
    }

    private void playSample() {
        playYellow();
        playRed();
        playYellow();
        playYellow();
        for(int i=0; i<levelsSequence.length; i++){
            switch (levelsSequence[i]){
                case RED:
//                    playRed();
                    break;
                case YELLOW:
//                    playYellow();
                    break;
                case BLUE:
//                    playBlue();
                    break;
                case GREEN:
//                    playGreen();
                    break;
                case ORANGE:
//                    playOrange();
                    break;
                case PINK:
//                    playPink();
                    break;
                case GRAY:
//                    playWhite();
                    break;
            }
        }
    }

    private void playRed() {

    }

    private void playYellow() {

    }

    private void toggleAnimProgress(){
        if(animProgress.isRunning()){
            animProgress.stop();
            btnProgress.setBackgroundResource(R.drawable.ic_sample);
        }else{
            animProgress.start();
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

//    private void performPlay(final ImageButton button, final int soundId,
//                             final int normalDrawable, final int modifiedDrawable) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                soundPool.play(soundId, volume, volume, 1, 0, 1f);
//                button.setBackground(getResources().getDrawable(
//                        modifiedDrawable));
//            }
//        }, postDelay+=DEFAULT_DELAY);
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                button.setBackground(getResources().getDrawable(normalDrawable));
//            }
//        }, postDelay+=DEFAULT_DELAY);
//    }
}
