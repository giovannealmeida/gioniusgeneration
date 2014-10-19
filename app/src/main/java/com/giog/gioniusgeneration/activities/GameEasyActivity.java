package com.giog.gioniusgeneration.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.fragment.ModeFragment;
import com.giog.gioniusgeneration.utils.ExitDialog;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameEasyActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView tvGameMode;
    private ImageButton btnProgress;
    private AnimationDrawable animProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_easy);
        tvGameMode = (TextView) findViewById(R.id.tvGameMode);
        btnProgress= (ImageButton) findViewById(R.id.imPlaySample);
        btnProgress.setOnClickListener(this);
        initializeScreen();
    }

    private void initializeScreen(){
        getSupportActionBar().hide();

        switch (getIntent().getExtras().getInt("GAME_MODE")){
            case ModeFragment.CLASSIC_MODE:
                tvGameMode.setText(getResources().getString(R.string.mode_classic_text));
                break;
            case ModeFragment.BLIND_MODE:
                tvGameMode.setText(getResources().getString(R.string.mode_blind_text));
                break;
        }
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
