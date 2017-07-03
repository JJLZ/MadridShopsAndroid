package com.emprendesoft.madridactivities.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emprendesoft.madridactivities.fragments.ActivitiesFragment;
import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.model.Activities;
import com.emprendesoft.madridshops.domain.model.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityListActivity extends AppCompatActivity {

    ActivitiesFragment mActivitiesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        mActivitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__fragment_activities);

        // TODO: temporal
        configActivitiesFragment();
    }

    // TODO: temporal
    private void configActivitiesFragment() {

        List<Activity> activities = new ArrayList<>();
        Activity activity1 = Activity.of(1, "Fist activity");
        Activity activity2 = Activity.of(2, "Second activity");
        activities.add(activity1);
        activities.add(activity2);

        Activities activities1 = Activities.from(activities);
        mActivitiesFragment.setActivities(activities1);
    }
}
