package com.emprendesoft.madridactivities.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.emprendesoft.madridactivities.fragments.ActivitiesFragment;
import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitesInteractor;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesInteractorCompletion;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesInteractorImp;
import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.network.ANetworkManager;
import com.emprendesoft.madridshops.domain.activities.network.GetAllActivitiesManagerImpl;
import com.emprendesoft.madridshops.domain.shops.interactors.InteractorErrorCompletion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityListActivity extends AppCompatActivity {

    @BindView(R.id.activity_activity_list__progress_bar)
    ProgressBar mProgressBar;

    ActivitiesFragment mActivitiesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        ButterKnife.bind(this);

        mActivitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__fragment_activities);

        // TODO: temporal
        obtainActivityList();
    }

    // TODO: temporal
    private void configActivitiesFragment(Activities activities) {

        mActivitiesFragment.setActivities(activities);
    }

    private void obtainActivityList() {

        mProgressBar.setVisibility(View.VISIBLE);

        ANetworkManager manager = new GetAllActivitiesManagerImpl(this);
        GetAllActivitesInteractor getAllActivitesInteractor = new GetAllActivitiesInteractorImp(manager);
        getAllActivitesInteractor.execute(
                new GetAllActivitiesInteractorCompletion() {
                    @Override
                    public void completion(@NonNull Activities activities) {

                        // TODO: temporal
                        configActivitiesFragment(activities);
                        mProgressBar.setVisibility(View.GONE);
                    }
                }, new InteractorErrorCompletion() {
                    @Override
                    public void onError(String errorDescription) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
        );
    }
}






























