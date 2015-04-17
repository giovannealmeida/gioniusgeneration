package com.giog.gioniusgeneration.activities;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.utils.ExitGameDialog;
import com.giog.gioniusgeneration.utils.GameUtils;
import com.giog.gioniusgeneration.utils.GameUtils.GAME_MODE;

import static com.giog.gioniusgeneration.utils.GameUtils.DEFAULT_DELAY;
import static com.giog.gioniusgeneration.utils.GameUtils.PREFS_GAME_MODE_KEY;

import static com.giog.gioniusgeneration.utils.GameUtils.setTextViewModeTitle;

public class GameBeginnerActivity extends ActionBarActivity implements View.OnClickListener {

    private Context context;
    private int postDelay = DEFAULT_DELAY;
    private GameUtils.GAME_DIFFICULT game_difficult = GameUtils.GAME_DIFFICULT.BEGINNER;

    private TextView tvGameMode, tvLevel, tvScore;
    private TextSwitcher tsStatus;
    private ImageButton btnProgress;
    private AnimationDrawable animProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_beginner);
        tvGameMode = (TextView) findViewById(R.id.tvGameMode);
        btnProgress= (ImageButton) findViewById(R.id.imPlaySample);
        btnProgress.setOnClickListener(this);
        initializeScreen();
    }

    private void initializeScreen(){
        setTextViewModeTitle(tvGameMode, (GAME_MODE) getIntent().getExtras().get(PREFS_GAME_MODE_KEY), this);
    }

    @Override
    public void onBackPressed(){
//        super.onBackPressed();
        DialogFragment exitDialog = new ExitGameDialog();
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
