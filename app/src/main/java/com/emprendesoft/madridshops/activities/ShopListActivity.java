package com.emprendesoft.madridshops.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.emprendesoft.Utils.Utilities;
import com.emprendesoft.madridshops.R;
import com.emprendesoft.madridshops.domain.shops.interactors.GetAllShopsFromCacheInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.GetAllShopsFromCacheInteractorImpl;
import com.emprendesoft.madridshops.domain.shops.interactors.GetAllShopsInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.GetAllShopsInteractorCompletion;
import com.emprendesoft.madridshops.domain.shops.interactors.GetAllShopsInteractorImp;
import com.emprendesoft.madridshops.domain.shops.interactors.GetIfAllShopsAreCachedInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.GetIfAllShopsAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.shops.interactors.InteractorErrorCompletion;
import com.emprendesoft.madridshops.domain.shops.interactors.SaveAllShopsIntoCacheInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.SaveAllShopsIntoCacheInteractorImp;
import com.emprendesoft.madridshops.domain.shops.interactors.SetAllShopsAreCachedInteractor;
import com.emprendesoft.madridshops.domain.shops.interactors.SetAllShopsAreCachedInteractorImpl;
import com.emprendesoft.madridshops.domain.shops.managers.cache.GetAllShopsFromCacheManager;
import com.emprendesoft.madridshops.domain.shops.managers.cache.GetAllShopsFromCacheManagerDAOImp;
import com.emprendesoft.madridshops.domain.shops.managers.cache.SaveAllShopsIntoCacheManager;
import com.emprendesoft.madridshops.domain.shops.managers.cache.SaveAllShopsIntoCacheManagerDAOImp;
import com.emprendesoft.madridshops.domain.shops.managers.network.GetAllShopsManagerImpl;
import com.emprendesoft.madridshops.domain.shops.managers.network.NetworkManager;
import com.emprendesoft.madridshops.domain.shops.model.Shop;
import com.emprendesoft.madridshops.domain.shops.model.Shops;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

public class ShopListActivity extends AppCompatActivity {

    @BindView(R.id.activity_shop_list__progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    ShopsFragment shopsFragment;
    private SupportMapFragment mapFragment;
    public GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            obtainShopList();
        }
        else
        {
            // Alert user not Internet Available
            sendAlertInternetNotAvailable();
        }
    }

    private void sendAlertInternetNotAvailable() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Conexión no disponible");
        alertDialog.setMessage("Se requiere conexión a Internet para descargar la información de las tiendas.");
        alertDialog.setPositiveButton("ENTERADO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        alertDialog.show();
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

                        //-- TODO: cached images --
//                        configShopsFragment(shops);
                        downloadImagesToCache(shops);
                        configShopsFragment(shops);
//                        mProgressBar.setVisibility(View.GONE);
                        //--
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

    private void downloadImagesToCache(Shops shops)
    {
        if (shops != null)
        {
            for (Shop shop : shops.allShops())
            {
                String imageURL = shop.getImageUrl();
                String logoURL = shop.getLogoUrl();

                Picasso.with(this).
                        load(logoURL).
                        placeholder(R.drawable.shop_placeholder).fetch();

                Picasso.with(this).
                        load(imageURL).
                        placeholder(R.drawable.shop_placeholder).fetch();

            }

            mProgressBar.setVisibility(View.GONE);
        }
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































