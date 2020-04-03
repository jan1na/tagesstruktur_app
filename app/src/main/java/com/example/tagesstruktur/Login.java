package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private List<String> loginData;
    private Internal_Storage internal_storage;
    private LinearLayout login_LL;
    private Button login_B;
    private TextView welcome_title_TV;
    private EditText name_register_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.color_theme);
        setContentView(R.layout.activity_login);

        login_B = findViewById(R.id.login_B);
        welcome_title_TV = findViewById(R.id.welcome_title_TV);
        name_register_ET = findViewById(R.id.registration_name_ET);

        internal_storage = new Internal_Storage(this, getString(R.string.login_datafile_name));

        /*
        "login" file:
        user-name
         */

        login_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_register_ET.getText().toString().equals("")){
                    Toast.makeText(Login.this, R.string.no_name_toast_message, Toast.LENGTH_SHORT).show();
                    return;
                }
                MainActivity.user_name = name_register_ET.getText().toString();
                internal_storage.saveData(name_register_ET.getText().toString());
                Intent intent = new Intent(Login.this, Preferences_Interaction.class);
                Login.this.startActivity(intent);
            }
        });
    }
}
