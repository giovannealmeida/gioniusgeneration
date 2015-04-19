package com.giog.gioniusgeneration.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.activities.HighScoresActivity;
import com.giog.gioniusgeneration.utils.GameUtils;

public class GameOverFragment extends Fragment implements View.OnClickListener {

    private Button btClassicMode, btBlindMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_over, container, false);
//
//        this.btClassicMode = (Button) rootView.findViewById(R.id.btnClassicMode);
//        this.btBlindMode = (Button) rootView.findViewById(R.id.btnBlindMode);
//
//        this.btClassicMode.setOnClickListener(this);
//        this.btBlindMode.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

        if(getActivity() instanceof MainActivity)
            replaceFragment(new DifficultFragment());
        else {
            //Replace with high scores table
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
