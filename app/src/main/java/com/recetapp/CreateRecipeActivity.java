package com.recetapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.recetapp.model.Recipe;
import com.recetapp.model.User;

import java.util.HashMap;
import java.util.Map;

public class CreateRecipeActivity extends AppCompatActivity {

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User u = new User("Victor", "379823hfq");
        crearReceta(u.getId(), "Nombre receta", "Descripcion de la receta");
    }

    private void crearReceta(String userId, String nombre, String descripcion) {
        String recetaKey = mRef.child("recipes").push().getKey();
        Recipe recipe = new Recipe(userId, nombre, descripcion);
        Map<String, Object> rmap = recipe.toMap();

        Map<String, Object> updates = new HashMap<>();
        updates.put("/recipes/"+recetaKey, rmap);
        updates.put("/user-recipes/"+userId+"/"+recetaKey, rmap);

        mRef.updateChildren(updates);
    }
}
