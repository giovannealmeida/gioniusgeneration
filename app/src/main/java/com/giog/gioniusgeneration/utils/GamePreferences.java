package com.giog.gioniusgeneration.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Giovanne on 19/06/2015.
 */
public class GamePreferences {

    private String PREFS_GAME_OPTIONS = "GAME_OPTIONS";
    private String PREFS_SPEED_KEY = "GAME_SPEED";
    private String PREFS_SHOW_NOTE_KEY = "GAME_SHOW_NOTE";
    private String PREFS_SHOW_MESSAGE_KEY = "GAME_SHOW_MESSAGE";
    private String PREFS_IMMEDIATE_START_KEY = "GAME_START_IMMEDIATE";
    private String PREFS_RING_BELL_KEY = "GAME_RING_BELL";
    private String PREFS_NOTE_MODE_KEY = "GAME_NOTE_MODE";

    private SharedPreferences sharedPreferences;

    public GamePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_GAME_OPTIONS, Context.MODE_PRIVATE);
    }

    public void setGameSpeed(int speed){
        sharedPreferences.edit().putInt(PREFS_SPEED_KEY,speed).commit();
    }

    public int getGameSpeed(){
        return sharedPreferences.getInt(PREFS_SPEED_KEY,GameUtils.GAME_SPEED_NORMAL);
    }

    public void setShowNoteEnabled(boolean op){
        sharedPreferences.edit().putBoolean(PREFS_SHOW_NOTE_KEY, op).commit();
    }

    public boolean isShowNoteEnabled(){
        return sharedPreferences.getBoolean(PREFS_SHOW_NOTE_KEY, false);
    }

    public void setShowMessageEnabled(boolean op){
        sharedPreferences.edit().putBoolean(PREFS_SHOW_MESSAGE_KEY,op).commit();
    }

    public boolean isShowMessageEnabled(){
        return sharedPreferences.getBoolean(PREFS_SHOW_MESSAGE_KEY,true);
    }

    public void setStartImmediateEnabled(boolean op){
        sharedPreferences.edit().putBoolean(PREFS_IMMEDIATE_START_KEY,op).commit();
    }

    public boolean isImmediateStartEnabled(){
        return sharedPreferences.getBoolean(PREFS_IMMEDIATE_START_KEY,false);
    }

    public void setRingBellEnabled(boolean op) {
        sharedPreferences.edit().putBoolean(PREFS_RING_BELL_KEY,op).commit();
    }

    public boolean isRingBellEnabled() {
        return sharedPreferences.getBoolean(PREFS_RING_BELL_KEY,true);
    }

    public void setNoteMode(int mode) {
        sharedPreferences.edit().putInt(PREFS_NOTE_MODE_KEY, mode).commit();
    }

    public int getNoteMode() {
        return sharedPreferences.getInt(PREFS_NOTE_MODE_KEY,0);
    }
}
