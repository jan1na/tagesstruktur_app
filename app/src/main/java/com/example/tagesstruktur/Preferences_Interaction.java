package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Preferences_Interaction extends AppCompatActivity {

    Spinner sp_frueh;
    Spinner sp_mittag;
    Spinner sp_abend;
    CharSequence time_frueh, time_mittag, time_abend;
    boolean frueh_selected, mittag_selected, abend_selected;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.color_theme);
        setContentView(R.layout.activity_preferences__interaction);

        sp_frueh = (Spinner) findViewById(R.id.fruehstueck_spinner);
        sp_mittag = (Spinner) findViewById(R.id.mittagessen_spinner);
        sp_abend = (Spinner) findViewById(R.id.abendessen_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                //R.array.time_array, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, R.layout.spinner_default_item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        sp_frueh.setAdapter(adapter);
        sp_mittag.setAdapter(adapter);
        sp_abend.setAdapter(adapter);

        sp_frueh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time_frueh = adapter.getItem(position);
                frueh_selected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                frueh_selected = false;
            }
        });

        sp_mittag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time_mittag = adapter.getItem(position);
                mittag_selected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mittag_selected = false;
            }
        });

        sp_abend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time_abend = adapter.getItem(position);
                abend_selected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                abend_selected = false;
            }
        });
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frueh_selected && mittag_selected && abend_selected){
                    Intent intent = new Intent(getApplicationContext(), Preferences_Interaction2.class);
                    intent.putExtra("time_frueh", time_frueh);
                    intent.putExtra("time_mittag", time_mittag);
                    intent.putExtra("time_abend", time_abend);
                    startActivity(intent);
                }
            }
        });



    }

}
