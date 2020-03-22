package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.text.DateFormat.getDateInstance;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LinearLayout menu_LL = findViewById(R.id.menu_LL);
        List<Button> buttons = new ArrayList<>();

        TextView date_TV = findViewById(R.id.date_menu_TV);

        date_TV.setText("22.03.2020");

        TextView my_rescoures_menu_TV = findViewById(R.id.ressources_menu_TV);
        my_rescoures_menu_TV.setClickable(true);
        my_rescoures_menu_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, My_resources.class);
                Menu.this.startActivity(intent);
            }
        });


    }
}
