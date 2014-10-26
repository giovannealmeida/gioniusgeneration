package com.giog.gioniusgeneration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.giog.gioniusgeneration.fragment.MainFragment;
import com.giog.gioniusgeneration.utils.GameUtils;

public class MainActivity extends ActionBarActivity {
    public static GameUtils.GAME_MODE CURRENT_MODE;

    private static AnimationDrawable animLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivImage = (ImageView) findViewById(R.id.ivLogo);

        animLogo = (AnimationDrawable) ivImage.getBackground();
        animLogo.setOneShot(false);
        animLogo.start();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    protected void onPause(){
        animLogo.stop();
        super.onPause();
    }

    @Override
    protected void onResume(){
        animLogo.start();
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        new CloseGameDialog().show(getSupportFragmentManager(),"close_dialog");
    }

    public static class CloseGameDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            animLogo.stop();
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_confirm_game_close)
                    .setPositiveButton(R.string.dialog_button_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton(R.string.dialog_button_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            animLogo.start();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
