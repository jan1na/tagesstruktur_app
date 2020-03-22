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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resources);



        final List<String> skills = new ArrayList<>();
        skills.add("Fähigkeit 1");
        skills.add("Fähigkeit 2");

        List<String>activities = new ArrayList<>();
        activities.add("Aktivität 1");
        activities.add("Aktivität 1");

        LinearLayout skills_LL = findViewById(R.id.skills_LL);
        /*

        for(String s: skills){
            TextView t = new TextView(this);
            t.setText(s);
            skills_LL.addView(t);

        }

         */

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
            }
        });

        Button add_resource_B = findViewById(R.id.add_resource_B);
        add_resource_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText add_ET = findViewById(R.id.add_resource_ET);
                skills.add(add_ET.getText().toString());
                RelativeLayout add_resource_LL = findViewById(R.id.add_resource_RL);
                add_resource_LL.setVisibility(View.INVISIBLE);
                ScrollView res_SV = findViewById(R.id.ressources_SV);
                res_SV.setAlpha(1.0f);
                finish();
                startActivity(getIntent());
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

}
