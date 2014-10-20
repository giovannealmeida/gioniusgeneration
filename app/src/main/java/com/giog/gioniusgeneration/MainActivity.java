package com.giog.gioniusgeneration;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.giog.gioniusgeneration.fragment.MainFragment;
import com.giog.gioniusgeneration.utils.GameUtils;

public class MainActivity extends ActionBarActivity {
    public static GameUtils.GAME_MODE CURRENT_MODE;

    private AnimationDrawable animLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivImage = (ImageView) findViewById(R.id.ivLogo);

        animLogo = (AnimationDrawable) ivImage.getBackground();
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
}
