package com.recetapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.recetapp.model.User;
import com.recetapp.util.FacebookUtil;
import com.recetapp.util.UserUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private Firebase ref;
    private ProgressDialog progressDialog;

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

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));

        AccessTokenTracker mFacebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) { // Is logged in
                    MainActivity.this.onFacebookAccessTokenChange(currentAccessToken);
                    getAccessFacebookData(currentAccessToken);
                }
            }
        };

        //If the user is already logged in, just get the user data (email, username...)
        if (FacebookUtil.isFacebookLoggedIn()) {
            findViewById(R.id.lnLayout).setVisibility(View.GONE);
            getAccessFacebookData(AccessToken.getCurrentAccessToken());
        }

        setUpRegisterBt();
        setUpLoginBt();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getAccessFacebookData(final AccessToken token) {
        createProcessDialog();

        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                //Get facebook data from login
                final Map<String, Object> fbUserData;
                try {
                    Log.i("Data from login:", object.toString());
                    fbUserData = FacebookUtil.assertfbUserData(object);
                    checkRegister(fbUserData);
                } catch (JSONException | NullPointerException e) {
                    //The account does not have verified email
                    Snackbar.make((View) findViewById(R.id.lnLayout), "Datos de cuenta facebook insuficientes", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    findViewById(R.id.lnLayout).setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    UserUtil.logOut();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void onFacebookAccessTokenChange(AccessToken token) {
        if (token != null) {
            ref.authWithOAuthToken("facebook", token.getToken(), null);
        } else {
            ref.unauth();
        }
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
                Log.i("Error", "The read failed: " + firebaseError.getMessage());
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

    private void createProcessDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Procesando datos...");
        if (! this.isFinishing()) {
            progressDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void onUserLogin(User user) {
        UserUtil.createUserManager(user);
        finish();
        Intent intent = new Intent(this, WallActivity.class);
        startActivity(intent);
        Snackbar.make((View) findViewById(R.id.lnLayout), "Bienvenido "+user.getName(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void setUpRegisterBt() {
        findViewById(R.id.txRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpLoginBt() {
        findViewById(R.id.loginBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check inputs
                final String email = ((EditText) findViewById(R.id.field_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.field_password)).getText().toString();
                if (email.equals("") || password.equals("")) {
                    Snackbar.make((View) findViewById(R.id.lnLayout), "Email y password requeridos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    //Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(loginButton.getWindowToken(), 0);

                    createProcessDialog();
                    ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("email", email);
                            checkRegister(data);
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            Snackbar.make((View) findViewById(R.id.lnLayout), "Email/password incorrecto", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}

