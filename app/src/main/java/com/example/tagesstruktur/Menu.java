package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.text.DateFormat.getDateInstance;

public class Menu extends AppCompatActivity {

    private LinearLayout menu_LL;
    private List<Button> buttons;
    private TextView date_TV, my_rescoures_menu_TV, day_plan_menu_TV, successes_menu_TV, burger_menu_icon;
    private Switch color_theme_switch;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.color_theme);

        setContentView(R.layout.activity_menu);

        menu_LL = findViewById(R.id.menu_LL);
        buttons = new ArrayList<>();
        date_TV = findViewById(R.id.date_menu_TV);
        my_rescoures_menu_TV = findViewById(R.id.ressources_menu_TV);
        day_plan_menu_TV = findViewById(R.id.dayplan_menu_TV);
        successes_menu_TV = findViewById(R.id.success_menu_TV);
        burger_menu_icon = findViewById(R.id.burger_menu_icon);
        color_theme_switch = findViewById(R.id.color_theme_switch);

        // set date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(c);
        date_TV.setText(formattedDate);


        my_rescoures_menu_TV.setClickable(true);
        my_rescoures_menu_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, My_resources.class);
                Menu.this.startActivity(intent);
            }
        });


        day_plan_menu_TV.setClickable(true);
        day_plan_menu_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, DayPlan.class);
                Menu.this.startActivity(intent);
            }
        });

        successes_menu_TV.setClickable(true);
        successes_menu_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Successes.class);
                Menu.this.startActivity(intent);
            }
        });



        if(MainActivity.color_theme == R.style.AppTheme_Dark){
            color_theme_switch.setChecked(true);
        }
        color_theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Internal_Storage color_theme_data = new Internal_Storage(Menu.this, getString(R.string.color_theme_datafile_name));
                if(isChecked){
                    MainActivity.color_theme = R.style.AppTheme_Dark;
                    color_theme_data.overrideData("1");
                }else {
                    MainActivity.color_theme = R.style.AppTheme_Light;
                    color_theme_data.overrideData("0");
                }
                // todo: maybe better way to refresh activity after changing color theme ?!
                Intent intent = new Intent(Menu.this, Menu.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });




    }
}
