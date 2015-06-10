package com.giog.gioniusgeneration.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

/**
 * Created by Giovanne on 10/06/2015.
 */
public class PlayGamesServicesHandler implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    /*Google Play Games*/
    private final String LEADERBOARD_CLASSIC_BEGGINER = "CgkI2LrX6_UYEAIQBA";
    private final String LEADERBOARD_CLASSIC_EASY = "";
    private final String LEADERBOARD_CLASSIC_NORMAL = "";
    private final String LEADERBOARD_CLASSIC_HARD = "";
    private final String LEADERBOARD_CLASSIC_EXPERT = "";
    private final String LEADERBOARD_CLASSIC_GENIUS = "";
    private final String LEADERBOARD_BLIND_BEGGINER = "";
    private final String LEADERBOARD_BLIND_EASY = "";
    private final String LEADERBOARD_BLIND_NORMAL = "";
    private final String LEADERBOARD_BLIND_HARD = "";
    private final String LEADERBOARD_BLIND_EXPERT = "";
    private final String LEADERBOARD_BLIND_GENIUS = "";
    private GoogleApiClient mGoogleApiClient;

    Context context;

    public PlayGamesServicesHandler(Context context) {
        this.context = context;
    }

    public void connectOnGooglePlayGames() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                    .build();
        }

        mGoogleApiClient.connect();
    }

    public void showBeginnerScore(FragmentActivity parentActivity) {
        if(mGoogleApiClient.isConnected()){
            parentActivity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, LEADERBOARD_CLASSIC_BEGGINER), 0);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
