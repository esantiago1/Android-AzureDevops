package com.demo.demosomosoh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText tvEmail, tvLastName, tvAge, tvBirthday;
    private Button btSave;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_main);
        tvEmail = findViewById(R.id.tvEmail);
        tvLastName = findViewById(R.id.tvLastName);
        tvAge = findViewById(R.id.tvAge);
        btSave = findViewById(R.id.btSave);
        tvBirthday = findViewById(R.id.tvBirthday);

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
