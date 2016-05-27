package com.recetapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://recetapp-android.firebaseio.com/");
        final Context that = this;


        mRef.createUser("victorovi94@gmail.com", "hola", new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.i("HOLA", "ME REGISTRO");

                mRef.authWithPassword("victorovi94@gmail.com", "hola", new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.i("HOLA", "logueo");
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.i("HOLA", "LO2G");
                    }
                });
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Log.i("err", "Ya registrado");
                mRef.authWithPassword("victorovi94@gmail.com", "hola", new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.i("HOLA", "logueo");
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.i("HOLA", "LO2G");
                    }
                });
            }
        });


    }
}
