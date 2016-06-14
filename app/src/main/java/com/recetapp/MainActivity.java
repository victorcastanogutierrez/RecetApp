package com.recetapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.recetapp.model.Recipe;
import com.recetapp.model.User;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Firebase.setAndroidContext(this);
        mRef = new Firebase("https://recetapp-android.firebaseio.com/");
        final Context that = this;


        mRef.createUser("victorovi94@gmail.com", "hola", new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG);
                createUser(result.get("uid").toString());

                mRef.authWithPassword("victorovi94@gmail.com", "hola", new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(getApplicationContext(), "Logueado", Toast.LENGTH_LONG);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                    }
                });
            }
            @Override
            public void onError(FirebaseError firebaseError) {
               Log.i("err", "Ya registrado");
                mRef.authWithPassword("victorovi94@gmail.com", "hola", new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(getApplicationContext(), "Logueado", Toast.LENGTH_LONG);
                        crearReceta(mRef.getAuth().getUid(), "croquetas", "a la plancha");
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                    }
                });
            }
        });*/


    }

    private void createUser(String uid) {
        String nombre = "Victor Castaño";
        User u = new User(nombre, uid);

        Map<String, Object> insert = new HashMap<String, Object>();
        insert.put("/users/"+uid, u.toMap());
        mRef.updateChildren(insert);
    }

    private void crearReceta(String userId, String nombre, String descripcion) {
        //Creamos la receta
        String recetaKey = mRef.child("recipes").push().getKey();
        Recipe recipe = new Recipe(userId, nombre, descripcion);
        Map<String, Object> rmap = recipe.toMap();
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("/recipes/"+recetaKey, rmap);
        mRef.updateChildren(updates);

        //User update including his/her new own recipe
        updates = new HashMap<String, Object>();
        updates.put("/users/"+userId+"/own_recipes/"+recetaKey, true);
        mRef.updateChildren(updates);
    }
}
