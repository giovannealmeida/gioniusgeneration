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
import com.giog.gioniusgeneration.activities.GameBeginnerActivity;
import com.giog.gioniusgeneration.activities.GameGeniusActivity;
import static com.giog.gioniusgeneration.utils.GameUtils.PREFS_GAME_MODE_KEY;

public class DifficultFragment extends Fragment implements View.OnClickListener{

    private Button btBeginner, btEasy, btNormal, btHard, btExpert, btGenius;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_difficult, container, false);

        btBeginner = (Button) view.findViewById(R.id.btnBeginnerDifficult);
        btEasy = (Button) view.findViewById(R.id.btnEasyDifficult);
        btNormal = (Button) view.findViewById(R.id.btnNormalDifficult);
        btHard = (Button) view.findViewById(R.id.btnHardDifficult);
        btExpert = (Button) view.findViewById(R.id.btnExpertDifficult);
        btGenius = (Button) view.findViewById(R.id.btnGeniusDifficult);

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
            case R.id.btnBeginnerDifficult:
                startActivity(new Intent(getActivity(), GameBeginnerActivity.class).putExtra(PREFS_GAME_MODE_KEY, MainActivity.CURRENT_MODE));
                break;
            case R.id.btnEasyDifficult:
                break;
            case R.id.btnNormalDifficult:
                break;
            case R.id.btnHardDifficult:
                break;
            case R.id.btnExpertDifficult:
                break;
            case R.id.btnGeniusDifficult:
                startActivity(new Intent(getActivity(), GameGeniusActivity.class).putExtra(PREFS_GAME_MODE_KEY, MainActivity.CURRENT_MODE));
                break;
        }
        getActivity().finish();
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
