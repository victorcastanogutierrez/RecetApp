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
    }
}
