package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button login_B = findViewById(R.id.login_B);
        login_B.setOnClickListener(this);
    }

    //todo: Hier müssen wir noch unterschiedliche Activities starten, je nachdem ob wir Registrierung oder Login durchführen
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Preferences_Interaction.class);
        Login.this.startActivity(intent);
    }
}
