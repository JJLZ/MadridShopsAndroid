package com.emprendesoft.madridshops.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.emprendesoft.madridshops.services.ClearCacheService;
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

public class ShopListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    public final static int USER_PERMISSION_FINE_LOCATION = 101;

    public GoogleMap map;
    @BindView(R.id.activity_shop_list__progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    ShopsFragment shopsFragment;
    private SupportMapFragment mapFragment;
    private Shops allShops;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shop_list__fragment_shops);

        initializeMap();
    }

    private void checkCacheData()
    {
        GetIfAllShopsAreCachedInteractor getIfAllShopsAreCachedInteractor = new GetIfAllShopsAreCachedInteractorImpl(this);
        getIfAllShopsAreCachedInteractor.execute(new Runnable()
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
            obtainShopList();
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
        alertDialog.setMessage("Se requiere conexión a Internet para descargar la información de las tiendas.");
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
        GetAllShopsFromCacheManager getAllShopsFromCacheManager = new GetAllShopsFromCacheManagerDAOImp(this);
        GetAllShopsFromCacheInteractor getAllShopsFromCacheInteractor = new GetAllShopsFromCacheInteractorImpl(getAllShopsFromCacheManager);
        getAllShopsFromCacheInteractor.execute(new GetAllShopsInteractorCompletion()
        {
            @Override
            public void completion(@NonNull Shops shops)
            {

                allShops = shops;   // save a copy for filter
                configShopsFragment(shops); // load shops in fragment shop list
            }
        });
    }

    private void obtainShopList()
    {
        mProgressBar.setVisibility(View.VISIBLE);

        NetworkManager manager = new GetAllShopsManagerImpl(this);
        GetAllShopsInteractor getAllShopsInteractor = new GetAllShopsInteractorImp(manager);
        getAllShopsInteractor.execute(
                new GetAllShopsInteractorCompletion()
                {
                    @Override
                    public void completion(Shops shops)
                    {

                        allShops = shops; // save a copy for filter

                        SaveAllShopsIntoCacheManager saveManager = new SaveAllShopsIntoCacheManagerDAOImp(getBaseContext());
                        SaveAllShopsIntoCacheInteractor saveInteractor = new SaveAllShopsIntoCacheInteractorImp(saveManager);
                        saveInteractor.execute(shops, new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                SetAllShopsAreCachedInteractor setAllShopsAreCachedInteractor = new SetAllShopsAreCachedInteractorImpl(getBaseContext());
                                setAllShopsAreCachedInteractor.execute(true);

                                //-- Schedule cache cleaning --
                                ClearCacheService.cancelScheduledCacheCleaning(getBaseContext());
                                ClearCacheService.scheduleCacheCleaning(getBaseContext(), getBaseContext().getResources().getInteger(R.integer.SEVEN_DAYS_IN_SECONDS));
                                //--
                            }
                        });

                        downloadImagesToCache(shops);
                        configShopsFragment(shops);
                    }
                },
                new InteractorErrorCompletion()
                {
                    @Override
                    public void onError(String errorDescription)
                    {
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

    private void configShopsFragment(Shops shops)
    {
        shopsFragment.setShops(shops);

        shopsFragment.setOnElementClickListener(new OnElementClick<Shop>()
        {
            @Override
            public void clickedOn(@NonNull Shop element, int position)
            {

                Navigator.navigateFromShopListActivityToShopDetailActivity(ShopListActivity.this, element, position);
            }
        });

        putShopPinsOnMap(shops);
    }

    private void initializeMap()
    {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shop_list__map);

        mapFragment.getMapAsync(new OnMapReadyCallback()
        {

            @Override
            public void onMapReady(GoogleMap googleMap)
            {
                if (googleMap == null)
                {
                    Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
                } else
                {
                    map = googleMap;
                    checkCacheData();
                    setupMap(googleMap);
                }
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

        enableUserLocation(map);
    }

    private void enableUserLocation(GoogleMap googleMap)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            googleMap.setMyLocationEnabled(true);
        } else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, USER_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case USER_PERMISSION_FINE_LOCATION:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        this.map.setMyLocationEnabled(true);
                    }
                    else
                    {
                        Toast.makeText(this, R.string.location_permission_message, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                break;
        }
    }

    private void putShopPinsOnMap(Shops shops)
    {
        List<MapPinnable> shopPins = ShopPin.shopPinsFromShops(shops);
        MapUtil.addPins(shopPins, map, this);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {

                if (marker.getTag() == null || !(marker.getTag() instanceof Shop))
                {
                    return;
                }

                Shop shop = (Shop) marker.getTag();
                Navigator.navigateFromShopListActivityToShopDetailActivity(ShopListActivity.this, shop, 0);
            }
        });
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

        Shops shops = new Shops();
        for (Shop shop : allShops.allShops())
        {
            String name = shop.getName().toLowerCase();

            if (name.contains(s))
            {
                shops.add(shop);
            }
        }

        configShopsFragment(shops); // load only filtered shops in fragment shop list

        map.clear();
        putShopPinsOnMap(shops);

        return true;
    }
}































