package com.emprendesoft.madridshops.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsFromCacheInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsFromCacheInteractorImpl;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorCompletion;
import com.emprendesoft.madridshops.domain.interactors.GetAllShopsInteractorImp;
import com.emprendesoft.madridshops.domain.interactors.GetIfAllShopsAreCachedInteractor;
import com.emprendesoft.madridshops.domain.interactors.GetIfAllShopsAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.domain.interactors.SaveAllShopsIntoCacheInteractor;
import com.emprendesoft.madridshops.domain.interactors.SaveAllShopsIntoCacheInteractorImp;
import com.emprendesoft.madridshops.domain.interactors.SetAllShopsAreCachedInteractor;
import com.emprendesoft.madridshops.domain.interactors.SetAllShopsAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.managers.cache.GetAllShopsFromCacheManager;
import com.emprendesoft.madridshops.domain.managers.cache.GetAllShopsFromCacheManagerDAOImp;
import com.emprendesoft.madridshops.domain.managers.cache.SaveAllShopsIntoCacheManager;
import com.emprendesoft.madridshops.domain.managers.cache.SaveAllShopsIntoCacheManagerDAOImp;
import com.emprendesoft.madridshops.domain.managers.network.GetAllShopsManagerImpl;
import com.emprendesoft.madridshops.domain.managers.network.NetworkManager;
import com.emprendesoft.madridshops.domain.model.Shop;
import com.emprendesoft.madridshops.domain.model.Shops;
import com.emprendesoft.madridshops.fragments.ShopsFragment;
import com.emprendesoft.madridshops.navigator.Navigator;
import com.emprendesoft.madridshops.util.map.MapPinnable;
import com.emprendesoft.madridshops.util.map.MapUtil;
import com.emprendesoft.madridshops.util.map.model.ShopPin;
import com.emprendesoft.madridshops.views.OnElementClick;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

public class ShopListActivity extends AppCompatActivity {

    @BindView(R.id.activity_shop_list__progress_bar)
    ProgressBar mProgressBar;


    ShopsFragment shopsFragment;
    private SupportMapFragment mapFragment;
    public GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        ButterKnife.bind(this);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shop_list__fragment_shops);

        initializeMap();
    }

    private void checkCacheData() {

        GetIfAllShopsAreCachedInteractor getIfAllShopsAreCachedInteractor = new GetIfAllShopsAreCachedInteractorImpl(this);
        getIfAllShopsAreCachedInteractor.execute(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         // all cached already, no need to download things, just read from DB
                                                         readDataFromCache();
                                                     }
                                                 }, new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         // nothing cached yet
                                                         obtainShopList();
                                                     }
                                                 }

        );
    }

    private void readDataFromCache() {

        GetAllShopsFromCacheManager getAllShopsFromCacheManager = new GetAllShopsFromCacheManagerDAOImp(this);
        GetAllShopsFromCacheInteractor getAllShopsFromCacheInteractor = new GetAllShopsFromCacheInteractorImpl(getAllShopsFromCacheManager);
        getAllShopsFromCacheInteractor.execute(new GetAllShopsInteractorCompletion() {
            @Override
            public void completion(@NonNull Shops shops) {
                configShopsFragment(shops);
            }
        });
    }

    private void obtainShopList() {

        mProgressBar.setVisibility(View.VISIBLE);

        NetworkManager manager = new GetAllShopsManagerImpl(this);
        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorImp(manager);
        getAllShopsInteractor.execute(
                new GetAllShopsInteractorCompletion() {
                    @Override
                    public void completion(Shops shops) {

                        SaveAllShopsIntoCacheManager saveManager = new SaveAllShopsIntoCacheManagerDAOImp(getBaseContext());
                        SaveAllShopsIntoCacheInteractor saveInteractor = new SaveAllShopsIntoCacheInteractorImp(saveManager);
                        saveInteractor.execute(shops, new Runnable() {
                            @Override
                            public void run() {

                                SetAllShopsAreCachedInteractor setAllShopsAreCachedInteractor = new SetAllShopsAreCachedInteractorImpl(getBaseContext());
                                setAllShopsAreCachedInteractor.execute(true);
                            }
                        });

                        configShopsFragment(shops);
                        mProgressBar.setVisibility(View.GONE);
                    }
                },
                new InteractorErrorCompletion() {
                    @Override
                    public void onError(String errorDescription) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
        );

    }

    private void configShopsFragment(Shops shops) {
        shopsFragment.setShops(shops);

        shopsFragment.setOnElementClickListener(new OnElementClick<Shop>() {
            @Override
            public void clickedOn(@NonNull Shop element, int position) {

                Navigator.navigateFromShopListActivityToShopDetailActivity(ShopListActivity.this, element, position);
            }
        });

        putShopPinsOnMap(shops);
    }


    private void initializeMap() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shop_list__map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {

                if (googleMap == null) {
                    Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
                } else {
                    map = googleMap;
                    checkCacheData();
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

    private void putShopPinsOnMap(Shops shops) {

        List<MapPinnable> shopPins = ShopPin.shopPinsFromShops(shops);
        MapUtil.addPins(shopPins, map, this);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                if (marker.getTag() == null || !(marker.getTag() instanceof Shop)) {
                    return;
                }

                Shop shop = (Shop) marker.getTag();
                Navigator.navigateFromShopListActivityToShopDetailActivity(ShopListActivity.this, shop, 0);
            }
        });
    }
}































