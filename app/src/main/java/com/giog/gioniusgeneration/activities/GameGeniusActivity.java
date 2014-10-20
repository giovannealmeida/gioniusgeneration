package com.giog.gioniusgeneration.activities;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.utils.ExitDialog;
import java.util.Random;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_COLORS;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_DIFFICULT;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_MODE;
import static com.giog.gioniusgeneration.utils.GameUtils.PREFS_GAME_MODE_KEY;
import static com.giog.gioniusgeneration.utils.GameUtils.getRamdomColorsSequence;
import static com.giog.gioniusgeneration.utils.GameUtils.setTextViewModeTitle;

public class GameGeniusActivity extends ActionBarActivity implements View.OnClickListener {

    private GAME_DIFFICULT game_difficult = GAME_DIFFICULT.GENIUS;

    private TextView tvGameMode;
    private ImageButton btnProgress;
    private AnimationDrawable animProgress;
    private GAME_MODE game_mode;
    private GAME_COLORS[] levelsSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_genius);
        tvGameMode = (TextView) findViewById(R.id.tvGameMode);
        btnProgress= (ImageButton) findViewById(R.id.imPlaySample);
        btnProgress.setOnClickListener(this);

        initializeScreen();
        initializeSequence();
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
//        super.onBackPressed();
        DialogFragment exitDialog = new ExitDialog();
        exitDialog.show(getSupportFragmentManager(),"exit_dialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imPlaySample:
                btnProgress.setBackgroundResource(R.drawable.anim_progress);
                animProgress = (AnimationDrawable) btnProgress.getBackground();
                toggleAnimProgress();
                break;
        }
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
}
