package com.recetapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.recetapp.Util.UserManager;
import com.recetapp.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    CallbackManager callbackManager;

    private Firebase ref;
    private ProgressDialog progressDialog;


    private void createUser(String name, String email) {
        User u = new User(name, email);
        Firebase postRef = ref.child("users");
        postRef.push().setValue(u.toMap());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Facebook login
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        //Firebase instanciation
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://recetapp-android.firebaseio.com/");

        //View and Facebook login callbacks
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);

        if (isLoggedIn()) {
            ((LinearLayout)findViewById(R.id.lnLayout)).setVisibility(View.GONE);
            getAccessFacebookData(AccessToken.getCurrentAccessToken());
        } else {
            initFacebookCallbacks();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initFacebookCallbacks() {
        //Facebook login callbacks
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {
                getAccessFacebookData(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "No se pudo loguear", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "No se pudo loguear", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAccessFacebookData(final AccessToken token) {
        createProcessDialog();
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                //Get facebook data from login
                Bundle bFacebookData = getFacebookData(object);
                Map<String, Object> fbUserData = null;
                try {
                    fbUserData = assertfbUserData(object);
                    onFacebookAccessTokenChange(token, fbUserData);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Datos de cuenta facebook insuficientes", Toast.LENGTH_LONG)
                            .show();
                    progressDialog.cancel();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void onFacebookAccessTokenChange(AccessToken token, final Map<String, Object> data) {
        if (token != null) {
            ref.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    checkRegister(data);
                }
                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    finish();
                }
            });
        } else {
            /* Logged out of Facebook so do a logout from the Firebase app */
            ref.unauth();
        }
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkRegister(final Map<String, Object> userdata) {
        Firebase ref = new Firebase("https://recetapp-android.firebaseio.com/users");
        Query queryef = ref.orderByChild("email").equalTo(userdata.get("email").toString());
        queryef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = null;
                if (snapshot.getValue() == null) { // Account does not exists
                    user = registerNewAccount(userdata);
                } else {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        user = postSnapshot.getValue(User.class);
                        user.setId(postSnapshot.getKey());
                    }
                }
                onUserLogin(user);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i("ASD", "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private User registerNewAccount(Map<String,Object> userdata) {
        Firebase userRef = ref.child("users");
        Firebase newUserRef = userRef.push();

        User user = new User(userdata.get("name").toString(),
                newUserRef.getKey(),
                userdata.get("email").toString());

        newUserRef.setValue(user.toMap());
        return user;
    }

    private Map<String,Object> assertfbUserData(JSONObject object) throws JSONException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("email", object.get("email"));
        result.put("name", object.get("name"));
        return result;
    }

    @NonNull
    private void createProcessDialog() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                progressDialog.show(MainActivity.this, "", "Cargando", false, false);
            }
        });
    }

    private void onUserLogin(User user) {
        createUserManager(user);
        progressDialog.dismiss();
        finish();
        Intent intent = new Intent(this, WallActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Bienvenido "+user.getName(),
                Toast.LENGTH_LONG).show();
    }

    private void createUserManager(User user) {
        User u = UserManager.getManager().getUser();
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setId(user.getId());
    }

    public void btRegister(View view) {
        /*Intent intent = new Intent(this, RegistrerActivity.class);
        startActivity(intent);*/
    }

    public boolean isLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null ? true : false;
    }
}

