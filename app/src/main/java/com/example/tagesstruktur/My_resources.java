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
    private Internal_Storage skills_storage, activities_storage;
    private List<String> skills, activities;
    private LinearLayout skills_LL, activities_LL;
    private Button addSkill, add_resource_B, add_activity_B;
    private RelativeLayout add_resource_LL;
    private ScrollView res_SV;
    private EditText add_ET;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resources);

        skills_storage = new Internal_Storage(this, "skills");
        activities_storage = new Internal_Storage(this, "activities");
        skills_LL = findViewById(R.id.skills_LL);
        activities_LL = findViewById(R.id.activities_LL);
        addSkill = findViewById(R.id.add_skill_B);
        add_resource_B = findViewById(R.id.add_resource_B);
        add_resource_LL = findViewById(R.id.add_resource_RL);
        res_SV = findViewById(R.id.ressources_SV);
        add_ET = findViewById(R.id.add_resource_ET);
        add_activity_B = findViewById(R.id.add_activity_B);

        skills = skills_storage.readData();
        activities = activities_storage.readData();

        addViewToList(skills_LL, skills);
        addViewToList(activities_LL, activities);


        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_resource_LL.setVisibility(View.VISIBLE);
                res_SV.setAlpha(0.4f);
                add_ET.setHint("Fähigkeit");
                My_resources.skillsIsClicked = true;
            }
        });


        add_resource_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(My_resources.skillsIsClicked) {
                    skills_storage.saveData(add_ET.getText().toString());
                    addViewToList(skills_LL, add_ET.getText().toString());

                }else{
                    activities_storage.saveData(add_ET.getText().toString());
                    addViewToList(activities_LL, add_ET.getText().toString());
                }
                add_resource_LL.setVisibility(View.INVISIBLE);
                add_ET.getText().clear();
                res_SV.setAlpha(1.0f);
            }
        });


        add_activity_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_resource_LL.setVisibility(View.VISIBLE);
                res_SV.setAlpha(0.4f);
                add_ET.setHint("Aktivität");
                My_resources.skillsIsClicked = false;
            }
        });



    }

    @Override
    public void onBackPressed() {
        if (add_resource_LL.getVisibility() == View.VISIBLE){
            add_resource_LL.setVisibility(View.INVISIBLE);
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
