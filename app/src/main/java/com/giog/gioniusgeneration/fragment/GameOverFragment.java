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
    private SharedPreferences preferences;
    private ListView lvScoreBeginner, lvScoreEasy, lvScoreNormal, lvScoreHard, lvScoreExpert, lvScoreGenius;

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

        btClearScoreBeginner.setOnClickListener(this);
        btClearScoreEasy.setOnClickListener(this);
        btClearScoreNormal.setOnClickListener(this);
        btClearScoreHard.setOnClickListener(this);
        btClearScoreExpert.setOnClickListener(this);
        btClearScoreGenius.setOnClickListener(this);

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

        preferences = getActivity().getSharedPreferences(GameUtils.PREFS_SCORE_KEY, getActivity().MODE_PRIVATE);

        lvScoreBeginner = (ListView) rootView.findViewById(R.id.lvScoreBeginner);
        lvScoreEasy = (ListView) rootView.findViewById(R.id.lvScoreEasy);
        lvScoreNormal = (ListView) rootView.findViewById(R.id.lvScoreNormal);
        lvScoreHard = (ListView) rootView.findViewById(R.id.lvScoreHard);
        lvScoreExpert = (ListView) rootView.findViewById(R.id.lvScoreExpert);
        lvScoreGenius = (ListView) rootView.findViewById(R.id.lvScoreGenius);
        lvScoreBeginner.setEmptyView(rootView.findViewById(R.id.tvEmptyBeginner));
        lvScoreEasy.setEmptyView(rootView.findViewById(R.id.tvEmptyEasy));
        lvScoreNormal.setEmptyView(rootView.findViewById(R.id.tvEmptyNormal));
        lvScoreHard.setEmptyView(rootView.findViewById(R.id.tvEmptyHard));
        lvScoreExpert.setEmptyView(rootView.findViewById(R.id.tvEmptyExpert));
        lvScoreGenius.setEmptyView(rootView.findViewById(R.id.tvEmptyGenius));

        setUpScoreList(lvScoreBeginner,GameUtils.GAME_DIFFICULT.BEGINNER.toString());
        setUpScoreList(lvScoreEasy,GameUtils.GAME_DIFFICULT.EASY.toString());
        setUpScoreList(lvScoreNormal,GameUtils.GAME_DIFFICULT.NORMAL.toString());
        setUpScoreList(lvScoreHard,GameUtils.GAME_DIFFICULT.HARD.toString());
        setUpScoreList(lvScoreExpert,GameUtils.GAME_DIFFICULT.EXPERT.toString());
        setUpScoreList(lvScoreGenius,GameUtils.GAME_DIFFICULT.GENIUS.toString());

        return rootView;
    }

    private void setUpScoreList(ListView listView, String difficult) {
        ArrayList<String> namesAndScores = new ArrayList<String>();

        for(int i=0;!preferences.getString(i+GameUtils.PREFS_NAME_VALUE,"").equals("");i++){
            if( preferences.getString(i+GameUtils.PREFS_MODE_VALUE,"").equals(current_mode)&&
                    preferences.getString(i + GameUtils.PREFS_DIFFICULT_VALUE, "").equals(difficult))
                namesAndScores.add(preferences.getString(i+GameUtils.PREFS_NAME_VALUE,"")+"|"+String.valueOf(preferences.getInt(i + GameUtils.PREFS_SCORE_VALUE, -1)));
        }

        ScoreAdapter adapter = new ScoreAdapter(getActivity(), namesAndScores);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                removeRegistry(GameUtils.GAME_DIFFICULT.BEGINNER);
                break;
            case R.id.btnClearEasy:
                removeRegistry(GameUtils.GAME_DIFFICULT.EASY);
                break;
            case R.id.btnClearNormal:
                removeRegistry(GameUtils.GAME_DIFFICULT.NORMAL);
                break;
            case R.id.btnClearHard:
                removeRegistry(GameUtils.GAME_DIFFICULT.HARD);
                break;
            case R.id.btnClearExpert:
                removeRegistry(GameUtils.GAME_DIFFICULT.EXPERT);
                break;
            case R.id.btnClearGenius:
                removeRegistry(GameUtils.GAME_DIFFICULT.GENIUS);
                break;
        }
    }

    private void removeRegistry(GameUtils.GAME_DIFFICULT difficult) {
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 0; i < GameUtils.MAX_SAVED_SCORES; i++) {
            if(preferences.getString(i+GameUtils.PREFS_DIFFICULT_VALUE,"").equals(difficult.toString())) {
                editor.remove(i + GameUtils.PREFS_DIFFICULT_VALUE);
                editor.remove(i + GameUtils.PREFS_MODE_VALUE);
                editor.remove(i + GameUtils.PREFS_NAME_VALUE);
                editor.remove(i + GameUtils.PREFS_SCORE_VALUE);
            }
        }
        editor.commit();

        switch (difficult){
            case BEGINNER:
                setUpScoreList(lvScoreBeginner,GameUtils.GAME_DIFFICULT.BEGINNER.toString());
                break;
            case EASY:
                setUpScoreList(lvScoreEasy,GameUtils.GAME_DIFFICULT.EASY.toString());
                break;
            case NORMAL:
                setUpScoreList(lvScoreNormal,GameUtils.GAME_DIFFICULT.NORMAL.toString());
                break;
            case HARD:
                setUpScoreList(lvScoreHard,GameUtils.GAME_DIFFICULT.HARD.toString());
                break;
            case EXPERT:
                setUpScoreList(lvScoreExpert,GameUtils.GAME_DIFFICULT.EXPERT.toString());
                break;
            case GENIUS:
                setUpScoreList(lvScoreGenius,GameUtils.GAME_DIFFICULT.GENIUS.toString());
                break;
        }
    }
}
