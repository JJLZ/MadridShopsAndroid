package com.emprendesoft.madridactivities.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.emprendesoft.Utils.Utilities;
import com.emprendesoft.madridactivities.fragments.ActivitiesFragment;
import com.emprendesoft.madridactivities.util.map.model.ActivityPin;
import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitesInteractor;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesFromCacheInteractor;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesFromCacheInteractorImpl;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesInteractorCompletion;
import com.emprendesoft.madridshops.domain.activities.interactors.GetAllActivitiesInteractorImp;
import com.emprendesoft.madridshops.domain.activities.interactors.GetIfAllActivitiesAreCachedInteractor;
import com.emprendesoft.madridshops.domain.activities.interactors.GetIfAllActivitiesAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.activities.interactors.SaveAllActivitiesIntoCacheInteractor;
import com.emprendesoft.madridshops.domain.activities.interactors.SaveAllActivitiesIntoCacheInteractorImpl;
import com.emprendesoft.madridshops.domain.activities.interactors.SetAllActivitesCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.activities.interactors.SetAllActivitiesCachedInteractor;
import com.emprendesoft.madridshops.domain.activities.managers.cache.GetAllActivitiesFromCacheManager;
import com.emprendesoft.madridshops.domain.activities.managers.cache.GetAllActivitiesFromCacheManagerDAOImpl;
import com.emprendesoft.madridshops.domain.activities.managers.cache.SaveAllActivitiesIntoCacheManager;
import com.emprendesoft.madridshops.domain.activities.managers.cache.SaveAllActivitiesIntoCacheManagerDAOImpl;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

public class ActivityListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    @BindView(R.id.activity_activity_list__progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    ActivitiesFragment mActivitiesFragment;
    private SupportMapFragment mapFragment;
    public GoogleMap map;
    private Activities allActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActivitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__fragment_activities);
        initializeMap();
    }

    private void initializeMap()
    {

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activity_list__map);

        mapFragment.getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {

                if (googleMap == null)
                {
                    Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_LONG).show();
                } else
                {
                    map = googleMap;
                    checkCacheData();
                    setupMap(googleMap);
                }
            }
        });
    }

    private void checkCacheData()
    {
        GetIfAllActivitiesAreCachedInteractor getIfAllActivitiesAreCachedInteractor = new GetIfAllActivitiesAreCachedInteractorImpl(this);
        getIfAllActivitiesAreCachedInteractor.execute(new Runnable()
                                                      {
                                                          @Override
                                                          public void run()
                                                          {
                                                              // all cached already, no need to download things, just read from DB
                                                              readDataFromCache();
                                                          }
                                                      }, new Runnable()
                                                      {
                                                          @Override
                                                          public void run()
                                                          {
                                                              // nothing cached yet
                                                              downloadIfInternetAvailable();
                                                          }
                                                      }
        );
    }

    private void downloadIfInternetAvailable()
    {
        if (Utilities.CheckInternetConnection(this))
        {
            // download activities info
            obtainActivityList();
        } else
        {
            // Alert user not Internet Available
            sendAlertInternetNotAvailable();
        }
    }

    private void sendAlertInternetNotAvailable()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Conexión no disponible");
        alertDialog.setMessage("Se requiere conexión a Internet para descargar la información de actividades.");
        alertDialog.setPositiveButton("ENTERADO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
            }
        });

        alertDialog.show();
    }

    private void readDataFromCache()
    {
        GetAllActivitiesFromCacheManager getAllActivitiesFromCacheManager = new GetAllActivitiesFromCacheManagerDAOImpl(this);
        GetAllActivitiesFromCacheInteractor getAllActivitiesFromCacheInteractor = new GetAllActivitiesFromCacheInteractorImpl(getAllActivitiesFromCacheManager);
        getAllActivitiesFromCacheInteractor.execute(new GetAllActivitiesInteractorCompletion()
        {
            @Override
            public void completion(@NonNull Activities activities)
            {
                allActivities = activities; // save a copy for filter
                configActivitiesFragment(activities); // load activities in fragment activity list
            }
        });
    }

    private void setupMap(GoogleMap map)
    {
        MapUtil.centerMapInPosition(map, 40.411335, -3.674908, (float) 12.0);
        map.setBuildingsEnabled(true);
        map.setMapType(MAP_TYPE_NORMAL);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    private void configActivitiesFragment(final Activities activities)
    {

        mActivitiesFragment.setActivities(activities);

        mActivitiesFragment.setOnElementClickListener(new OnElementClick<Activity>()
        {
            @Override
            public void clickedOn(@NonNull Activity element, int position)
            {
                Navigator.navigateFromActivityListActivityToActivityDetailActivity(ActivityListActivity.this, element, position);
            }
        });

        putShopPinsOnMap(activities);
    }

    private void putShopPinsOnMap(Activities activities)
    {

        List<MapPinnable> activityPins = ActivityPin.activityPinsFromActivities(activities);
        MapUtil.addPins(activityPins, map, this);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {

                if (marker.getTag() == null || !(marker.getTag() instanceof Activity))
                {
                    return;
                }

                Activity activity = (Activity) marker.getTag();
                Navigator.navigateFromActivityListActivityToActivityDetailActivity(ActivityListActivity.this, activity, 0);
            }
        });
    }

    private void obtainActivityList()
    {

        mProgressBar.setVisibility(View.VISIBLE);

        ANetworkManager manager = new GetAllActivitiesManagerImpl(this);
        GetAllActivitesInteractor getAllActivitesInteractor = new GetAllActivitiesInteractorImp(manager);
        getAllActivitesInteractor.execute(
                new GetAllActivitiesInteractorCompletion()
                {
                    @Override
                    public void completion(@NonNull Activities activities)
                    {
                        allActivities = activities; // save a copy for filter

                        SaveAllActivitiesIntoCacheManager saveManager = new SaveAllActivitiesIntoCacheManagerDAOImpl(getBaseContext());
                        SaveAllActivitiesIntoCacheInteractor saveInteractor = new SaveAllActivitiesIntoCacheInteractorImpl(saveManager);
                        saveInteractor.execute(activities, new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                SetAllActivitiesCachedInteractor setAllActivitiesCachedInteractor = new SetAllActivitesCachedInteractorImpl(getBaseContext());
                                setAllActivitiesCachedInteractor.execute(true);
                            }
                        });

                        downloadImagesToCache(activities);
                        configActivitiesFragment(activities);
                    }
                }, new InteractorErrorCompletion()
                {
                    @Override
                    public void onError(String errorDescription)
                    {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
        );
    }

    private void downloadImagesToCache(Activities activities)
    {
        if (activities != null)
        {
            for (Activity activity : activities.allActivities())
            {
                String imageURL = activity.getImageUrl();
                String logoURL = activity.getLogoUrl();

                Picasso.with(this).
                        load(logoURL).
                        placeholder(R.drawable.activity_placeholder).fetch();

                Picasso.with(this).
                        load(imageURL).
                        placeholder(R.drawable.activity_placeholder).fetch();

            }

            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_items, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s)
    {
        s = s.toLowerCase();

        Activities activities = new Activities();
        for (Activity activity : allActivities.allActivities())
        {
            String name = activity.getName().toLowerCase();

            if (name.contains(s))
            {
                activities.add(activity);
            }
        }

        configActivitiesFragment(activities); // load only filtered activities into fragment activity list

        map.clear();
        putShopPinsOnMap(activities);

        return true;
    }
}






























