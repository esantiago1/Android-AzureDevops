package com.demo.demosomosoh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText tvEmail, tvLastName, tvAge, tvBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEmail = findViewById(R.id.tvEmail);
        tvLastName = findViewById(R.id.tvLastName);
        tvAge = findViewById(R.id.tvAge);
        tvBirthday = findViewById(R.id.tvBirthday);
    }


    @Override
    protected void onStart() {
        super.onStart();
        data(getIntent());
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
}
