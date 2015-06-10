package com.giog.gioniusgeneration.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.utils.ExitGameDialog;
import com.giog.gioniusgeneration.utils.GameOverDialog;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_COLORS;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_DIFFICULT;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_MODE;

import java.util.Random;

import static com.giog.gioniusgeneration.utils.GameUtils.DEFAULT_DELAY;
import static com.giog.gioniusgeneration.utils.GameUtils.MAX_LEVELS;
import static com.giog.gioniusgeneration.utils.GameUtils.PREFS_GAME_MODE_KEY;
import static com.giog.gioniusgeneration.utils.GameUtils.getRandomColorsSequence;
import static com.giog.gioniusgeneration.utils.GameUtils.setTextViewModeTitle;

public class GameGeniusActivity extends ActionBarActivity implements View.OnClickListener {

    private Context context;
    private int postDelay = DEFAULT_DELAY;
    private GAME_DIFFICULT game_difficult = GAME_DIFFICULT.GENIUS;
    private GAME_MODE game_mode;

    private TextView tvGameMode, tvLevel, tvScore;
    private TextSwitcher tsStatus;
    private ImageButton btnProgress;
    private AnimationDrawable animProgress;
    private GAME_COLORS[] levelsSequence;
    private int currentLevel, score, levelCarret = 0;
    private boolean isGameRunning = false;

    private Handler handler;

    private ImageButton btnRed, btnYellow, btnBlue, btnGreen, btnOrange, btnPink, btnGray;

    //Sounds
    private AudioManager audioManager;
    private SoundPool soundPool;

    private float volume;

    private int soundGameOverId, soundYourTurnId, soundWinGameId;
    private int soundRedId, soundYellowId, soundBlueId, soundGreenId, soundOrangeId, soundPinkId, soundGrayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_genius);

        context = getApplicationContext();

        handler = new Handler();
        tvGameMode = (TextView) findViewById(R.id.tvGameMode);
        tvLevel = (TextView) findViewById(R.id.tvLevel);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tsStatus = (TextSwitcher) findViewById(R.id.tsStatus);

        initializeButtons();
        initializeScreen();
        initializeSequence();
        initializeSounds();
    }

    private void initializeButtons() {
        btnProgress = (ImageButton) findViewById(R.id.imPlaySample);
        btnProgress.setOnClickListener(this);

        this.btnRed = (ImageButton) findViewById(R.id.btnRed);
        this.btnYellow = (ImageButton) findViewById(R.id.btnYellow);
        this.btnBlue = (ImageButton) findViewById(R.id.btnBlue);
        this.btnGreen = (ImageButton) findViewById(R.id.btnGreen);
        this.btnOrange = (ImageButton) findViewById(R.id.btnOrange);
        this.btnPink = (ImageButton) findViewById(R.id.btnPink);
        this.btnGray = (ImageButton) findViewById(R.id.btnGray);

        this.btnRed.setOnClickListener(this);
        this.btnYellow.setOnClickListener(this);
        this.btnBlue.setOnClickListener(this);
        this.btnGreen.setOnClickListener(this);
        this.btnOrange.setOnClickListener(this);
        this.btnPink.setOnClickListener(this);
        this.btnGray.setOnClickListener(this);
    }

    private void initializeScreen() {
        game_mode = (GAME_MODE) getIntent().getExtras().get(PREFS_GAME_MODE_KEY);
        setTextViewModeTitle(tvGameMode, game_mode, this);
        tvScore.setText(getResources().getText(R.string.game_text_score) + " " + "0");
        tvLevel.setText(getResources().getText(R.string.game_text_level) + " " + "1");

        tsStatus.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView myText = new TextView(context);
                myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                myText.setTextAppearance(context, R.style.TextGameScreen);
                FrameLayout.LayoutParams rlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                myText.setLayoutParams(rlp);
                return myText;
            }
        });

        tsStatus.setText(getResources().getText(R.string.game_text_press_play));
    }

    private void initializeSequence() {
        levelsSequence = getRandomColorsSequence(new Random(System.currentTimeMillis()), game_difficult);
        currentLevel = 1;
    }

    private void initializeSounds() {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        soundRedId = soundPool.load(this, R.raw.sound_red, 1);
        soundYellowId = soundPool.load(this, R.raw.sound_yellow, 1);
        soundBlueId = soundPool.load(this, R.raw.sound_blue, 1);
        soundGreenId = soundPool.load(this, R.raw.sound_green, 1);
        soundOrangeId = soundPool.load(this, R.raw.sound_orange, 1);
        soundPinkId = soundPool.load(this, R.raw.sound_pink, 1);
        soundGrayId = soundPool.load(this, R.raw.sound_gray, 1);
        soundGameOverId = soundPool.load(this, R.raw.sound_game_over, 1);
        soundYourTurnId = soundPool.load(this, R.raw.sound_turn, 1);
        soundWinGameId = soundPool.load(this, R.raw.sound_win_game, 1);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        volume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {
        new ExitGameDialog().show(getSupportFragmentManager(), "exit_dialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imPlaySample:
                isGameRunning = true;
                btnProgress.setBackgroundResource(R.drawable.anim_progress);
                animProgress = (AnimationDrawable) btnProgress.getBackground();
                playSample();
                break;

            case R.id.btnRed:
                soundPool.play(soundRedId, volume, volume, 1, 0, 1f);
                if (isGameRunning)
                    if (levelsSequence[levelCarret] == GAME_COLORS.RED) {
                        if (++levelCarret == currentLevel)
                            notifySuccess();
                    } else {
                        notifyFailure();
                    }
                break;

            case R.id.btnYellow:
                soundPool.play(soundYellowId, volume, volume, 1, 0, 1f);
                if (isGameRunning)
                    if (levelsSequence[levelCarret] == GAME_COLORS.YELLOW) {
                        if (++levelCarret == currentLevel)
                            notifySuccess();
                    } else {
                        notifyFailure();
                    }
                break;

            case R.id.btnBlue:
                soundPool.play(soundBlueId, volume, volume, 1, 0, 1f);
                if (isGameRunning)
                    if (levelsSequence[levelCarret] == GAME_COLORS.BLUE) {
                        if (++levelCarret == currentLevel)
                            notifySuccess();
                    } else {
                        notifyFailure();
                    }
                break;

            case R.id.btnGreen:
                soundPool.play(soundGreenId, volume, volume, 1, 0, 1f);
                if (isGameRunning)
                    if (levelsSequence[levelCarret] == GAME_COLORS.GREEN) {
                        if (++levelCarret == currentLevel)
                            notifySuccess();
                    } else {
                        notifyFailure();
                    }
                break;

            case R.id.btnOrange:
                soundPool.play(soundOrangeId, volume, volume, 1, 0, 1f);
                if (isGameRunning)
                    if (levelsSequence[levelCarret] == GAME_COLORS.ORANGE) {
                        if (++levelCarret == currentLevel)
                            notifySuccess();
                    } else {
                        notifyFailure();
                    }
                break;

            case R.id.btnPink:
                soundPool.play(soundPinkId, volume, volume, 1, 0, 1f);
                if (isGameRunning)
                    if (levelsSequence[levelCarret] == GAME_COLORS.PINK) {
                        if (++levelCarret == currentLevel)
                            notifySuccess();
                    } else {
                        notifyFailure();
                    }
                break;

            case R.id.btnGray:
                soundPool.play(soundGrayId, volume, volume, 1, 0, 1f);
                if (isGameRunning)
                    if (levelsSequence[levelCarret] == GAME_COLORS.GRAY) {
                        if (++levelCarret == currentLevel)
                            notifySuccess();
                    } else {
                        notifyFailure();
                    }
                break;
        }
    }

    private void playSample() {
        disableButtons();
        toggleAnimProgress();
        tsStatus.setText("");
        tsStatus.setText(getResources().getText(R.string.game_text_warning));
        for (int i = 0; i < currentLevel; i++) {
            switch (levelsSequence[i]) {
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toggleAnimProgress();
                soundPool.play(soundYourTurnId, volume, volume, 1, 0, 1f);
                tsStatus.setText(getResources().getText(R.string.game_text_your_turn));
                enableButtons();
            }
        }, postDelay += DEFAULT_DELAY);

        levelCarret = 0; //Reinicia o contador para a verificação de acerto do próximo nível
        postDelay = DEFAULT_DELAY;
    }

    private void playRed() {
        performPlay(btnRed, soundRedId, R.drawable.button_red, R.drawable.button_red_pressed);
    }

    private void playYellow() {
        performPlay(btnYellow, soundYellowId, R.drawable.button_yellow, R.drawable.button_yellow_pressed);
    }

    private void playBlue() {
        performPlay(btnBlue, soundBlueId, R.drawable.button_blue, R.drawable.button_blue_pressed);
    }

    private void playGreen() {
        performPlay(btnGreen, soundGreenId, R.drawable.button_green, R.drawable.button_green_pressed);
    }

    private void playOrange() {
        performPlay(btnOrange, soundOrangeId, R.drawable.button_orange, R.drawable.button_orange_pressed);
    }

    private void playPink() {
        performPlay(btnPink, soundPinkId, R.drawable.button_pink, R.drawable.button_pink_pressed);
    }

    private void playGray() {
        performPlay(btnGray, soundGrayId, R.drawable.button_gray, R.drawable.button_gray_pressed);
    }

    private void toggleAnimProgress() {
        if (animProgress.isRunning()) {
            stopAnimProgress();
        } else {
            startAnimProgress();
        }
    }

    private void startAnimProgress() {
        if (!animProgress.isRunning()) {
            btnProgress.setBackgroundResource(R.drawable.anim_progress);
            animProgress = (AnimationDrawable) btnProgress.getBackground();
            animProgress.start();
        }
    }

    private void stopAnimProgress() {
        if (animProgress.isRunning()) {
            animProgress.stop();
            btnProgress.setBackgroundResource(R.drawable.ic_sample);
        }
    }

    private void disableButtons() {
        btnRed.setEnabled(false);
        btnYellow.setEnabled(false);
        btnBlue.setEnabled(false);
        btnGreen.setEnabled(false);
        btnOrange.setEnabled(false);
        btnPink.setEnabled(false);
        btnGray.setEnabled(false);
        btnProgress.setEnabled(false);
    }

    private void enableButtons() {
        btnRed.setEnabled(true);
        btnYellow.setEnabled(true);
        btnBlue.setEnabled(true);
        btnGreen.setEnabled(true);
        btnOrange.setEnabled(true);
        btnPink.setEnabled(true);
        btnGray.setEnabled(true);
//        btnProgress.setEnabled(true);
    }

    private void performPlay(final ImageButton button, final int soundId,
                             final int normalState, final int modifiedState) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(soundId, volume, volume, 1, 0, 1f);
                if (game_mode != GAME_MODE.BLIND_MODE)
                    button.setBackgroundResource(modifiedState);
            }
        }, postDelay += DEFAULT_DELAY);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (game_mode != GAME_MODE.BLIND_MODE)
                    button.setBackgroundResource(normalState);
            }
        }, postDelay += DEFAULT_DELAY);
    }


    private void notifySuccess() {
        if(currentLevel == MAX_LEVELS){
            notifyVictory();
        } else {
            updateLevel();
            updateScore();
            tsStatus.setText(getResources().getText(R.string.game_text_success));
            playNewSample();
        }
    }

    private void notifyFailure() {
        soundPool.play(soundGameOverId, volume, volume, 1, 0, 1f);
        tsStatus.setText("");
        tsStatus.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_top));
        tsStatus.setText(getResources().getText(R.string.game_text_failure));
        disableButtons();

        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putString("difficult", game_difficult.toString());
        bundle.putString("mode", game_mode.toString());
        GameOverDialog alertDialog = new GameOverDialog();
        alertDialog.setArguments(bundle);
        alertDialog.show(getSupportFragmentManager(), "game_over_dialog");
    }

    private void notifyVictory() {
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        bundle.putString("difficult", game_difficult.toString());
        bundle.putString("mode", game_mode.toString());
        startActivity(new Intent(this, WinActivity.class).putExtras(bundle));
        finish();
    }

    private void playNewSample() {
        disableButtons();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playSample();
            }
        }, 2000); //Toast.LENGTH_LONG = 3500 && Toast.LENGTH_SHORT = 2000
    }

    private void updateLevel() {
        tvLevel.setText(getResources().getText(R.string.game_text_level) + " " + String.valueOf(++currentLevel));
    }

    private void updateScore() {
        tvScore.setText(getResources().getText(R.string.game_text_score) + " " + String.valueOf(score += 5));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
