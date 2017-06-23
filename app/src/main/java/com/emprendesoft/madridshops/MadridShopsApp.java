package com.emprendesoft.madridshops;

import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.emprendesoft.madridshops.domain.managers.network.GetAllShopsManagerCompletion;
import com.emprendesoft.madridshops.domain.managers.network.GetAllShopsManagerImpl;
import com.emprendesoft.madridshops.domain.managers.network.ManagerErrorCompletion;
import com.emprendesoft.madridshops.domain.managers.network.NetworkManager;
import com.emprendesoft.madridshops.domain.model.Shops;
import com.squareup.picasso.Picasso;

public class MadridShopsApp extends MultiDexApplication {

    public static final String APP_NAME = MadridShopsApp.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: quitar la aberración que sigue
        NetworkManager manager = new GetAllShopsManagerImpl(getApplicationContext());
        manager.getShopsFromServer(new GetAllShopsManagerCompletion() {
                                       @Override
                                       public void completion(@NonNull Shops shops) {

                                       }
                                   }, new ManagerErrorCompletion() {
                                       @Override
                                       public void onError(String errorDescription) {

                                       }
                                   });

        // init app
        Log.d(APP_NAME, "App starting");
        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // low memory: dump something
    }
}
