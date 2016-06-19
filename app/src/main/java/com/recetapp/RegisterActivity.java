package com.recetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.recetapp.Util.UserUtil;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        setUpEmailPredict();
        setUpRegisterBt();
    }

    //TODO: register logic
    private void setUpRegisterBt() {
        //TODO: cuando un usuario se registra, su cuenta se debe registrar en firebase en /users
    }

    private void setUpEmailPredict() {
        List<String> emails = UserUtil.getEmailAddress(RegisterActivity.this);
        if (emails.size() > 0) {
            ((EditText)findViewById(R.id.field_email)).setText(emails.get(0));
        }
    }
}
