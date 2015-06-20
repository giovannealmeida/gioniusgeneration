package com.giog.gioniusgeneration.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.utils.GamePreferences;
import com.giog.gioniusgeneration.utils.GameUtils;

public class OptionsActivity extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener {

    private CheckBox cbShowMessage, cbShowNotes, cbImmediateStart, cbRingBell;
    private RadioGroup rgGameSpeed;
    private RadioButton rbSlow, rbVerySlow, rbNormal, rbFast, rbVeryFast;

    private GamePreferences gamePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        rbVerySlow = (RadioButton) findViewById(R.id.rbVerySlow);
        rbSlow = (RadioButton) findViewById(R.id.rbSlow);
        rbNormal = (RadioButton) findViewById(R.id.rbNormal);
        rbFast = (RadioButton) findViewById(R.id.rbFast);
        rbVeryFast = (RadioButton) findViewById(R.id.rbVeryFast);

        rgGameSpeed = (RadioGroup) findViewById(R.id.rgGameSpeed);
        rgGameSpeed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.rbVerySlow:
                        gamePreferences.setGameSpeed(GameUtils.GAME_SPEED_VERY_SLOW);
                        GameUtils.GAME_SPEED = GameUtils.GAME_SPEED_VERY_SLOW;
                        break;
                    case R.id.rbSlow:
                        gamePreferences.setGameSpeed(GameUtils.GAME_SPEED_SLOW);
                        GameUtils.GAME_SPEED = GameUtils.GAME_SPEED_SLOW;
                        break;
                    case R.id.rbNormal:
                        gamePreferences.setGameSpeed(GameUtils.GAME_SPEED_NORMAL);
                        GameUtils.GAME_SPEED = GameUtils.GAME_SPEED_NORMAL;
                        break;
                    case R.id.rbFast:
                        gamePreferences.setGameSpeed(GameUtils.GAME_SPEED_FAST);
                        GameUtils.GAME_SPEED = GameUtils.GAME_SPEED_FAST;
                        break;
                    case R.id.rbVeryFast:
                        gamePreferences.setGameSpeed(GameUtils.GAME_SPEED_VERY_FAST);
                        GameUtils.GAME_SPEED = GameUtils.GAME_SPEED_VERY_FAST;
                        break;
                }
            }
        });

        cbShowMessage = (CheckBox) findViewById(R.id.cbShowMessages);
        cbShowNotes = (CheckBox) findViewById(R.id.cbShowNoteNames);
        cbImmediateStart = (CheckBox) findViewById(R.id.cbImmediateStart);
        cbRingBell = (CheckBox) findViewById(R.id.cbRingBell);

        gamePreferences = new GamePreferences(this);

        cbShowMessage.setOnCheckedChangeListener(this);
        cbShowNotes.setOnCheckedChangeListener(this);
        cbImmediateStart.setOnCheckedChangeListener(this);
        cbRingBell.setOnCheckedChangeListener(this);

        initializeScreen();
    }

    private void initializeScreen() {
        switch (gamePreferences.getGameSpeed()){
            case GameUtils.GAME_SPEED_VERY_SLOW:
                rbVerySlow.setChecked(true);
                break;
            case GameUtils.GAME_SPEED_SLOW:
                rbSlow.setChecked(true);
                break;
            case GameUtils.GAME_SPEED_NORMAL:
                rbNormal.setChecked(true);
                break;
            case GameUtils.GAME_SPEED_FAST:
                rbFast.setChecked(true);
                break;
            case GameUtils.GAME_SPEED_VERY_FAST:
                rbVeryFast.setChecked(true);
                break;
        }

        cbShowMessage.setChecked(gamePreferences.isShowMessageEnabled());
        cbShowNotes.setChecked(gamePreferences.isShowNoteEnabled());
        cbImmediateStart.setChecked(gamePreferences.isImmediateStartEnabled());
        cbRingBell.setChecked(gamePreferences.isRingBellEnabled());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cbShowMessages:
                gamePreferences.setShowMessageEnabled(isChecked);
                break;
            case R.id.cbShowNoteNames:
                gamePreferences.setShowNoteEnabled(isChecked);
                break;
            case R.id.cbImmediateStart:
                gamePreferences.setStartImmediateEnabled(isChecked);
                break;
            case R.id.cbRingBell:
                gamePreferences.setRingBellEnabled(isChecked);
                break;
        }
    }
}
