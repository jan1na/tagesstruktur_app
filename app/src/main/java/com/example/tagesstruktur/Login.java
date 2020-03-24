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
    public static boolean loginExists;
    private List<String> loginData;
    private Internal_Storage internal_storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        internal_storage = new Internal_Storage(this, "login");
        //todo: password nicht im Klartext speichern und überlegen ob Konto online gespeichert wird
        // und somit rücksetzbar ist
        /*
        "login" file:
        user-name
        password
         */
        loginExists = internal_storage.fileExists();

        LinearLayout login_LL = findViewById(R.id.login_LL); // ist per default invisible
        LinearLayout register_LL = findViewById(R.id.register_LL);

        if(loginExists){
            loginData = internal_storage.readData();
            for(String d: loginData){
                System.out.println("in data: "+d);
            }
            login_LL.setVisibility(View.VISIBLE);
            register_LL.setVisibility(View.INVISIBLE);
        }


        Button login_B = findViewById(R.id.login_B);
        login_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login_password_ET = findViewById(R.id.login_password_ET);
                if(loginData.get(1).equals(login_password_ET.getText().toString())){
                    Intent intent = new Intent(Login.this, Menu.class);
                    Login.this.startActivity(intent);
                    login_password_ET.getText().clear();
                }else{
                    Toast.makeText(Login.this, R.string.wrong_password_login_message, Toast.LENGTH_SHORT).show();
                    login_password_ET.getText().clear();
                }
            }
        });


        Button register_B = findViewById(R.id.register_B);
        register_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText register_name_ET = findViewById(R.id.register_name_ET);
                EditText register_password_ET = findViewById(R.id.register_password_ET);
                EditText register_password_again_ET = findViewById(R.id.register_password_again_ET);
                List<String> registerData = new ArrayList<>();
                registerData.add(register_name_ET.getText().toString());
                System.out.println("1: "+register_password_ET.getText().toString());
                System.out.println("2: "+register_password_again_ET.getText().toString());
                if(Login.loginExists){
                    Toast.makeText(Login.this, R.string.already_registrated_message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Preferences_Interaction.class);
                    Login.this.startActivity(intent);
                }
                else if(register_password_ET.getText().toString().equals(register_password_again_ET.getText().toString())){
                    registerData.add(register_password_ET.getText().toString());
                    internal_storage.saveData(registerData);
                    loginExists = true;
                    register_password_ET.getText().clear();
                    register_password_again_ET.getText().clear();
                    Intent intent = new Intent(Login.this, Preferences_Interaction.class);
                    Login.this.startActivity(intent);
                }else{
                    Toast.makeText(Login.this, R.string.not_identical_passwords_registration_message, Toast.LENGTH_SHORT).show();
                    register_password_ET.getText().clear();
                    register_password_again_ET.getText().clear();
                }
            }
        });

        TextView welcome_title_TV = findViewById(R.id.welcome_title_TV);

        if(loginExists){
            Resources res = getResources();
            welcome_title_TV.setText(String.format(res.getString(R.string.welcome_messages_login), loginData.get(0)));
        }
    }
}
