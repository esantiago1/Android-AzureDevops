package com.demo.demosomosoh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    private LoginButton loginButton;
    private CallbackManager callbackManager;


    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private RelativeLayout content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupFaribase();
        content = findViewById(R.id.content);
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.loginButton);
        loginButton.setReadPermissions("public_profile", "email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookTokenFirebase(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void setupFaribase() {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void passMainActivity(Model model) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("USUARIO",model);
        startActivity(intent);
    }

    private void facebookTokenFirebase(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Model model=new Model();
                        model.setEmail(user.getEmail());
                        model.setLastName(user.getDisplayName());
                        //Toast.makeText(LoginActivity.this, user.getEmail()+" "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        passMainActivity(model);
                    } else {
                        Snackbar.make(content, "Fallo la autentificacion", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
