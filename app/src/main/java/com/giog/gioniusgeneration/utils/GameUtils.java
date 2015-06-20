package com.giog.gioniusgeneration.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.TextView;
import com.giog.gioniusgeneration.R;

import java.util.Random;

/**
 * Created by Giovanne on 19/10/2014.
 */
public final class GameUtils {

    /*PREFRERENCES KEYS*/
    public static final String PREFS_GAME_MODE_KEY = "GAME_MODE";
    public static final String PREFS_SCORE_KEY = "SCORE";
    public static final String PREFS_NAME_VALUE = "_name";
    public static final String PREFS_SCORE_VALUE = "_score";
    public static final String PREFS_DIFFICULT_VALUE = "_difficult";
    public static final String PREFS_MODE_VALUE = "_mode";

    /*GAME CONSTANTS*/
    public final static int GAME_SPEED_VERY_SLOW = 1000; //in milliseconds
    public final static int GAME_SPEED_SLOW = 500; //in milliseconds
    public final static int GAME_SPEED_NORMAL = 350; //in milliseconds
    public final static int GAME_SPEED_FAST = 150; //in milliseconds
    public final static int GAME_SPEED_VERY_FAST = 70; //in milliseconds
    public final static int GAME_IMMEDIATE_START_DELAY = 1500; //in milliseconds
    public final static int MAX_LEVELS = 100;
    public final static int MAX_SAVED_SCORES = 100;
    public enum GAME_MODE {CLASSIC_MODE, BLIND_MODE}
    public enum GAME_DIFFICULT {BEGINNER, EASY, NORMAL, HARD, EXPERT, GENIUS}
    public enum GAME_COLORS {RED, YELLOW, BLUE, GREEN, ORANGE, PINK, GRAY}
    public final static int NOTE_MODE_LETTER = 0;
    public final static int NOTE_MODE_NAME = 1;

    /*GAME PREFERENCES*/
    public static int GAME_SPEED;
    public static boolean IS_IMMEDIATE_START_ENABLED;
    public static boolean IS_MESSAGE_ENABLED;
    public static boolean IS_NOTE_NAME_ENABLED;
    public static boolean IS_RING_BELL_ENABLED;
    public static int NOTE_MODE;

    /*GOOGLE PLAY GAMES CONSTANTS*/
    public static final int REQUEST_RESOLVE_ERROR = 1001;

    public static final String ACH_CONQUISTA_1 = "CgkI2LrX6_UYEAIQBQ";
    public static final String ACH_CONQUISTA_2 = "CgkI2LrX6_UYEAIQBg";
    public static final String ACH_CONQUISTA_3 = "CgkI2LrX6_UYEAIQBw";
    public static final String ACH_CONQUISTA_4 = "CgkI2LrX6_UYEAIQCA";
    public static final String ACH_CONQUISTA_5 = "CgkI2LrX6_UYEAIQCQ";
    public static final String ACH_PLAY_2_BLIND_GAMES = "CgkI2LrX6_UYEAIQFQ";

    public static final String LEAD_CLASSIC_MODE_BEGINNER = "CgkI2LrX6_UYEAIQBA";
    public static final String LEAD_CLASSIC_MODE_EASY = "CgkI2LrX6_UYEAIQCg";
    public static final String LEAD_CLASSIC_MODE_NORMAL = "CgkI2LrX6_UYEAIQDA";
    public static final String LEAD_CLASSIC_MODE_HARD = "CgkI2LrX6_UYEAIQCw";
    public static final String LEAD_CLASSIC_MODE_EXPERT = "CgkI2LrX6_UYEAIQDQ";
    public static final String LEAD_CLASSIC_MODE_GENIUS = "CgkI2LrX6_UYEAIQDg";
    public static final String LEAD_BLIND_MODE_BEGINNER = "CgkI2LrX6_UYEAIQDw";
    public static final String LEAD_BLIND_MODE_EASY = "CgkI2LrX6_UYEAIQEA";
    public static final String LEAD_BLIND_MODE_NORMAL = "CgkI2LrX6_UYEAIQEQ";
    public static final String LEAD_BLIND_MODE_HARD = "CgkI2LrX6_UYEAIQEg";
    public static final String LEAD_BLIND_MODE_EXPERT = "CgkI2LrX6_UYEAIQEw";
    public static final String LEAD_BLIND_MODE_GENIUS = "CgkI2LrX6_UYEAIQFA";

    public static void loadPreferences(Context context){
        GamePreferences gamePreferences = new GamePreferences(context);

        IS_IMMEDIATE_START_ENABLED = gamePreferences.isImmediateStartEnabled();
        IS_MESSAGE_ENABLED = gamePreferences.isShowMessageEnabled();
        IS_NOTE_NAME_ENABLED = gamePreferences.isShowNoteEnabled();
        IS_RING_BELL_ENABLED = gamePreferences.isRingBellEnabled();
        GAME_SPEED = gamePreferences.getGameSpeed();
        NOTE_MODE = gamePreferences.getNoteMode();
    }

    public static void setTextViewModeTitle(TextView tv, GAME_MODE mode, Context context){
        switch (mode){
            case CLASSIC_MODE:
                tv.setText(context.getResources().getString(R.string.game_text_mode)+" "+context.getResources().getString(R.string.mode_classic_text));
                break;
            case BLIND_MODE:
                tv.setText(context.getResources().getString(R.string.game_text_mode)+" "+context.getResources().getString(R.string.mode_blind_text));
                break;
        }
    }

    public static GAME_COLORS[] getRandomColorsSequence(Random random, GAME_DIFFICULT difficultId){
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

    public static boolean isOnline(Context context) {
        NetworkInfo mNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
