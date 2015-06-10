package com.giog.gioniusgeneration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.giog.gioniusgeneration.fragment.MainFragment;
import com.giog.gioniusgeneration.utils.GameUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import static com.giog.gioniusgeneration.utils.GameUtils.isOnline;
import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorDialog;
import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    public static GameUtils.GAME_MODE CURRENT_MODE;
    public static GoogleApiClient mGoogleApiClient;
    private static AnimationDrawable animLogo;
    private ImageView ivImage;
    private boolean mResolvingError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage = (ImageView) findViewById(R.id.ivLogo);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        animLogo = (AnimationDrawable) ivImage.getBackground();
        animLogo.start();
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectToGooglePlayGames();
    }

    @Override
    protected void onPause() {
        if (animLogo != null && !animLogo.isRunning()) {
            animLogo.stop();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (animLogo != null && !animLogo.isRunning()) {
            animLogo.stop();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {

        verifyGooglePlayServices();
        connectToGooglePlayGames();

        if (animLogo != null && !animLogo.isRunning()) {
            animLogo.start();
        }
        super.onResume();
    }

    private void connectToGooglePlayGames() {
        if (isOnline(this)) {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                        .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        .build();
            }
            if (!isSignedIn()) {
                mGoogleApiClient.connect();
            }
        }
    }

    private void verifyGooglePlayServices() {
        switch (isGooglePlayServicesAvailable(this)) {
            case ConnectionResult.SUCCESS:
                Toast.makeText(this, "Services OK", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(this, "Services desatualizado", Toast.LENGTH_SHORT).show();
                getErrorDialog(ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED, this, 0).show();
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(this, "Services inexistente", Toast.LENGTH_SHORT).show();
                getErrorDialog(ConnectionResult.SERVICE_MISSING, this, 0).show();
                break;
            case ConnectionResult.SERVICE_DISABLED:
                Toast.makeText(this, "Services desabilitado", Toast.LENGTH_SHORT).show();
                getErrorDialog(ConnectionResult.SERVICE_DISABLED, this, 0).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new CloseGameDialog().show(getSupportFragmentManager(), "close_dialog");
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Conexão PRSTOU!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Conexão supendida", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"Conexão falhou - "+ connectionResult,Toast.LENGTH_LONG).show();
        if (connectionResult.hasResolution() && !mResolvingError) {
            try {
                mResolvingError = true;
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                Toast.makeText(this, "Deu merda", Toast.LENGTH_SHORT).show();
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "Resultado = " + resultCode, Toast.LENGTH_LONG).show();
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    Toast.makeText(this, "Tentando de novo", Toast.LENGTH_SHORT).show();
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    public static class CloseGameDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_confirm_game_close)
                    .setPositiveButton(R.string.dialog_button_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton(R.string.dialog_button_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
