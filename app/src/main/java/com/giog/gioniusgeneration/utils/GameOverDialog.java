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
import android.widget.EditText;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;

import org.w3c.dom.Text;

public class GameOverDialog extends DialogFragment {

    private EditText etName;
    private TextView tvScore;
    private int currentScore;

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
                        startActivity(new Intent(getActivity(),MainActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(),MainActivity.class));
                        getActivity().finish();
                    }
                });

        etName = (EditText) view.findViewById(R.id.etName);
        tvScore = (TextView) view.findViewById(R.id.tvScore);
        currentScore = getArguments().getInt("score", 0);
        tvScore.setText(getActivity().getResources().getText(R.string.game_dialog_your_score) + " " + String.valueOf(currentScore));

        return builder.create();
    }

    private void saveScore(){

        SharedPreferences preferences = getActivity().getSharedPreferences(GameUtils.PREFS_SCORE_KEY, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        String fixedName = etName.getText().toString();
        for(int i=0; i < GameUtils.MAX_SAVED_SCORES; i++) {
            if(preferences.getInt(i+GameUtils.PREFS_SCORE_VALUE, 0) == 0){
                editor.putInt(i+GameUtils.PREFS_SCORE_VALUE, currentScore);
                editor.putString(i+GameUtils.PREFS_NAME_VALUE, fixedName);
                break;
            } else{
                if(preferences.getInt(i+GameUtils.PREFS_SCORE_VALUE, 0) < currentScore){
                    int auxScore = preferences.getInt(i+GameUtils.PREFS_SCORE_VALUE, 0);
                    String auxName = preferences.getString(i+GameUtils.PREFS_NAME_VALUE, "");
                    editor.putInt(i+GameUtils.PREFS_SCORE_VALUE, currentScore);
                    editor.putString(i+GameUtils.PREFS_NAME_VALUE, fixedName);
                    currentScore = auxScore;
                    fixedName = auxName;
                }
            }
        }
        editor.commit();
    }
}