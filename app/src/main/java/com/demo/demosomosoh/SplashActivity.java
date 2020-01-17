package com.demo.demosomosoh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.microsoft.appcenter.AppCenter;


public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Toolbar toolbar;
    private Button btNext;
    private Button btError,btError2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        btNext = findViewById(R.id.btNext);
        toolbar = findViewById(R.id.toolbar);
        btError=findViewById(R.id.btError);
        btError.setOnClickListener(v->{
            btError2.setText("error");
        });
        auth = FirebaseAuth.getInstance();
        setupToolbar();

        btNext.setOnClickListener(v -> {
            verifyAccount();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppCenter.setLogLevel(Log.VERBOSE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.option_exit).setVisible(false);
        menu.findItem(R.id.option_info).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_info) {
            infoDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void verifyAccount() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Model model = new Model();
            model.setEmail(user.getEmail());
            model.setLastName(user.getDisplayName());
            startActivity(new Intent(SplashActivity.this, MainActivity.class).putExtra("USUARIO",model));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }

    private void infoDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.licence_software)
                .content(R.string.message_info).positiveText(R.string.accept_general)
                .positiveColor(ContextCompat.getColor(this, R.color.colorAccent))
                .show();
    }
}
