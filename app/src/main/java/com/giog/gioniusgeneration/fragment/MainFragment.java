package com.giog.gioniusgeneration.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.activities.GameExpertActivity;
import com.giog.gioniusgeneration.activities.HighScoresActivity;

import static com.giog.gioniusgeneration.utils.GameUtils.PREFS_GAME_MODE_KEY;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Button btPlay, btHighScores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        btPlay = (Button) rootView.findViewById(R.id.btnPlay);
        btHighScores = (Button) rootView.findViewById(R.id.btnHighScores);
        btPlay.setOnClickListener(this);
        btHighScores.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
                replaceFragment(new ModeFragment());
                break;
            case R.id.btnHighScores:
                startActivity(new Intent(getActivity(), HighScoresActivity.class));
                break;
        }
    }

    private void replaceFragment(Fragment newFragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_fragment_menu_enter,
                        R.anim.anim_fragment_menu_exit,
                        R.anim.anim_fragment_menu_popenter,
                        R.anim.anim_fragment_menu_popexit)
                .replace(R.id.container, newFragment)
                .addToBackStack("mode")
                .commit();
    }
}
