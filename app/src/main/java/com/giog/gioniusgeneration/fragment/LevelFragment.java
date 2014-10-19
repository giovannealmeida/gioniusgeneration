package com.giog.gioniusgeneration.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.giog.gioniusgeneration.MainActivity;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.activities.GameEasyActivity;

public class LevelFragment extends Fragment implements View.OnClickListener{

    private Button btBeginner, btEasy, btNormal, btHard, btExpert, btGenius;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_level, container, false);

        btBeginner = (Button) view.findViewById(R.id.btnBeginnerLevel);
        btEasy = (Button) view.findViewById(R.id.btnEasyLevel);
        btNormal = (Button) view.findViewById(R.id.btnNormalLevel);
        btHard = (Button) view.findViewById(R.id.btnHardLevel);
        btExpert = (Button) view.findViewById(R.id.btnExpertLevel);
        btGenius = (Button) view.findViewById(R.id.btnGeniusLevel);

        btBeginner.setOnClickListener(this);
        btEasy.setOnClickListener(this);
        btNormal.setOnClickListener(this);
        btHard.setOnClickListener(this);
        btExpert.setOnClickListener(this);
        btGenius.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBeginnerLevel:
                startActivity(new Intent(getActivity(), GameEasyActivity.class).putExtra("GAME_MODE", MainActivity.GAME_MODE));
                getActivity().finish();
                break;
            case R.id.btnEasyLevel:
                break;
            case R.id.btnNormalLevel:
                break;
            case R.id.btnHardLevel:
                break;
            case R.id.btnExpertLevel:
                break;
            case R.id.btnGeniusLevel:
                break;
        }
//        replaceFragment(new MainFragment());
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
