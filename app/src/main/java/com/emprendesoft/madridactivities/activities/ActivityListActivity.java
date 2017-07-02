package com.emprendesoft.madridactivities.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.emprendesoft.madridactivities.fragments.ActivitiesFragment;
import com.emprendesoft.madridshops.R;

import butterknife.ButterKnife;

public class ActivityListActivity extends AppCompatActivity {

    ActivitiesFragment mActivitiesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        ButterKnife.bind(this);

//        mActivitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__fragment_activities);
    }
}
