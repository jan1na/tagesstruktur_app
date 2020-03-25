package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayPlan extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_plan);

        // data to populate the RecyclerView with
        ArrayList<DayPlan_Database.DataSet> dataSets = new ArrayList<>();
        dataSets.add(new DayPlan_Database.DataSet(new Date(), "Lesen", false));
        dataSets.add(new DayPlan_Database.DataSet(new Date(), "Malen", false));
        dataSets.add(new DayPlan_Database.DataSet(new Date(), "Musik machen", false));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.day_plan_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, dataSets);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        TextView title_day_plan_TV = findViewById(R.id.title_day_plan_TV);
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("DD.MM.YYYY");
        title_day_plan_TV.setText(format.format(d));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
