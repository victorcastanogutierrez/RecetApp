package com.recetapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.recetapp.Util.UserUtil;

public class WallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        ((Button) findViewById(R.id.btPrueba)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtil.logOut();
                finish();
                Intent i = new Intent(WallActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
