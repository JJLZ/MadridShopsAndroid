package com.emprendesoft.madridactivities.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.emprendesoft.madridactivities.fragments.ActivitiesFragment;
import com.emprendesoft.madridactivities.util.map.model.ActivityPin;
import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitesInteractor;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesInteractorCompletion;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesInteractorImp;
import com.emprendesoft.madridshops.domain.activities.model.Activities;
import com.emprendesoft.madridshops.domain.activities.model.Activity;
import com.emprendesoft.madridshops.domain.activities.network.ANetworkManager;
import com.emprendesoft.madridshops.domain.activities.network.GetAllActivitiesManagerImpl;
import com.emprendesoft.madridshops.domain.shops.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.navigator.Navigator;
import com.emprendesoft.madridshops.util.map.MapPinnable;
import com.emprendesoft.madridshops.util.map.MapUtil;
import com.emprendesoft.madridshops.views.OnElementClick;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

public class ActivityListActivity extends AppCompatActivity {

    @BindView(R.id.activity_activity_list__progress_bar)
    ProgressBar mProgressBar;

    ActivitiesFragment mActivitiesFragment;
    private SupportMapFragment mapFragment;
    public GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        ButterKnife.bind(this);

        mActivitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__fragment_activities);

        initializeMap();

        // TODO: temporal
        obtainActivityList();
    }

    private void initializeMap() {

        mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                if (googleMap == null) {
                    Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_LONG).show();
                } else {
                    map = googleMap;
                    // TODO: falta
                    setupMap(googleMap);
                }
            }
        });
    }

    private void setupMap(GoogleMap map) {

        MapUtil.centerMapInPosition(map, 40.411335, -3.674908);
        map.setBuildingsEnabled(true);
        map.setMapType(MAP_TYPE_NORMAL);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    // TODO: temporal
    private void configActivitiesFragment(Activities activities) {

        mActivitiesFragment.setActivities(activities);

        mActivitiesFragment.setOnElementClickListener(new OnElementClick<Activity>() {
            @Override
            public void clickedOn(@NonNull Activity element, int position) {
                Navigator.navigateFromActivityListActivityToActivityDetailActivity(ActivityListActivity.this, element, position);
            }
        });

        putShopPinsOnMap(activities);
    }

    private void putShopPinsOnMap(Activities activities) {

        List<MapPinnable> activityPins = ActivityPin.activityPinsFromActivities(activities);
        MapUtil.addPins(activityPins, map, this);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                if (marker.getTag() == null || !(marker.getTag() instanceof Activity)) {
                    return;
                }

                Activity activity = (Activity) marker.getTag();
                Navigator.navigateFromActivityListActivityToActivityDetailActivity(ActivityListActivity.this, activity, 0);
            }
        });
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






























