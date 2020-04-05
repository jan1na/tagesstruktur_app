package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Menu extends AppCompatActivity {

    private LinearLayout menu_LL;
    private List<Button> buttons;
    private TextView date_TV, my_rescoures_menu_TV, day_plan_menu_TV, successes_menu_TV;
    private Switch color_theme_switch;
    private ImageView burger_menu_icon;

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
            /**
             * In the file "color_theme" is ether a "0" or "1" to save the last selected color theme.
             * "0" -> Light Theme
             * "1" -> Dark Theme
             *
             * @param buttonView
             * @param isChecked
             */
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
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });




    }

    @Override
    public void onBackPressed()
    {
        // to not get to the FirstFragment
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
