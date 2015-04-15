package com.giog.gioniusgeneration.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.giog.gioniusgeneration.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Button btPlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        btPlay = (Button) rootView.findViewById(R.id.btnPlay);
        btPlay.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
                replaceFragment(new ModeFragment());
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
