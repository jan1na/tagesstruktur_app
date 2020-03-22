package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class My_resources extends AppCompatActivity {

    static boolean skillsIsClicked = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resources);

        final Internal_Storage skills_storage = new Internal_Storage(this, "skills");
        final Internal_Storage activities_storage = new Internal_Storage(this, "activities");




        final List<String> skills = skills_storage.readData();
        /*
        skills.add("Fähigkeit 1");
        skills.add("Fähigkeit 2");

         */

        List<String> activities = activities_storage.readData();
        /*
        activities.add("Aktivität 1");
        activities.add("Aktivität 1");

         */

        LinearLayout skills_LL = findViewById(R.id.skills_LL);
        addViewToList(skills_LL, skills);

        LinearLayout activities_LL = findViewById(R.id.activities_LL);
        addViewToList(activities_LL, activities);



        Button addSkill = findViewById(R.id.add_skill_B);
        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout add_resource_LL = findViewById(R.id.add_resource_RL);
                add_resource_LL.setVisibility(View.VISIBLE);
                ScrollView res_SV = findViewById(R.id.ressources_SV);
                res_SV.setAlpha(0.4f);
                EditText add_ET = findViewById(R.id.add_resource_ET);
                add_ET.setHint("Ressource");
                My_resources.skillsIsClicked = true;
            }
        });

        Button add_resource_B = findViewById(R.id.add_resource_B);
        add_resource_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText add_ET = findViewById(R.id.add_resource_ET);
                if(My_resources.skillsIsClicked) {
                    skills_storage.saveData(add_ET.getText().toString());
                    LinearLayout skills_LL = findViewById(R.id.skills_LL);
                    addViewToList(skills_LL, add_ET.getText().toString());

                }else{
                    activities_storage.saveData(add_ET.getText().toString());
                    LinearLayout activities_LL = findViewById(R.id.activities_LL);
                    addViewToList(activities_LL, add_ET.getText().toString());
                }
                RelativeLayout add_resource_LL = findViewById(R.id.add_resource_RL);
                add_resource_LL.setVisibility(View.INVISIBLE);
                ScrollView res_SV = findViewById(R.id.ressources_SV);
                res_SV.setAlpha(1.0f);
            }
        });

        Button add_activity_B = findViewById(R.id.add_activity_B);

        add_activity_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout add_resource_LL = findViewById(R.id.add_resource_RL);
                add_resource_LL.setVisibility(View.VISIBLE);
                ScrollView res_SV = findViewById(R.id.ressources_SV);
                res_SV.setAlpha(0.4f);
                EditText add_ET = findViewById(R.id.add_resource_ET);
                add_ET.setHint("Aktivität");
                My_resources.skillsIsClicked = false;
            }
        });



    }

    @Override
    public void onBackPressed() {
        RelativeLayout add_resource_LL = findViewById(R.id.add_resource_RL);
        if (add_resource_LL.getVisibility() == View.VISIBLE){
            add_resource_LL.setVisibility(View.INVISIBLE);
            ScrollView res_SV = findViewById(R.id.ressources_SV);
            res_SV.setAlpha(1.0f);
            return;
        }
        super.onBackPressed();
    }

    private void addViewToList(LinearLayout linearLayout, String data){
        List<String> list = new ArrayList<>();
        list.add(data);
        addViewToList(linearLayout, list);
    }

    private void addViewToList(LinearLayout linearLayout, List<String> data){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(15,15,15,15);


        for(String s: data){
            TextView t = new TextView(this);
            t.setText(s);
            t.setTextSize(18);
            t.setPadding(5,5,5,5);
            t.setLayoutParams(params);
            linearLayout.addView(t);

        }

    }

}
