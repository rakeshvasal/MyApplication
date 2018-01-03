package com.example.rakeshvasal.myapplication.Fragments.FacebookFragments;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.ImageHelper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FacebookFragment extends Fragment {

    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView name, email, frnds, data;
    LinearLayout ll_user_details;
    ImageView profile_pic;
    Button getdata;
    LoginResult fbloginResult;
    List<String> permissons;
    ProfileTracker mProfileTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_facebook, parent, false);
        try {
            loginButton = (LoginButton) v.findViewById(R.id.login_button);
            name = (TextView) v.findViewById(R.id.name);
            email = (TextView) v.findViewById(R.id.email);
            frnds = (TextView) v.findViewById(R.id.frnds);
            data = (TextView) v.findViewById(R.id.data);
            permissons = new ArrayList<String>();
            profile_pic = (ImageView) v.findViewById(R.id.profilePicture);
            ll_user_details = (LinearLayout) v.findViewById(R.id.userDatall);
            getdata = (Button) v.findViewById(R.id.getInterestsButton);
            permissons.add("email");
            permissons.add("user_friends");
            permissons.add("user_posts");
            permissons.add("user_photos");
            permissons.add("read_custom_friendlists");
            permissons.add("user_hometown");
            permissons.add("user_about_me");
            permissons.add("user_birthday");
            permissons.add("user_education_history");
            permissons.add("user_hometown");
            permissons.add("user_location");
            permissons.add("user_likes");
            permissons.add("user_tagged_places");
            permissons.add("user_hometown");
            permissons.add("user_hometown");
            permissons.add("user_hometown");




            loginButton.setReadPermissions(permissons);
            // loginButton.setReadPermissions();
            // If using in a fragment
            loginButton.setFragment(FacebookFragment.this);
            boolean loggedIn = isLoggedIn();
            if (loggedIn) {
                ll_user_details.setVisibility(View.VISIBLE);
                // Toast.makeText(getActivity(),loginResult.getRecentlyGrantedPermissions().toString(),Toast.LENGTH_SHORT).show();
                boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
                AccessToken accesstoken = AccessToken.getCurrentAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    getdata.setVisibility(View.VISIBLE);
                    setProfileData(profile);
                } else {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.v("facebook - profile", currentProfile.getFirstName());
                            mProfileTracker.stopTracking();
                            Profile.setCurrentProfile(currentProfile);
                            setProfileData(currentProfile);
                        }
                    };
                }
            }
            getdata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    android.app.Fragment fragment = new FacebookHomeDashboard();
                    transaction.replace(((ViewGroup)getView().getParent()).getId(), fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                   /* setFacebookData();
                    getPosts();
                    getFreindsList();
                    getThreads();*/

                }
            });
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    ll_user_details.setVisibility(View.VISIBLE);
                    getdata.setVisibility(View.VISIBLE);
                    fbloginResult = loginResult;
                    // Toast.makeText(getActivity(),loginResult.getRecentlyGrantedPermissions().toString(),Toast.LENGTH_SHORT).show();

                    AccessToken accesstoken = AccessToken.getCurrentAccessToken();
                    Profile profile = Profile.getCurrentProfile();

                    if (profile != null) {
                        setProfileData(profile);
                    } else {
                        mProfileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                Log.v("facebook - profile", currentProfile.getFirstName());
                                mProfileTracker.stopTracking();
                                Profile.setCurrentProfile(currentProfile);
                                setProfileData(currentProfile);
                            }
                        };
                    }
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getActivity(), "Signing In Cancelled", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(getActivity(), "Error while Signing In", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception r) {
            r.printStackTrace();
            Log.e("Loginexception", r.getMessage());
            //loginButton.performClick();
        }
        return v;
    }

    private void setProfileData(Profile profile) {
        name.setText(profile.getName());
        email.setText(profile.getLinkUri().toString());

        try {
            new LoadProfileImage(profile_pic).execute(profile.getProfilePictureUri(100, 100).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        /*To respond to a login result, you need to register a callback with either LoginManager or LoginButton.
        If you register the callback with LoginButton, don't need to register the callback on Login manager.*/

       /* LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });*/
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;

        }


        protected Bitmap doInBackground(String... uri) {
            String url = uri[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            if (result != null) {


                Bitmap resized = Bitmap.createScaledBitmap(result, 200, 200, true);
                bmImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(), resized, 250, 200, 200, false, false, false, false));

            }
        }
    }

    private void setFacebookData() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");
                            Log.i("AccessToken", AccessToken.getCurrentAccessToken().getToken());

                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link", link);
                            if (Profile.getCurrentProfile() != null) {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }
                            if (object.has("friends")) {
                                try {
                                    JSONObject friend = object.getJSONObject("friends");
                                    JSONArray data = friend.getJSONArray("data");
                                    for (int i = 0; i < data.length(); i++) {

                                        Log.i("idddd", data.getJSONObject(i).getString("id"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.i("Login" + "Email", email);
                            Log.i("Login" + "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);

                            StringBuilder sb = new StringBuilder();
                            sb.append("Email -" + email + "\n");
                            sb.append("FirstName -" + firstName + "\n");
                            sb.append("LastName -" + lastName + "\n");
                            sb.append("Gender -" + gender + "\n");

                            data.setText(sb.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender,friends");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getFreindsList() {
        GraphRequest request1 = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friendlists",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("FrndsResponse1", response.toString());
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,link,picture,name");
        request1.setParameters(parameters);
        request1.executeAsync();
        //request1.executeAsync();
        Profile profile = Profile.getCurrentProfile();
        GraphRequest request = new GraphRequest(

                AccessToken.getCurrentAccessToken(),
                "/me/permissions",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("FrndsResponse2", response.toString());
                    }
                }

        );

        request.executeAsync();
    }


    private void getPosts() {
        Profile profile = Profile.getCurrentProfile();
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/photos", null, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.e("Responsephotos", response.toString());
                        /*String json = new Gson().toJson(response);
                        Log.e("Responsephotosjson", response.toString());*/
                    }
                }

        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "album,id,from,link,picture,name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getThreads() {
        Profile profile = Profile.getCurrentProfile();
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.e("Responsefeed", response.toString());
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("since", "1 january 2017");
        parameters.putString("until", "now");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /*get Facebook Friends Who has downoaded your app:

    replace parameters.putString("fields", "id,email,first_name,last_name");

    with parameters.putString("fields", "id,email,first_name,last_name,friends");

    Add below logic to get friends Data

                if (object.has("friends")) {
        JSONObject friend = object.getJSONObject("friends");
        JSONArray data = friend.getJSONArray("data");
        for (int i=0;i<data.length();i++){
            Log.i("idddd",data.getJSONObject(i).getString("id"));
        }
    }*/
}
