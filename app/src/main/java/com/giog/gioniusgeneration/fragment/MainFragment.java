package com.giog.gioniusgeneration.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.giog.gioniusgeneration.R;
import com.giog.gioniusgeneration.activities.CreditsActivity;
import com.giog.gioniusgeneration.activities.OptionsActivity;
import com.giog.gioniusgeneration.utils.NotificationDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.plus.Plus;

import static com.giog.gioniusgeneration.utils.GameUtils.REQUEST_RESOLVE_ERROR;
import static com.giog.gioniusgeneration.utils.GameUtils.isOnline;
import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorDialog;
import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

public class MainFragment extends Fragment implements View.OnClickListener {

    private Button btPlay, btOptions, btHighScores, btAchievements, btCredits;
    private ImageButton btPlayGamesLogin;
    private boolean mResolvingError = false;
    private boolean tryinOpenScores = false;
    private boolean tryinOpenAchievements = false;

    public static GoogleApiClient mGoogleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        btPlay = (Button) rootView.findViewById(R.id.btnPlay);
        btOptions = (Button) rootView.findViewById(R.id.btnOptions);
        btHighScores = (Button) rootView.findViewById(R.id.btnHighScores);
        btAchievements = (Button) rootView.findViewById(R.id.btnAchievements);
        btCredits = (Button) rootView.findViewById(R.id.btnCredits);
        btPlayGamesLogin = (ImageButton) rootView.findViewById(R.id.btnLoginPlayGames);
        btPlay.setOnClickListener(this);
        btOptions.setOnClickListener(this);
        btHighScores.setOnClickListener(this);
        btAchievements.setOnClickListener(this);
        btCredits.setOnClickListener(this);
        btPlayGamesLogin.setOnClickListener(this);

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
                if (isOnline(getActivity())) {
                    tryinOpenScores = true;
                    if (mGoogleApiClient != null) {
                        if (mGoogleApiClient.isConnected()) {
                            tryinOpenScores = false;
                            getActivity().startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient), 0);
                        } else {
                            mGoogleApiClient.connect();
                        }
                    } else {
                        createApiClient();
                        connectToGooglePlayGames();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.dialog_notification_offline), Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btnAchievements:
                if (isOnline(getActivity())) {
                    tryinOpenAchievements = true;
                    if (mGoogleApiClient != null) {
                        if (mGoogleApiClient.isConnected()) {
                            tryinOpenAchievements = false;
                            getActivity().startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 0);
                        } else {
                            mGoogleApiClient.connect();
                        }
                    } else {
                        createApiClient();
                        connectToGooglePlayGames();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.dialog_notification_offline), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCredits:
                startActivity(new Intent(getActivity(), CreditsActivity.class));
                break;
            case R.id.btnLoginPlayGames:
                if(isOnline(getActivity())) {
                    if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
                        connectToGooglePlayGames();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.dialog_notification_offline), Toast.LENGTH_LONG).show();
                }
        }
    }

    private void showNotificationDialog(String message){
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        NotificationDialog dialog = new NotificationDialog();
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(),"notification_dialog");
    }

    @Override
    public void onStart() {
        super.onStart();
        connectToGooglePlayGames();
    }

    @Override
    public void onResume() {
        verifyGooglePlayServices();
        connectToGooglePlayGames();
        super.onResume();
    }

    private void verifyGooglePlayServices() {
        switch (isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
//                Toast.makeText(getActivity(), "Services OK", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
//                Toast.makeText(getActivity(), "Services desatualizado", Toast.LENGTH_SHORT).show();
                getErrorDialog(ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED, getActivity(), 0).show();
                break;
            case ConnectionResult.SERVICE_MISSING:
//                Toast.makeText(getActivity(), "Services inexistente", Toast.LENGTH_SHORT).show();
                getErrorDialog(ConnectionResult.SERVICE_MISSING, getActivity(), 0).show();
                break;
            case ConnectionResult.SERVICE_DISABLED:
//                Toast.makeText(getActivity(), "Services desabilitado", Toast.LENGTH_SHORT).show();
                getErrorDialog(ConnectionResult.SERVICE_DISABLED, getActivity(), 0).show();
                break;
        }
    }

    private void connectToGooglePlayGames() {
        if (isOnline(getActivity())) {
            if (mGoogleApiClient == null) {
                createApiClient();
            }
            if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    private void createApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        if (tryinOpenAchievements) {
                            getActivity().startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 0);
                            tryinOpenAchievements = false;
                        }

                        if (tryinOpenScores) {
                            getActivity().startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient), 0);
                            tryinOpenScores = false;
                        }

                        btPlayGamesLogin.setVisibility(View.GONE);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(), "Conexao falhou - " + connectionResult, Toast.LENGTH_LONG).show();

                        if (connectionResult.getErrorCode() == ConnectionResult.SIGN_IN_REQUIRED) {
                            Toast.makeText(getActivity(), "E preciso fazer login no Play Games", Toast.LENGTH_SHORT).show();
                        }

                        if (connectionResult.hasResolution() && !mResolvingError) {
                            try {
                                mResolvingError = true;
                                connectionResult.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
                            } catch (IntentSender.SendIntentException e) {
                                Toast.makeText(getActivity(), "Deu merda", Toast.LENGTH_SHORT).show();
                                mGoogleApiClient.connect();
                            }
                        }
                    }
                })
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (!mGoogleApiClient.isConnecting() &&
                            !mGoogleApiClient.isConnected()) {
                        Toast.makeText(getActivity(), "Tentando de novo", Toast.LENGTH_SHORT).show();
                        mGoogleApiClient.connect();
                    }
                    break;
                case GamesActivityResultCodes.RESULT_SIGN_IN_FAILED:
                    Toast.makeText(getActivity(), "Problemas na conexao. Tente mais tarde", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getActivity(), "Codigo estranho = " + resultCode, Toast.LENGTH_LONG).show();
            }
        }
    }
}
