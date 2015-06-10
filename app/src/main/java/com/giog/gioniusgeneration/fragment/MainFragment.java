package com.giog.gioniusgeneration.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.activities.CreditsActivity;
import com.giog.gioniusgeneration.activities.OptionsActivity;
import com.google.android.gms.games.Games;

import static com.giog.gioniusgeneration.utils.GameUtils.isOnline;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Button btPlay, btOptions, btHighScores, btAchievements, btCredits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        btPlay = (Button) rootView.findViewById(R.id.btnPlay);
        btOptions = (Button) rootView.findViewById(R.id.btnOptions);
        btHighScores = (Button) rootView.findViewById(R.id.btnHighScores);
        btAchievements = (Button) rootView.findViewById(R.id.btnAchievements);
        btCredits = (Button) rootView.findViewById(R.id.btnCredits);
        btPlay.setOnClickListener(this);
        btOptions.setOnClickListener(this);
        btHighScores.setOnClickListener(this);
        btAchievements.setOnClickListener(this);
        btCredits.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                replaceFragment(new ModeFragment());
                break;
            case R.id.btnOptions:
                startActivity(new Intent(getActivity(), OptionsActivity.class));
                break;
            case R.id.btnHighScores:
//                startActivity(new Intent(getActivity(), HighScoresActivity.class));
                if (isOnline(getActivity())) {
                    if (MainActivity.mGoogleApiClient != null) {
                        if (MainActivity.mGoogleApiClient.isConnected()) {
                            getActivity().startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(MainActivity.mGoogleApiClient), 0);
                        } else {
                            MainActivity.mGoogleApiClient.connect();
                            getActivity().startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(MainActivity.mGoogleApiClient), 0);
                        }
                    }
                } else {

                    Toast.makeText(getActivity(),"You must be online",Toast.LENGTH_LONG).show(); //Transfomar em AlertDialog
                }

                break;
            case R.id.btnAchievements:
                if (isOnline(getActivity())) {
                    if (MainActivity.mGoogleApiClient != null) {
                        if (MainActivity.mGoogleApiClient.isConnected()) {
                            getActivity().startActivityForResult(Games.Achievements.getAchievementsIntent(MainActivity.mGoogleApiClient), 0);
                        } else {
                            MainActivity.mGoogleApiClient.connect();
                            getActivity().startActivityForResult(Games.Achievements.getAchievementsIntent(MainActivity.mGoogleApiClient), 0);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(),"You must be online",Toast.LENGTH_LONG).show(); //Transfomar em AlertDialog
                }
                break;
            case R.id.btnCredits:
                startActivity(new Intent(getActivity(), CreditsActivity.class));
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
