package com.example.rakeshvasal.myapplication.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.Activity.MainActivity;
import com.example.rakeshvasal.myapplication.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Rakeshvasal on 26-Jan-17.
 */

public class SigningActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    boolean signinresult=false;
    private GoogleApiClient mGoogleApiClient;
    Context context;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    public boolean getGoogleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        if(Utils.is_Connected_To_Internet(this)){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            //showProgressDialog();
            startActivityForResult(signInIntent, RC_SIGN_IN);}
        else {
            Toast.makeText(SigningActivity.this,getResources().getString(R.string.Check_Internet),LENGTH_SHORT).show();
        }


        return signinresult;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //hideProgressDialog();
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this,"Welcome " + acct.getDisplayName(),Toast.LENGTH_SHORT).show();
             signinresult=true;
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            signinresult=false;
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
