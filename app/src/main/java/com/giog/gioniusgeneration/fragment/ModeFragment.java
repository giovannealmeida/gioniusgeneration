package com.giog.gioniusgeneration.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;

public class ModeFragment extends Fragment implements View.OnClickListener {

    public static final int CLASSIC_MODE = 0;
    public static final int BLIND_MODE = 1;

    private Button btClassicMode, btBlindMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mode, container, false);

        this.btClassicMode = (Button) rootView.findViewById(R.id.btnClassicMode);
        this.btBlindMode = (Button) rootView.findViewById(R.id.btnBlindMode);

        this.btClassicMode.setOnClickListener(this);
        this.btBlindMode.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnClassicMode:
                MainActivity.GAME_MODE = CLASSIC_MODE;
                break;

            case R.id.btnBlindMode:
                MainActivity.GAME_MODE = BLIND_MODE;
                break;

            default:
                MainActivity.GAME_MODE = CLASSIC_MODE;
        }
        replaceFragment(new LevelFragment());
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
