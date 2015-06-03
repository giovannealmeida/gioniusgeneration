package com.giog.gioniusgeneration.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.utils.GameOverDialog;

public class WinActivity extends ActionBarActivity {

    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        this.bundle = getIntent().getExtras();
    }

    @Override
    public void onBackPressed() {
        GameOverDialog alertDialog = new GameOverDialog();
        alertDialog.setArguments(bundle);
        alertDialog.show(getSupportFragmentManager(), "game_over_dialog");
    }
}
