package com.example.tagesstruktur;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.TypedValue;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static String user_name;
    public static int color_theme;
    public static ColorTheme colorTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Find out what color theme was used the last time. Default is Light mode.
         * If file "color_theme" has a 0 its the Light Mode and if it has a 1 its the Dark Mode.
         * The theme has to be set in every activity, that is why it is saved in a static variable.
         */
        Internal_Storage color_theme_data = new Internal_Storage(this, getString(R.string.color_theme_datafile_name));
        if(color_theme_data.fileExists()){

            if(color_theme_data.readData().get(0).equals("0")){
                color_theme = R.style.AppTheme_Light;
                colorTheme = ColorTheme.LIGHT;
            }else {
                color_theme = R.style.AppTheme_Dark;
                colorTheme = ColorTheme.DARK;
            }
        }else {
            color_theme = R.style.AppTheme_Light;
            colorTheme = ColorTheme.LIGHT;
        }
        setTheme(color_theme);


        /**
         * If the App is used for the first time the user needs to input his Name and gets to the
         * Login activity. Otherwise he directly is in the Menu. The name is also saved in a static
         * variable.
         */
        Internal_Storage login_data = new Internal_Storage(this, getString(R.string.login_datafile_name));
        if(login_data.fileExists()){
            user_name = login_data.readData().get(0);
            Intent intent = new Intent(this, Menu.class);
            MainActivity.this.startActivity(intent);
        }else {
            Intent intent = new Intent(this, Login.class);
            MainActivity.this.startActivity(intent);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public static int getColorWithAttrs(Context context, int attrs){
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrs, typedValue, true);
        return typedValue.data;
    }

}
