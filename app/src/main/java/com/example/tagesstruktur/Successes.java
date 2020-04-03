package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Successes extends AppCompatActivity {

    private RecyclerViewAdapter_Successes adapter;
    private RecyclerView successes_RV;
    private DayPlan_Database dayPlan_database;
    private List<DayPlan_Database.DataSet_DayPlan> daySuccessesList;
    private TextView successes_title_TV;
    private Date displayedDate;
    private ImageView back_B, next_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.color_theme);
        setContentView(R.layout.activity_successes);

        successes_RV = findViewById(R.id.successes_RV);
        successes_title_TV = findViewById(R.id.successes_title_TV);
        displayedDate = Calendar.getInstance().getTime();
        back_B = findViewById(R.id.success_back_B);
        next_B = findViewById(R.id.success_next_B);


        dayPlan_database = new DayPlan_Database(this);
        daySuccessesList = dayPlan_database.getTodaySuccesses();

        successes_RV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter_Successes(this, daySuccessesList);
        adapter.setClickListener(new RecyclerViewAdapter_Successes.ItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Toast.makeText(Successes.this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            }
        });
        successes_RV.setAdapter(adapter);

        setDateTitle();



        next_B.setClickable(true);
        next_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterDisplayedDate(1);
                displayNewDate();
            }
        });


        back_B.setClickable(true);
        back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterDisplayedDate(-1);
                displayNewDate();
            }
        });


    }

    private void alterDisplayedDate(int difference){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(displayedDate);
        calendar.add(Calendar.DATE, difference);
        displayedDate = calendar.getTime();
    }

    private void displayNewDate(){
        daySuccessesList.clear();
        daySuccessesList.addAll(dayPlan_database.getDaySuccesses(displayedDate));
        adapter.notifyDataSetChanged();
        setDateTitle();
    }

    private void setDateTitle(){
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(displayedDate);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.GERMAN);
        String dayOfTheWeek = sdf.format(displayedDate);
        successes_title_TV.setText("Erfolge\n"+dayOfTheWeek+", "+formattedDate);
    }
}
