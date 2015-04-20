package com.giog.gioniusgeneration.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.giog.gioniusgeneration.adapters.ScoreAdapter;
import com.giog.gioniusgeneration.utils.GameUtils;
import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.activities.HighScoresActivity;

import java.util.ArrayList;

public class GameOverFragment extends Fragment implements View.OnClickListener {

    private String current_mode = HighScoresActivity.CURRENT_MODE.toString();
    private TabHost tabHost;
    private TabHost.TabSpec tabSpec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_over, container, false);

        Button btClearScoreBeginner, btClearScoreEasy, btClearScoreNormal, btClearScoreHard, btClearScoreExpert, btClearScoreGenius;

        btClearScoreBeginner = (Button) rootView.findViewById(R.id.btnClearBeginner);
        btClearScoreEasy = (Button) rootView.findViewById(R.id.btnClearEasy);
        btClearScoreNormal = (Button) rootView.findViewById(R.id.btnClearNormal);
        btClearScoreHard = (Button) rootView.findViewById(R.id.btnClearHard);
        btClearScoreExpert = (Button) rootView.findViewById(R.id.btnClearExpert);
        btClearScoreGenius = (Button) rootView.findViewById(R.id.btnClearGenius);

        TextView tvMode = (TextView) rootView.findViewById(R.id.tvMode);
        if (HighScoresActivity.CURRENT_MODE == GameUtils.GAME_MODE.CLASSIC_MODE){
            tvMode.setText(getActivity().getResources().getText(R.string.button_mode_classic_text));
        } else {
            tvMode.setText(getActivity().getResources().getText(R.string.button_mode_blind_text));
        }

        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup();

        setUpTab("begginerTab", R.id.tabBeginner, R.string.button_difficult_beginner_text);
        setUpTab("easyTab", R.id.tabEasy, R.string.button_difficult_easy_text);
        setUpTab("normalTab", R.id.tabNormal, R.string.button_difficult_normal_text);
        setUpTab("hardTab", R.id.tabHard, R.string.button_difficult_hard_text);
        setUpTab("expertTab", R.id.tabExpert, R.string.button_difficult_expert_text);
        setUpTab("geniusTab", R.id.tabGenius, R.string.button_difficult_genius_text);

        SharedPreferences preferences = getActivity().getSharedPreferences(GameUtils.PREFS_SCORE_KEY, getActivity().MODE_PRIVATE);

        ListView lvScoreBeginner = (ListView) rootView.findViewById(R.id.lvScoreBeginner);
        ListView lvScoreEasy = (ListView) rootView.findViewById(R.id.lvScoreEasy);
        ListView lvScoreNormal = (ListView) rootView.findViewById(R.id.lvScoreNormal);
        ListView lvScoreHard = (ListView) rootView.findViewById(R.id.lvScoreHard);
        ListView lvScoreExpert = (ListView) rootView.findViewById(R.id.lvScoreExpert);
        ListView lvScoreGenius = (ListView) rootView.findViewById(R.id.lvScoreGenius);
        lvScoreBeginner.setEmptyView(rootView.findViewById(R.id.tvEmptyBeginner));
        lvScoreEasy.setEmptyView(rootView.findViewById(R.id.tvEmptyEasy));
        lvScoreNormal.setEmptyView(rootView.findViewById(R.id.tvEmptyNormal));
        lvScoreHard.setEmptyView(rootView.findViewById(R.id.tvEmptyHard));
        lvScoreExpert.setEmptyView(rootView.findViewById(R.id.tvEmptyExpert));
        lvScoreGenius.setEmptyView(rootView.findViewById(R.id.tvEmptyGenius));

        setUpScoreList(lvScoreBeginner,preferences,GameUtils.GAME_DIFFICULT.BEGINNER.toString());
        setUpScoreList(lvScoreEasy,preferences,GameUtils.GAME_DIFFICULT.EASY.toString());
        setUpScoreList(lvScoreNormal,preferences,GameUtils.GAME_DIFFICULT.NORMAL.toString());
        setUpScoreList(lvScoreHard,preferences,GameUtils.GAME_DIFFICULT.HARD.toString());
        setUpScoreList(lvScoreExpert,preferences,GameUtils.GAME_DIFFICULT.EXPERT.toString());
        setUpScoreList(lvScoreGenius,preferences,GameUtils.GAME_DIFFICULT.GENIUS.toString());

        return rootView;
    }

    private void setUpScoreList(ListView listView, SharedPreferences preferences, String difficult) {
        ArrayList<String> namesAndScores = new ArrayList<String>();

        for(int i=0;!preferences.getString(i+GameUtils.PREFS_NAME_VALUE,"").equals("");i++){
            if( preferences.getString(i+GameUtils.PREFS_MODE_VALUE,"").equals(current_mode)&&
                    preferences.getString(i + GameUtils.PREFS_DIFFICULT_VALUE, "").equals(difficult))
                namesAndScores.add(preferences.getString(i+GameUtils.PREFS_NAME_VALUE,"")+"|"+String.valueOf(preferences.getInt(i + GameUtils.PREFS_SCORE_VALUE, -1)));
        }

        ScoreAdapter adapter = new ScoreAdapter(getActivity(), namesAndScores);
        listView.setAdapter(adapter);
    }

    private void setUpTab(String tabTag, int tabId, int indicatorStringId) {
        tabSpec = tabHost.newTabSpec(tabTag);
        tabSpec.setContent(tabId);
        tabSpec.setIndicator(getResources().getText(indicatorStringId));
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnClearBeginner:
                break;
            case R.id.btnClearEasy:
                break;
            case R.id.btnClearNormal:
                break;
            case R.id.btnClearHard:
                break;
            case R.id.btnClearExpert:
                break;
            case R.id.btnClearGenius:
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
