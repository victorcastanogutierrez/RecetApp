package com.recetapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.recetapp.util.UserUtil;
import com.recetapp.model.User;

import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Firebase ref;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        //Firebase instanciation
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://recetapp-android.firebaseio.com/");

        setUpEmailPredict();
        registerUser();
    }

    private void setUpEmailPredict() {
        List<String> emails = UserUtil.getEmailAddress(RegisterActivity.this);
        if (emails.size() > 0) {
            ((EditText)findViewById(R.id.field_email)).setText(emails.get(0));
        }
    }

    private void registerUser() {

        findViewById(R.id.RegisterButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String nombre = ((EditText) findViewById(R.id.field_user)).getText().toString();
                String password = ((EditText) findViewById(R.id.field_password)).getText().toString();
                final String email = ((EditText) findViewById(R.id.field_email)).getText().toString();
                if(assertRegisterFields(nombre, password, email)){
                    ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        createNewUser(nombre, email);
                                    }
                                }).start();

                                finish();
                                Intent intent = new Intent(getApplicationContext(), WallActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Bienvenido " + nombre,
                                        Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                handleErrors(firebaseError);
                            }
                        }
                    );
                }
            }
        });
    }

    private void createNewUser(String nombre, String email) {
        Firebase userRef = ref.child("users");
        Firebase newUserRef = userRef.push();

        user = new User(nombre,newUserRef.getKey(),email);

        newUserRef.setValue(user.toMap());

        UserUtil.createUserManager(user);
    }

    private boolean assertRegisterFields(String nombre, String password, String email) {
        return nombre != null && email != null && password != null;
    }

    private void handleErrors(FirebaseError error){
        switch (error.getCode()) {
            case FirebaseError.EMAIL_TAKEN:
                Snackbar.make((View) findViewById(R.id.layoutRegister), "El Email ya está en uso", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case FirebaseError.INVALID_EMAIL:
                Snackbar.make((View) findViewById(R.id.layoutRegister), "El Email es incorrecto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case FirebaseError.NETWORK_ERROR:
                Snackbar.make((View) findViewById(R.id.layoutRegister), "Error al conectar con el servidor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
                Snackbar.make((View) findViewById(R.id.layoutRegister), "Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }
}
