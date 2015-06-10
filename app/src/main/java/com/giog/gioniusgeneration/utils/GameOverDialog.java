package com.giog.gioniusgeneration.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;
import com.google.android.gms.games.Games;

public class GameOverDialog extends DialogFragment {

    private EditText etName;
    private TextView tvScore;
    private int currentScore;
    private String difficultLevel, mode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_over, null);
        builder.setView(view).setMessage(getResources()
                .getText(R.string.dialog_save_your_score))
                .setPositiveButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        saveScore();
//                        startActivity(new Intent(getActivity(),MainActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        startActivity(new Intent(getActivity(),MainActivity.class));
                        getActivity().finish();
                    }
                });

        etName = (EditText) view.findViewById(R.id.etName);
        tvScore = (TextView) view.findViewById(R.id.tvScore);
        currentScore = getArguments().getInt("score", 0);
        difficultLevel = getArguments().getString("difficult");
        mode = getArguments().getString("mode");
        tvScore.setText(getActivity().getResources().getText(R.string.game_dialog_your_score) + " " + String.valueOf(currentScore));

        return builder.create();
    }

    private void saveScore(){

        if(MainActivity.mGoogleApiClient != null && MainActivity.mGoogleApiClient.isConnected()) {
            Toast.makeText(getActivity(), "Publicando no placar", Toast.LENGTH_LONG).show();
            Games.Leaderboards.submitScore(MainActivity.mGoogleApiClient, getString(R.string.leaderboard_classic_easy), currentScore);
            Toast.makeText(getActivity(), "Publicado!", Toast.LENGTH_LONG).show();
        }

        SharedPreferences preferences = getActivity().getSharedPreferences(GameUtils.PREFS_SCORE_KEY, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        String fixedName = etName.getText().toString();
        if(fixedName.trim().length() == 0)
            fixedName = getActivity().getResources().getString(R.string.player_name_default);

        for(int i=0; i < GameUtils.MAX_SAVED_SCORES; i++) {

//            if(actualDifficult == actualMode)
                if(preferences.getInt(i+GameUtils.PREFS_SCORE_VALUE, -1) == -1){
                    editor.putInt(i+GameUtils.PREFS_SCORE_VALUE, currentScore);
                    editor.putString(i+GameUtils.PREFS_NAME_VALUE, fixedName);
                    editor.putString(i+GameUtils.PREFS_DIFFICULT_VALUE, difficultLevel);
                    editor.putString(i+GameUtils.PREFS_MODE_VALUE, mode);
                    break;
                } else{
                    if(preferences.getInt(i+GameUtils.PREFS_SCORE_VALUE, -1) < currentScore){
                        int auxScore = preferences.getInt(i+GameUtils.PREFS_SCORE_VALUE, 0);
                        String auxName = preferences.getString(i+GameUtils.PREFS_NAME_VALUE, "");
                        String auxMode = preferences.getString(i+GameUtils.PREFS_MODE_VALUE, "");
                        String auxDiff = preferences.getString(i+GameUtils.PREFS_DIFFICULT_VALUE, "");
                        editor.putInt(i+GameUtils.PREFS_SCORE_VALUE, currentScore);
                        editor.putString(i+GameUtils.PREFS_NAME_VALUE, fixedName);
                        editor.putString(i+GameUtils.PREFS_DIFFICULT_VALUE, difficultLevel);
                        editor.putString(i+GameUtils.PREFS_MODE_VALUE, mode);
                        currentScore = auxScore;
                        fixedName = auxName;
                        difficultLevel = auxDiff;
                        mode = auxMode;
                    }
                }
        }
        editor.commit();
    }
}