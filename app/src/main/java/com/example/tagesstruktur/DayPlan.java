package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayPlan extends AppCompatActivity {

    private RecyclerViewAdapter_DayPlan adapter;
    private DatePicker datePick;
    private TimePicker timePicker;
    private EditText get_activity_ET;
    private Button pick_B;
    private Date new_Date;
    private String new_activity;
    private RelativeLayout add_plan_RL;
    private RecyclerView recyclerView;
    private TextView title_day_plan_TV;
    private FloatingActionButton add_plan;
    private DayPlan_Database dayPlan_database;
    private List<DayPlan_Database.DataSet_DayPlan> dayPlanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_plan);

        recyclerView = findViewById(R.id.day_plan_RV);
        datePick = findViewById(R.id.datePicker);
        pick_B = findViewById(R.id.pick_input_B);
        timePicker = findViewById(R.id.time_picker);
        get_activity_ET = findViewById(R.id.get_activity_ET);
        add_plan_RL = findViewById(R.id.add_plans_RL);
        title_day_plan_TV = findViewById(R.id.title_day_plan_TV);
        add_plan = findViewById(R.id.add_plan_in_day_plan_B);


        // get all appointments of today out of the database
        dayPlan_database = new DayPlan_Database(this);
        dayPlanList = dayPlan_database.getTodayDayPlan();


        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter_DayPlan(this, dayPlanList);
        adapter.setClickListener(new RecyclerViewAdapter_DayPlan.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(DayPlan.this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);


        // set date in Title
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(c);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.GERMAN);
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        title_day_plan_TV.setText(dayOfTheWeek + ", " + formattedDate);


        // set time picker to 24 hour view
        timePicker.setIs24HourView(true);


        // all actions for adding new data set to layout and database:

        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick.setVisibility(View.VISIBLE);
                pick_B.setVisibility(View.VISIBLE);
                add_plan_RL.setVisibility(View.VISIBLE);
                add_plan.setClickable(false);
            }
        });


        pick_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // different stages of collecting data for the data set

                // get date
                if (datePick.getVisibility() == View.VISIBLE) {
                    datePick.setVisibility(View.INVISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                }
                // get time
                else if (timePicker.getVisibility() == View.VISIBLE) {
                    new_Date = new Date(datePick.getYear() - 1900, datePick.getMonth(), datePick.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                    get_activity_ET.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.INVISIBLE);
                }
                // get Content/Activity as a string
                else {
                    pick_B.setVisibility(View.INVISIBLE);
                    new_activity = get_activity_ET.getText().toString();
                    get_activity_ET.setVisibility(View.INVISIBLE);
                    add_plan_RL.setVisibility(View.INVISIBLE);
                    add_plan.setClickable(true);
                    get_activity_ET.getText().clear();

                    // all data is collected and can be inserted into database and displayed
                    dayPlan_database.insert(new_Date, new_activity);

                    // appointment needs to be today cause this is the day plan
                    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String day = dayFormat.format(Calendar.getInstance().getTime());
                    try {
                        Date start = Internal_Database.dateFormat.parse(day + " 00:00:00"), end = Internal_Database.dateFormat.parse(day + " 23:59:59");
                        if(start.compareTo(new_Date) <= 0 && end.compareTo(new_Date) >=0) {

                            // find correct index to insert new appointment
                            int insertIndex = 0;
                            while (insertIndex < dayPlanList.size() - 1 && dayPlanList.get(insertIndex).getDate().compareTo(new_Date) < 0) {
                                insertIndex++;
                            }

                            dayPlanList.add(insertIndex, new DayPlan_Database.DataSet_DayPlan(new_Date, new_activity, false));
                            adapter.notifyItemInserted(insertIndex);
                            add_plan.setClickable(true);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
