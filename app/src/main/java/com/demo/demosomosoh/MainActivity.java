package com.demo.demosomosoh;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements BiometricCallback {

    private EditText tvEmail, tvLastName, tvAge, tvBirthday;
    private Button btSave, btSecurity;
    private FirebaseDatabase database;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        tvEmail = findViewById(R.id.tvEmail);
        btSecurity = findViewById(R.id.btSecurity);
        tvLastName = findViewById(R.id.tvLastName);
        tvAge = findViewById(R.id.tvAge);
        btSave = findViewById(R.id.btSave);
        tvBirthday = findViewById(R.id.tvBirthday);
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();
        btSave.setOnClickListener(v -> {


            DatabaseReference myRef1 = database.getReference("correo");
            myRef1.setValue(tvEmail.getText().toString());

            DatabaseReference myRef2 = database.getReference("nombres");
            myRef2.setValue(tvLastName.getText().toString());

            DatabaseReference myRef3 = database.getReference("edad");
            myRef3.setValue(tvAge.getText().toString());

            DatabaseReference myRef4 = database.getReference("cumpleanios");
            myRef4.setValue(tvBirthday.getText().toString());


        });

        btSecurity.setOnClickListener(v ->
                new BiometricManager.BiometricBuilder(MainActivity.this)
                        .setTitle(getString(R.string.app_name))
                        .setSubtitle("Ingrese su huella")
                        .setDescription("Debe tener al menos una huella registrada en su dispositivo")
                        .setNegativeButtonText("Cancelar")
                        .build()
                        .authenticate(MainActivity.this));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_action_arrow_back);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        data(getIntent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.option_exit:
                onCloseSession();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void data(Intent intent) {
        if (intent != null) {
            Model model = intent.getParcelableExtra("USUARIO");
            if (model != null) {
                tvEmail.setText(model.getEmail());
                tvLastName.setText(model.getLastName());
                tvAge.setText(model.getAge());
                tvBirthday.setText(model.getBirthday());
            }
        }
    }

    private void onCloseSession() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(MainActivity.this, SplashActivity.class));
        finish();
    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_LONG).show();
        startActivity(new Intent(MainActivity.this, SecurityActivity.class));
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
    }
}
