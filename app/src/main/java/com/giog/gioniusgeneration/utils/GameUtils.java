package com.giog.gioniusgeneration.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageButton;
import android.widget.TextView;
import com.giog.gioniusgeneration.R;

import java.util.Random;

/**
 * Created by Giovanne on 19/10/2014.
 */
public final class GameUtils {

    /*PREFRERENCES KEYS*/
    public static final String PREFS_GAME_MODE_KEY = "GAME_MODE";

    /*GAME CONSTANTS*/
    public static final int DEFAULT_DELAY = 800;
    private static int MAX_LEVELS = 100;
    public enum GAME_MODE {CLASSIC_MODE, BLIND_MODE}
    public enum GAME_DIFFICULT {BEGINNER, EASY, NORMAL, HARD, EXPERT, GENIUS}
    public enum GAME_COLORS {RED, YELLOW, BLUE, GREEN, ORANGE, PINK, GRAY}

    public static void setTextViewModeTitle(TextView tv, GAME_MODE mode, Context context){
        switch (mode){
            case CLASSIC_MODE:
                tv.setText(context.getResources().getString(R.string.mode_classic_text));
                break;
            case BLIND_MODE:
                tv.setText(context.getResources().getString(R.string.mode_blind_text));
                break;
        }
    }

    public static GAME_COLORS[] getRamdomColorsSequence(Random random, GAME_DIFFICULT difficultId){
        GAME_COLORS[] colors = new GAME_COLORS[MAX_LEVELS];
        for(int i = 0; i < MAX_LEVELS; i++){
            int color = random.nextInt()%(difficultId.ordinal()+2);
            if(color < 0){
                color*=-1;
            }
            colors[i] = GAME_COLORS.values()[color];
        }
        return colors;
    }
}
