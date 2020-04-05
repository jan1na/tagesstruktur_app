package com.example.tagesstruktur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class My_resources extends AppCompatActivity {

    static boolean skillsIsClicked = true;
    private Internal_Storage skills_storage, activities_storage;
    private List<String> skills, activities;
    private LinearLayout skills_LL, activities_LL;
    private Button add_resource_B;
    private RelativeLayout add_resource_LL;
    private ScrollView res_SV;
    private EditText add_ET;
    private boolean removeIconVisible;
    private ImageView currentRemoveIcon;
    private RecyclerViewAdapter_Resources adapter_skills, adapter_activities;
    private RecyclerView recyclerView_skills, recyclerView_activities;
    private ImageView add_skill_B, add_activity_B;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.color_theme);
        setContentView(R.layout.activity_my_resources);

        skills_storage = new Internal_Storage(this, getString(R.string.resource_skills_filename));
        activities_storage = new Internal_Storage(this, getString(R.string.resource_activities_filename));
        skills_LL = findViewById(R.id.skills_LL);
        activities_LL = findViewById(R.id.activities_LL);
        add_skill_B = findViewById(R.id.add_skill_B);
        add_resource_B = findViewById(R.id.add_resource_B);
        add_resource_LL = findViewById(R.id.add_resource_RL);
        res_SV = findViewById(R.id.resources_SV);
        add_ET = findViewById(R.id.add_resource_ET);
        add_activity_B = findViewById(R.id.add_activity_B);
        recyclerView_skills = findViewById(R.id.resource_skill_RV);
        recyclerView_activities = findViewById(R.id.resource_activities_RV);

        currentRemoveIcon = null;
        removeIconVisible = false;


        skills = skills_storage.readData();
        activities = activities_storage.readData();


        /**
         * set up recycler view adapter for skills
         */
        recyclerView_skills.setLayoutManager(new LinearLayoutManager(this));
        adapter_skills = new RecyclerViewAdapter_Resources(this, skills);
        adapter_skills.setClickListener(new RecyclerViewAdapter_Resources.ItemClickListener() {
            /**
             *
             * Remove_icon was visible and is clicked now. So the "skill" needs to be removed from
             * the recycler view and the internal storage file of the skills needs to be updated.
             * Because the ViewHolder still is in the "selected" Mode, the properties needs to be
             * changed to default, in case of using this ViewHolder again for the next "skill",
             * which is added (deSelectAfterRemoving(View view)).
             *
             * @param view the remove_icon, which was clicked
             * @param position in the list of views in recycler view
             */
            @Override
            public void onItemClick(View view, final int position) {
                deSelectAfterRemoving(view);
                skills.remove(position);
                skills_storage.overrideData(skills);
                adapter_skills.notifyItemRemoved(position);
            }

            /**
             * Skill is clicked. If it was not selected before, it now shows the remove_icon. If
             * it was selected and remove_icon was visible, the ViewHolder gets set back to default
             * (remove_icon is not visible and text is not highlighted)
             *
             *
             * @param view ViewHolder of "skill"
             * @param position in List of ViewHolders
             */
            @Override
            public void onViewClick(View view, int position) {
                selectItem(view, position);
            }
        });
        recyclerView_skills.setAdapter(adapter_skills);


        /**
         * set up recycler view adapter for activities
         */
        recyclerView_activities.setLayoutManager(new LinearLayoutManager(this));
        adapter_activities = new RecyclerViewAdapter_Resources(this, activities);
        adapter_activities.setClickListener(new RecyclerViewAdapter_Resources.ItemClickListener() {
            /**
             *
             * Same as above with skills.
             *
             * @param view
             * @param position
             */
            @Override
            public void onItemClick(View view, final int position) {
                deSelectAfterRemoving(view);
                activities.remove(position);
                activities_storage.overrideData(activities);
                adapter_activities.notifyItemRemoved(position);
            }

            /**
             *
             * Same as above with skills.
             *
             * @param view
             * @param position
             */
            @Override
            public void onViewClick(View view, int position) {
                selectItem(view, position);
            }
        });
        recyclerView_activities.setAdapter(adapter_activities);


        add_skill_B.setClickable(true);
        add_skill_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_resource_LL.setVisibility(View.VISIBLE);
                add_ET.setHint(R.string.add_resource_skills_ET_hint);
                My_resources.skillsIsClicked = true;
            }
        });


        add_activity_B.setClickable(true);
        add_activity_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_resource_LL.setVisibility(View.VISIBLE);
                add_ET.setHint(R.string.add_resource_activities_ET_hint);
                My_resources.skillsIsClicked = false;
            }
        });


        /**
         * EditText and Button is used twice. Ether for adding skills, or for adding activities,
         * depending on which button was clicked before. This is saved in the boolean
         * "skillsIsClicked".
         *
         */
        add_resource_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (My_resources.skillsIsClicked) {
                    skills_storage.saveData(add_ET.getText().toString());
                    skills.add(add_ET.getText().toString());
                    adapter_skills.notifyItemInserted(adapter_skills.getItemCount() - 1);

                } else {
                    activities_storage.saveData(add_ET.getText().toString());
                    activities.add(add_ET.getText().toString());
                    adapter_activities.notifyItemInserted(adapter_activities.getItemCount() - 1);
                }
                add_resource_LL.setVisibility(View.INVISIBLE);
                add_ET.getText().clear();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (add_resource_LL.getVisibility() == View.VISIBLE) {
            add_resource_LL.setVisibility(View.INVISIBLE);
            res_SV.setAlpha(1.0f);
            return;
        }
        super.onBackPressed();
    }


    private void selectItem(View view, int position) {
        ImageView icon = view.findViewById(R.id.resource_row_remove_icon);
        TextView textView = view.findViewById(R.id.resource_row_TV);
        if (icon.getVisibility() == View.INVISIBLE) {
            textView.setTextColor(MainActivity.getColorWithAttrs(My_resources.this, R.attr.error));
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            icon.setVisibility(View.VISIBLE);
        } else {
            textView.setTextColor(MainActivity.getColorWithAttrs(My_resources.this, R.attr.on_surface));
            textView.setTypeface(null, Typeface.NORMAL);
            icon.setVisibility(View.INVISIBLE);
        }
    }



    private void deSelectAfterRemoving(View view) {
        ImageView icon = (ImageView) view;
        FrameLayout frameLayout = (FrameLayout) icon.getParent();
        TextView textView = frameLayout.findViewById(R.id.resource_row_TV);
        textView.setTextColor(MainActivity.getColorWithAttrs(My_resources.this, R.attr.on_surface));
        textView.setTypeface(null, Typeface.NORMAL);
        icon.setVisibility(View.INVISIBLE);
    }

}