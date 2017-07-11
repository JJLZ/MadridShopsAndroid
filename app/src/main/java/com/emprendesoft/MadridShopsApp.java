package com.emprendesoft;

import android.support.multidex.MultiDexApplication;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class MadridShopsApp extends MultiDexApplication {

    public static final String APP_NAME = MadridShopsApp.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        Picasso.setSingletonInstance(built);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // low memory: dump something
    }
}
